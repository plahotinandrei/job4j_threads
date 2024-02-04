package ru.job4j.nonblocking;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {

    @Test
    public void whenIt() throws InterruptedException {
        CASCount count = new CASCount();
        var thread1 = new Thread(
                () -> IntStream.range(0, 1000).forEach((i) -> count.increment())
        );
        var thread2 = new Thread(
                () -> IntStream.range(0, 2000).forEach((i) -> count.increment())
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(count.get()).isEqualTo(3000);
    }
}