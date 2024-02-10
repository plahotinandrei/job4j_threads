package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {

    private final T[] objects;
    private final T object;
    private final int from;
    private final int to;

    private ParallelSearch(T[] objects, T object, int from, int to) {
        this.objects = objects;
        this.object = object;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return linearSearch(objects, object, from, to);
        }
        int middle = (from + to) / 2;
        ParallelSearch<T> leftSearch = new ParallelSearch<>(objects, object, from, middle);
        ParallelSearch<T> rightSearch = new ParallelSearch<>(objects, object, middle + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        return Math.max(left, right);
    }

    private int linearSearch(T[] objects, T object, int from, int to) {
        int index = -1;
        for (int i = from; i <= to; i++) {
            if (object.equals(objects[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static <T> int search(T[] objects, T object) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.<Integer>invoke(new ParallelSearch<>(objects, object, 0, objects.length - 1));
    }
}
