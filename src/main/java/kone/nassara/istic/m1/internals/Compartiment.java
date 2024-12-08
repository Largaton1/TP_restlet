package kone.nassara.istic.m1.internals;

public enum Compartiment {
    POISSON(1000),
    VIANDE(1000),
    LEGUMES(1000),
    NOUILLES(1000);

    private final int capacity;

    Compartiment(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
