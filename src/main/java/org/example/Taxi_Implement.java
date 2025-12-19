package org.example;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public final class Taxi_Implement implements Taxi, Runnable {
    private static final Order POISON = new Order(-1, "POISON", "POISON");

    private final int id;
    private final BlockingQueue<Order> inbox = new ArrayBlockingQueue<>(1);

    private final long minMillis;
    private final long maxMillis;

    public Taxi_Implement(int id, long minMillis, long maxMillis) {
        if (minMillis < 0 || maxMillis < minMillis) {
            throw new IllegalArgumentException("Bad sleep range");
        }
        this.id = id;
        this.minMillis = minMillis;
        this.maxMillis = maxMillis;
    }

    @Override
    public int id() {
        return id;
    }

    // Called by dispatcher thread. Blocks if taxi is busy.
    @Override
    public void placeOrder(Order order) throws InterruptedException {
        if (order == null) throw new IllegalArgumentException("order is null");
        inbox.put(order);
        System.out.printf("[DISPATCH->TAXI %d] placed %s%n", id, order);
    }

    @Override
    public void shutdown() throws InterruptedException {
        inbox.put(POISON);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Order order = inbox.take();
                if (order.GetId() == -1) {
                    System.out.printf("[TAXI %d] shutting down%n", id);
                    return;
                }

                System.out.printf("[TAXI %d] started %s%n", id, order);

                long sleep = ThreadLocalRandom.current().nextLong(minMillis, maxMillis + 1);
                Thread.sleep(sleep);

                System.out.printf("[TAXI %d] finished %s (took %d ms)%n", id, order, sleep);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.printf("[TAXI %d] interrupted, exiting%n", id);
        }
    }
}
