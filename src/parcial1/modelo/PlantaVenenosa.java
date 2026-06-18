package parcial1.modelo;

/**
 *
 * @author Usuario
 */
public class PlantaVenenosa extends Planta implements Peligroso {
    
    public PlantaVenenosa(int tamanio, String nombre, double energia, int edad, boolean viva) {
        super(tamanio, nombre, energia, edad, viva);
    }

    @Override
    public int serComida() {
         this.setEnergia(0);
         this.setViva(false);
         return -50;
    }
    
    @Override
    public int getNivelPeligro() {
        return 3;
    }
}
