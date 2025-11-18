package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(() -> {});
        Thread second = new Thread(() -> {});
        System.out.printf("%s%n%s%n", first.getName(), second.getName());
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED &&
                second.getState() != Thread.State.TERMINATED) {}
        System.out.println("Работа завершена");
    }
}
