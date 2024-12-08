package kone.nassara.istic.m1.internals;


public class EmployeBuffet extends Thread {
    private final Buffet buffet;
    private final int delai;

    public EmployeBuffet(Buffet buffet, int delai) {
        this.buffet = buffet;
        this.delai = delai;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                for (Compartiment compartiment : Compartiment.values()) {
                    synchronized (buffet) {
                        if (buffet.getQuantite(compartiment) <= 100) {
                            buffet.recharger(compartiment);
                        } else {
                            System.out.println("Employé : Quantité suffisante dans " + compartiment + " : " + buffet.getQuantite(compartiment) + "g.");
                        }
                    }
                }
                Thread.sleep(delai);
            }
        } catch (InterruptedException e) {
            System.out.println("Employé Buffet interrompu.");
            Thread.currentThread().interrupt();
        }
    }
}
