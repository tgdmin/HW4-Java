package org.example;

public interface Taxi {
    void placeOrder(Order order) throws InterruptedException;
    int id();
    void shutdown() throws  InterruptedException;
}


