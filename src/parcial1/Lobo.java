package parcial1;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Lobo extends Animal implements Reproducible{
    
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
        
        int intento = random.nextInt(101);
        
        double probabilidadExito = getEnergia();
        
        if (eco.getClimaActual() == Clima.INVIERNO){
            
            probabilidadExito = probabilidadExito + 20;
        }
        
        if (probabilidadExito > 100){
            
            probabilidadExito = 100;
            
        }
        
        if (probabilidadExito >= intento){
            
            setExitosCaza(getExitosCaza()+1);
            
            comer(eco);
        }
        else{
            
            setEnergia(probabilidadExito-5);
            
            System.out.println("El lobo "+ getNombre()+ " Fallo al cazar un conejo");
        }
         
    }
    
    @Override public void comer(Ecosistema eco){
        
        List<Conejo> conejosVivos = eco.getConejos().stream().filter(c -> c.isViva()).collect(Collectors.toList());
        
        if(!conejosVivos.isEmpty()){
            
            int random = new Random().nextInt(conejosVivos.size());
            
            Conejo conejoCazado = conejosVivos.get(random);
            
            conejoCazado.setViva(false);
            
            System.out.println("El lobo " + getNombre() + " cazó y devoró a " + conejoCazado.getNombre());
            
            setEnergia(getEnergia() + 20);
        }
    }
    
    @Override public void mostrarEstado() {
       
        System.out.println("Estado del Lobo");
        
        System.out.println("Nombre: " + getNombre());
        
        System.out.println("Energía: " + getEnergia());
        
        System.out.println("Cacerías exitosas: " + this.exitosCaza);
    
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

    @Override
    public void reproducirse(Ecosistema eco) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean puedeReproducirse() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void intentarReproduccion(Ecosistema eco) {
        if (puedeReproducirse()) {
            reproducirse(eco);
        }
    }
}
