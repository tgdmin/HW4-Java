package org.example;
import java.util.List;

public final class Dispatcher_Implement implements Dispatcher {
    private final List<Taxi> taxis;
    private final List<Order> orders;

    public Dispatcher_Implement(List<Taxi> taxis, List<Order> orders) {
        if (taxis == null || taxis.isEmpty()) throw new IllegalArgumentException("no taxis");
        if (orders == null) throw new IllegalArgumentException("orders is null");
        this.taxis = taxis;
        this.orders = orders;
    }

    @Override
    public void run() {
        try {
            int idx = 0;
            for (Order order : orders) {
                Taxi taxi = taxis.get(idx);
                taxi.placeOrder(order);          // may block if that taxi is still busy
                idx = (idx + 1) % taxis.size();  // next taxi
            }
            System.out.println("[DISPATCHER] all orders were dispatched");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("[DISPATCHER] interrupted, stopping");
        }
    }
}

