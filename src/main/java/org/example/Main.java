package org.example;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        int taxiCount = 3;

        // Create taxis + start taxi threads
        List<Taxi_Implement> taxiObjects = new ArrayList<>();
        List<Thread> taxiThreads = new ArrayList<>();

        for (int i = 0; i < taxiCount; i++) {
            Taxi_Implement taxi = new Taxi_Implement(i, 300, 1200); // sleep range in ms
            taxiObjects.add(taxi);

            Thread t = new Thread(taxi, "taxi-" + i);
            taxiThreads.add(t);
            t.start();
        }

        // Create orders
        List<Order> orders = List.of(
                new Order(1, "A", "B"),
                new Order(2, "C", "D"),
                new Order(3, "E", "F"),
                new Order(4, "G", "H"),
                new Order(5, "I", "J"),
                new Order(6, "K", "L")
        );

        // Dispatcher runs alone in its own thread (or could run in main thread)
        Dispatcher dispatcher = new Dispatcher_Implement(new ArrayList<>(taxiObjects), orders);
        Thread dispatcherThread = new Thread(dispatcher, "dispatcher");
        dispatcherThread.start();

        // Wait for dispatcher to finish placing all orders
        dispatcherThread.join();

        // Shutdown taxis AFTER all orders were dispatched
        for (Taxi_Implement taxi : taxiObjects) taxi.shutdown();

        // Wait for taxis to exit
        for (Thread t : taxiThreads) t.join();

        System.out.println("DONE");
    }
}
