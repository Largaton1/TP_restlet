package kone.nassara.istic.m1.database;

import kone.nassara.istic.m1.internals.*;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private final int maxPlaces = 25;
    private final List<Client> clients = new ArrayList<>();
    private final StandCuisson standCuisson = new StandCuisson();
    private final Buffet buffet = new Buffet();

    public synchronized boolean isFull() {
        return clients.size() >= maxPlaces;
    }

    public synchronized void enter(Client client) {
        while (isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        clients.add(client);
    }

    public synchronized void leave(Client client) {
        clients.remove(client);
        notifyAll();
    }

    public List<Client> getClients() {
        return new ArrayList<>(clients);
    }

    public Buffet getBuffet() {
        return buffet;
    }

    public StandCuisson getStandCuisson() {
        return standCuisson;
    }
}
