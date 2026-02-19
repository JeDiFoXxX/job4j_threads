package ru.job4j.recursive;

import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelIndexSearch<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final T object;
    private final int from;
    private final int to;

    public ParallelIndexSearch(T[] array, T object, int from, int to) {
        Verification.verification(array, object, from, to);
        this.array = array;
        this.object = object;
        this.from = from;
        this.to = to;
    }

    public static <T> Integer search(T[] array, T object) {
        return ForkJoinPool.commonPool().invoke(new ParallelIndexSearch<>(array, object, 0, array.length));
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return linearSearch();
        }
        int middle = (from + to) / 2;
        ParallelIndexSearch<T> leftSearch = new ParallelIndexSearch<>(array, object, from, middle);
        ParallelIndexSearch<T> rightSearch = new ParallelIndexSearch<>(array, object, middle, to);
        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        return left != -1 ? left : right;
    }

    private Integer linearSearch() {
        int result = -1;
        for (int index = from; index < to; index++) {
            if (Objects.equals(array[index], object)) {
                result = index;
                break;
            }
        }
        return result;
    }
}
