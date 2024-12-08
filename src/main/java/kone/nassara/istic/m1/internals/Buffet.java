package kone.nassara.istic.m1.internals;

import java.util.HashMap;
import java.util.Map;

public class Buffet {
    private final Map<Compartiment, Integer> quantites = new HashMap<>();

    public Buffet() {
        for (Compartiment compartiment : Compartiment.values()) {
            quantites.put(compartiment, compartiment.getCapacity());
        }
    }

    public synchronized boolean servir(Compartiment compartiment, int quantite) {
        int restant = quantites.get(compartiment);
        if (restant >= quantite) {
            quantites.put(compartiment, restant - quantite);
            return true;
        }
        return false;
    }

    public synchronized void recharger(Compartiment compartiment) {
        quantites.put(compartiment, compartiment.getCapacity());
    }

    public synchronized int getQuantite(Compartiment compartiment) {
        return quantites.get(compartiment);
    }
    
}
