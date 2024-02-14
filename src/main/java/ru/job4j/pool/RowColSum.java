package ru.job4j.pool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

public class RowColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] rsl = new Sums[matrix.length];
        IntStream.range(0, rsl.length).forEach((i) -> rsl[i] = new Sums());
        for (int row = 0; row < matrix.length; row++) {
            Sums sums = rsl[row];
            for (int col = 0; col < matrix.length; col++) {
                sums.setRowSum(sums.getRowSum() + matrix[row][col]);
                rsl[col].setColSum(rsl[col].getColSum() + matrix[row][col]);
            }
        }
        return rsl;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Sums[] rsl = new Sums[matrix.length];
        Map<Integer, CompletableFuture<Sums>> futures = new HashMap<>();
        IntStream.range(0, matrix.length).forEach((i) -> futures.put(i, getSumsTask(matrix, i)));
        for (Integer key : futures.keySet()) {
            rsl[key] = futures.get(key).get();
        }
        return rsl;
    }

    private static CompletableFuture<Sums> getSumsTask(int[][] matrix, int i) {
        return CompletableFuture.supplyAsync(() -> {
            final Sums sums = new Sums();
            Arrays.stream(matrix[i])
                    .forEach((num) -> sums.setRowSum(sums.getRowSum() + num));
            Arrays.stream(matrix)
                    .forEach((row) -> sums.setColSum(sums.getColSum() + row[i]));
            return sums;
        });
    }
}
