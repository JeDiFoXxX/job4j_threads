package ru.job4j;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {
    @Test
    void transferElementsFromOneProducerToOneConsumer() throws InterruptedException {
        SimpleBlockingQueue<Integer> blockingQueue = new SimpleBlockingQueue<>(2);
        List<Integer> list = new ArrayList<>();
        Thread producer = new Thread(() -> {
            try {
                blockingQueue.offer(1);
                blockingQueue.offer(2);
                blockingQueue.offer(3);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                try {
                    list.add(blockingQueue.poll());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(list).containsExactly(1, 2, 3);
    }

    @Test
    void transferElementsFromTwoProducerToOneConsumer() throws InterruptedException {
        SimpleBlockingQueue<Integer> blockingQueue = new SimpleBlockingQueue<>(2);
        List<Integer> list = new ArrayList<>();
        Thread firstProducer = new Thread(() -> {
            try {
                blockingQueue.offer(1);
                blockingQueue.offer(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread secondProducer = new Thread(() -> {
            try {
                blockingQueue.offer(2);
                blockingQueue.offer(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 4; i++) {
                try {
                    list.add(blockingQueue.poll());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        firstProducer.start();
        secondProducer.start();
        consumer.start();
        firstProducer.join();
        secondProducer.join();
        consumer.join();
        assertThat(list).contains(1, 1, 2, 2);
    }
}