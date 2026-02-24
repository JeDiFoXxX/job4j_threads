package ru.job4j.pools;

import static ru.job4j.pools.RolColSum.*;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {
    @Test
    void whenSumThenCorrect() throws ExecutionException, InterruptedException {
        int[][] matrix = createMatrix(5);
        Sums[] sumResult = sum(matrix);
        Sums[] asyncSumResult = asyncSum(matrix);
        assertThat(sumResult).isEqualTo(asyncSumResult);
    }

    private int[][] createMatrix(int size) {
        int[][] matrix = new int[size][size];
        int startValue = 1;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = startValue++;
            }
        }
        return matrix;
    }
}