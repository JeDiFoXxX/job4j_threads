package ru.job4j.pools;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < matrix[i].length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            sums[i] = new Sums(rowSum, colSum);
        }
        return sums;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] sums = new Sums[matrix.length];
        HashMap<Integer, CompletableFuture<int[]>> map = new HashMap<>();
        for (int i = 0; i < matrix.length; i++) {
            final int index = i;
            CompletableFuture<int[]> completableFuture = CompletableFuture.supplyAsync(() -> {
                int rowSum = 0;
                int colSum = 0;
                for (int j = 0; j < matrix[index].length; j++) {
                    rowSum += matrix[index][j];
                    colSum += matrix[j][index];
                }
                return new int[]{rowSum, colSum};
            });
            map.put(index, completableFuture);
        }
        for (Integer key : map.keySet()) {
            int[] array = map.get(key).get();
            sums[key] = new Sums(array[0], array[1]);
        }
        return sums;
    }
}