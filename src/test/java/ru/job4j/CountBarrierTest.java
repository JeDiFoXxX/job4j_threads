package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CountBarrierTest {
    @Test
    void threadStateCheck() throws InterruptedException {
        CountBarrier barrier = new CountBarrier(2);
        Thread first = new Thread(() -> {
            barrier.count();
            barrier.await();
        });
        Thread second = new Thread(() -> {
            barrier.count();
            barrier.await();
        });
        first.start();
        Thread.sleep(100);
        Thread.State state = first.getState();
        second.start();
        Thread.sleep(100);
        assertThat(Thread.State.WAITING).isEqualTo(state);
        assertThat(Thread.State.TERMINATED).isEqualTo(first.getState());
        assertThat(Thread.State.TERMINATED).isEqualTo(second.getState());
    }
}