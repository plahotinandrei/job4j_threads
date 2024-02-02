package ru.job4j.synch;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {

    @Test
    public void whenExecuteProducerAndConsumerThreads() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(
                () -> IntStream.range(1, 30).forEach(queue::offer),
                "Producer"
        );
        Thread consumer = new Thread(
                () -> IntStream.range(1, 29).forEach((i) -> queue.poll()),
                "Consumer"
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(queue.poll()).isEqualTo(29);
    }
}