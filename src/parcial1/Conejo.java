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
        
        if (p.estaViva()){
            plantaEncontrada = p;
            break;
        }
        }
        
        if (plantaEncontrada != null){
            
            plantaEncontrada.morir();
            
            energia += 20;
            
            System.out.println(getNombre() + " comio una planta.");
            
        } else {
            
            energia -= 15;
            
            System.out.println(getNombre() + " no encontro comida y perdio energia.");
        }
    }
    
    @Override
    public void mostrarEstado(){
    
        System.out.println("Nombre: " + getNombre());
        
        System.out.println("Energia: " + energia);
        
        if (energia < 20){
            System.out.println("Esta en peligro");
        }else {
            System.out.println("Esta estable");
        }
    }
    
    @Override
    public void reproducirse(Ecosistema eco){
    
        int cantidadConejos = 0;
        
        for (Animal a: eco.getAnimales()){
        
            if(a instanceof Conejo && a.estaVivo()){
                cantidadConejos++;
            }
        }
        
        if (energia > 60 && cantidadConejos >= 2){
        
            Conejo bebe = new Conejo(
                    "ConejoBebe",
                    40,
                    true,
                    velocidad,
                    peso
            );
            
            eco.agregarAnimal(bebe);
            
            System.out.println(getNombre() + " se reprodujo.");
        }
    }
}
