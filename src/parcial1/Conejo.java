package parcial1;


public class Conejo extends Animal implements Reproducible{

    public Conejo (String nombre, double energia, int edad, boolean viva, int velocidad, double peso){
        super(nombre, energia, edad, viva, velocidad, peso);
}
    
    @Override
    public void actuar(Ecosistema eco){
    
        comer(eco);
        reproducirse(eco);
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
            
            plantaEncontrada.serComida();
            
            setEnergia(getEnergia() + 20);
            
            System.out.println(getNombre() + " comio una planta.");
            
        } else {
            
            setEnergia(getEnergia() - 15);
            
            System.out.println(getNombre() + " no encontro comida y perdio energia.");
        }
    }
    
    @Override
    public void mostrarEstado(){
    
        System.out.println("Nombre: " + getNombre());
        
        System.out.println("Energia: " + getEnergia());        
        if (getEnergia() < 20){
            System.out.println("Esta en peligro");
        }else {
            System.out.println("Esta estable");
        }
    }
    
    @Override
    public void reproducirse(Ecosistema eco){
    
        int cantidadConejos = 0;
        
        for (Animal a: eco.getConejos()){
        
            if(a instanceof Conejo && a.estaVivo()){
                cantidadConejos++;
            }
        }
        
        if (getEnergia() > 60 && cantidadConejos >= 2){
        
            Conejo bebe = new Conejo(
                    "ConejoBebe",
                    40,
                    0,
                    true,
                    velocidad,
                    peso
            );
            
            eco.getConejos().add(bebe);
            
            System.out.println(getNombre() + " se reprodujo.");
        }
    }

    @Override
    public void intentarReproduccion(Ecosistema eco) {
        if (puedeReproducirse()) {
            reproducirse(eco);
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

    @Override
    public boolean verificarMuerte() {
        return super.verificarMuerte(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public boolean puedeReproducirse() {
    return isViva() && getEnergia() > 60;
    }
}


