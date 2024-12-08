package kone.nassara.istic.m1.internals;

import kone.nassara.istic.m1.database.Restaurant;

public class Client extends Thread {
    private final int id;
    private final Restaurant restaurant;
    private ClientState state;

    public Client(int id, Restaurant restaurant) {
        this.id = id;
        this.restaurant = restaurant;
        this.state = ClientState.WAITING_TO_ENTER;
    }

    public int getClientId() {
        return id;
    }

    public ClientState getClientState() {
        return state;
    }

    public void setState(ClientState state) {
        this.state = state;
    }

    @Override
    public void run() {
        try {
            setState(ClientState.WAITING_TO_ENTER);
            restaurant.enter(this);

            setState(ClientState.AT_THE_BUFFET);
            for (Compartiment compartiment : Compartiment.values()) {
                boolean servi = false;
                while (!servi) {
                    servi = restaurant.getBuffet().servir(compartiment, 100);
                    if (!servi) {
                        Thread.sleep(100);
                    }
                }
            }

            setState(ClientState.WAITING_FOR_THE_COOK);
            restaurant.getStandCuisson().ajouterClient(this);

            synchronized (this) {
                wait();
            }

            setState(ClientState.EATING);
            Thread.sleep(1000);

            setState(ClientState.OUT);
            restaurant.leave(this);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
