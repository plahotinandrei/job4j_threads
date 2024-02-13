package ru.job4j.pool;

import org.junit.jupiter.api.Test;
import java.util.concurrent.ExecutionException;
import static org.assertj.core.api.Assertions.*;
import static ru.job4j.pool.RowColSum.*;

class RowColSumTest {

    @Test
    public void whenSum() {
        int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums sums1 = new Sums(6, 12);
        Sums sums2 = new Sums(15, 15);
        Sums sums3 = new Sums(24, 18);
        assertThat(sum(matrix)).contains(sums1, sums2, sums3);
    }

    @Test
    public void whenAsyncSum() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Sums sums1 = new Sums(6, 12);
        Sums sums2 = new Sums(15, 15);
        Sums sums3 = new Sums(24, 18);
        assertThat(asyncSum(matrix)).contains(sums1, sums2, sums3);
    }

    @Test
    public void whenAsyncSumEqualsSum() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][] {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        assertThat(asyncSum(matrix)).isEqualTo(sum(matrix));
    }

    @Test
    public void whenEmptyMatrix() throws ExecutionException, InterruptedException {
        int[][] matrix = new int[][] {};
        assertThat(asyncSum(matrix)).isEmpty();
        assertThat(sum(matrix)).isEmpty();
    }
}