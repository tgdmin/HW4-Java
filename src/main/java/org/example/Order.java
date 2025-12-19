package org.example;

public final class Order {
    private final long id;
    private final String from;
    private final String to;

    public Order(long id, String from, String to) {
        this.id = id;
        this.from = from;
        this.to = to;
    }
    public long GetId() {
        return id;
    }
    public String GetFrom() {
        return from;
    }
    public String GetTo() {
        return to;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", from=" + from + ", to=" + to + '}';
    }
}
