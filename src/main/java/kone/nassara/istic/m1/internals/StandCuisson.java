package kone.nassara.istic.m1.internals;

import java.util.LinkedList;
import java.util.Queue;

public class StandCuisson {
    private final Queue<Client> fileCuisson = new LinkedList<>();

    public synchronized void ajouterClient(Client client) {
        fileCuisson.add(client);
        notifyAll();
    }

    public synchronized Client prochainClient() throws InterruptedException {
        while (fileCuisson.isEmpty()) {
            wait();
        }
        return fileCuisson.poll();
    }
}
