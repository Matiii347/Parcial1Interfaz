package parcial1;

abstract public class Entidad {
    private String nombre;
    private double energia;
    private int edad;
    private boolean viva;

    public Entidad(String nombre, double energia, int edad, boolean viva) {
        this.nombre = nombre;
        this.energia = energia;
        this.edad = edad;
        this.viva = viva;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setViva(boolean viva) {
        this.viva = viva;
    }
    
    public void setEnergia(double energia) {
        if (energia <0) {
            this.energia = 0; 
        }else{
           this.energia = energia; 
        }
    }
    
    public double getEnergia() {
        return energia;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public boolean isViva() {
        return viva;
    }
    
    
    public abstract void actuar(Ecosistema eco);
    
    public abstract void mostrarEstado();
    
    void envejecer(){
        this.setEdad(this.getEdad()+1);
        this.setEnergia(this.getEnergia()-1);
    }
    
}
    