package parcial1;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Lobo extends Animal{

    private int exitosCaza;


    public Lobo (String nombre, double energia, int edad, boolean viva, int velocidad, double peso){

        super(nombre,energia,edad,viva,velocidad,peso);

        this.exitosCaza = 0;

    }

     public int getExitosCaza() {
        return exitosCaza;
    }

    public void setExitosCaza(int exitosCaza) {
        this.exitosCaza = exitosCaza;
    }

    @Override public void actuar(Ecosistema eco){

        if(!isViva()){
             return;
        }

        Random random = new Random();

        int intento = random.nextInt(121);

        double probabilidadExito = getEnergia();

        if (eco.getClimaActual() == Clima.INVIERNO){

            probabilidadExito = probabilidadExito + 20;
        }
        
         if (eco.getClimaActual() == Clima.LLUVIOSO){

            probabilidadExito = probabilidadExito - 5;
        }

        if (probabilidadExito > 100){

            probabilidadExito = 100;

        }

        if (probabilidadExito >= intento){

            List<Conejo> conejosVivos = eco.getConejos().stream().filter(c -> c.isViva()).collect(Collectors.toList());

            if (!conejosVivos.isEmpty()) {

                setExitosCaza(getExitosCaza() + 1);

                comer(eco);

            }

            else 
            {
                setEnergia(getEnergia() - 15);
                eco.registrarEvento(getNombre() + " intentó cazar pero no hay conejos vivos y perdió 15 de energia.");
            }
        }
        else{

            setEnergia(probabilidadExito-15);

            eco.registrarEvento("El lobo "+ getNombre()+ " Fallo al cazar un conejo y perdio 15 de energia");
        }

    }

        @Override public void comer(Ecosistema eco){

        List<Conejo> conejosVivos = eco.getConejos().stream().filter(c -> c.isViva()).collect(Collectors.toList());

        if(!conejosVivos.isEmpty()){

            int random = new Random().nextInt(conejosVivos.size());

            Conejo conejoCazado = conejosVivos.get(random);

            conejoCazado.setViva(false);

            eco.registrarMuerte("conejo");

            eco.registrarEvento("El lobo " + getNombre() + " cazó y devoró a " + conejoCazado.getNombre());

            setEnergia(getEnergia() + 30);
        }
    }

    @Override public void mostrarEstado() {

    System.out.println("Lobo: " + getNombre() + " | Energía: " + getEnergia() + " | Cacerías: " + exitosCaza);

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