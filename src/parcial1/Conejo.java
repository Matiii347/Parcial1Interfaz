package parcial1;


public class Conejo extends Animal implements Reproducible{


    public Conejo (String nombre, double energia, int edad, boolean viva, int velocidad, double peso){
        super(nombre, energia, edad, viva, velocidad, peso);
    }

    @Override
    public void actuar(Ecosistema eco){

        comer(eco);
        intentarReproduccion(eco);
    }

    @Override
    public void comer(Ecosistema eco){

        Planta plantaEncontrada = null;

        for (Planta p : eco.getPlantas()){
            if (p.isViva()){
                plantaEncontrada = p;
                break;
            }
        }

        if (plantaEncontrada != null){

            int valorNutritivo = plantaEncontrada.serComida();
            setEnergia(getEnergia() + valorNutritivo);
            eco.registrarMuerte("planta");
            eco.registrarEvento(getNombre() + " comió una planta y ganó " + valorNutritivo + " de energía.");

        } else {

            setEnergia(getEnergia() - 15);
            System.out.println(getNombre() + " no encontro comida y perdio energia.");
        }
    }

    @Override
    public void mostrarEstado(){

        System.out.println("Nombre: " + getNombre() + " | Energía: " + getEnergia() );

    }
@Override
    public boolean puedeReproducirse() {
        return isViva() && getEnergia() > 60;
    }

    @Override
    public void reproducirse(Ecosistema eco){

        int cantidadConejos = 0;

        for (Animal a: eco.getConejos()){

            if(a.estaVivo()){
                cantidadConejos++;
            }
        }

         if (cantidadConejos >= 2 && new java.util.Random().nextInt(100) < 30){
            String nombreBebe = "ConejoBebe_" + (eco.getConejos().size() + 1);
            Conejo bebe = new Conejo(nombreBebe, 40, 0, true, velocidad, peso);
            eco.getConejos().add(bebe);
            eco.registrarNacimiento("conejo");
            eco.registrarEvento(getNombre() + " se reprodujo y nació " + nombreBebe + ".");
        }
    }
    
    @Override
    public boolean estaVivo() {
    return isViva();
    }

    @Override
    public void morir() {
    setViva(false);
        setEnergia(0);
    }

}