package parcial1.modelo;

import parcial1.modelo.Ecosistema;
import parcial1.modelo.Clima;
import java.util.Random;

public class Planta extends Entidad implements Reproducible{
    private int tamanio;

    public Planta(int tamanio, String nombre, double energia, int edad, boolean viva) {
        super(nombre, energia, edad, viva);
        this.tamanio = (tamanio < 1) ? 1 : (tamanio > 5) ? 5 : tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = (tamanio < 1) ? 1 : (tamanio > 5) ? 5 : tamanio;
    }

    public int getTamanio() {
        return tamanio;
    }
    
    
    @Override
    public void actuar(Ecosistema eco) {
        if (eco.getClimaActual() != Clima.INVIERNO) {
            intentarReproduccion(eco);
        }
    }

    @Override
    public void mostrarEstado() {
        System.out.println("Nombre: "+getNombre()+",tamanio: "+getTamanio()+" y su energia es de: "+getEnergia());
    }
    
    public int serComida(){
        int valorN=getTamanio()*10;
        this.setEnergia(0);
        this.setViva(false);
        return valorN;
    }

    @Override
    public void reproducirse(Ecosistema eco) {
        if (eco.getClimaActual() == Clima.INVIERNO) return; // Invierno: no se reproducen

        int nuevas = 1; // Default
        
        // Modificador según clima
        switch(eco.getClimaActual()) {
            case SOLEADO: 
                // x1.5 (probabilidad 50% de que nazca 1 más)
                if (new Random().nextBoolean()) nuevas = 2; 
                break;
            case LLUVIOSO: 
                // x2 
                nuevas = 2; 
                break;
            case SEQUIA: 
                // x0.5 (probabilidad 50% de que nazca 1, si no 0)
                nuevas = (new Random().nextBoolean()) ? 1 : 0; 
                break;
        }
        
        for (int i = 0; i < nuevas; i++) {
            if (eco.getEntidadesVivas().size() >= 225) break;
            Planta nuevaP = new Planta(3, getNombre() + "_H", 30, 0, true);
            eco.getPlantas().add(nuevaP);
            eco.registrarNacimiento("planta");
            eco.registrarEvento(getNombre() + " generó una nueva planta hija.");
        }
        
        setEnergia(getEnergia() - 15); // Costo de reproducirse
    }

    @Override
    public boolean puedeReproducirse() {
        return isViva() && getEnergia() >=30;
    }

   @Override
    public void intentarReproduccion(Ecosistema eco) {
        if (eco.getEntidadesVivas().size() < 225) {
            if (puedeReproducirse()) {
                reproducirse(eco);
            }
        }
    }
    
}
