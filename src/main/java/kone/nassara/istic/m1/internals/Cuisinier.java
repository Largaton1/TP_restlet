package kone.nassara.istic.m1.internals;


public class Cuisinier extends Thread {
    private final StandCuisson standCuisson;

    public Cuisinier(StandCuisson standCuisson) {
        this.standCuisson = standCuisson;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Client client = standCuisson.prochainClient();
                System.out.println("Cuisinier commence à cuire pour le client " + client.getId());
                Thread.sleep(1000 + (int) (Math.random() * 1000));
                synchronized (client) {
                    client.notify();
                }
                System.out.println("Plat du client " + client.getId() + " est prêt !");
            }
        } catch (InterruptedException e) {
            System.out.println("Cuisinier interrompu.");
        }
    }
}
