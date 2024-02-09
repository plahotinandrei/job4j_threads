package ru.job4j.pool;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<T> extends RecursiveTask<Integer> {

    private T[] objects;
    private T object;
    private int from;
    private int to;

    public ParallelSearch() {

    }

    private ParallelSearch(T[] objects, T object, int from, int to) {
        this.objects = objects;
        this.object = object;
        this.from = from;
        this.to = to;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return List.of(objects).indexOf(object);
        }
        int middle = (from + to) / 2;
        ParallelSearch<T> leftSearch = new ParallelSearch<>(objects, object, from, middle);
        ParallelSearch<T> rightSearch = new ParallelSearch<>(objects, object, middle + 1, to);
        leftSearch.fork();
        rightSearch.fork();
        int left = leftSearch.join();
        int right = rightSearch.join();
        return left >= 0 ? left : right;
    }

    public int search(T[] objects, T object) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.<Integer>invoke(new ParallelSearch<>(objects, object, 0, objects.length - 1));
    }
}
