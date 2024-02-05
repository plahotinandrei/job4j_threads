package ru.job4j.nonblocking;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int expectedValue;
        int newValue;
        do {
            expectedValue = count.get();
            newValue = expectedValue + 1;
        } while (!count.compareAndSet(expectedValue, newValue));
    }

    public int get() {
        return count.get();
    }
}
