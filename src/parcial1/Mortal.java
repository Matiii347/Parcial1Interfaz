package parcial1;

public interface Mortal {
    boolean estaVivo();
    void morir();
    double getEnergia();
    
    String getNombre();
    default boolean verificarMuerte() {
        if (estaVivo() && getEnergia() <= 0) {
            morir();
            System.out.println("--- EVENTO: " + getNombre() + " ha muerto por falta de energía.");
            return true;
        }
        return false;
    }
}