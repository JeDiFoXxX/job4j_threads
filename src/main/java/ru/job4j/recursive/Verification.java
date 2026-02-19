package ru.job4j.recursive;

public class Verification<T> {
    public static <T> void verification(T[] array, T object, int from, int to) {
        if (array == null) {
            throw new IllegalArgumentException("Not found array, array is null");
        }
        if (object == null) {
            throw new IllegalArgumentException("Not found search object, object is null");
        }
        if (!array.getClass().getComponentType().isAssignableFrom(object.getClass())) {
            throw new IllegalArgumentException("Different type array and search object");
        }
        if (from >= array.length || from < 0) {
            throw new IllegalArgumentException("Start index is out of valid range");
        }
        if (to > array.length || to < 0) {
            throw new IllegalArgumentException("End index is out of valid range");
        }
        if (from > to) {
            throw new IllegalArgumentException("Start index cannot be greater than end index");
        }
    }
}
