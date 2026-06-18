package parcial1.modelo;

import java.io.Serializable;
import parcial1.modelo.Ecosistema;

abstract public class Entidad implements Serializable{
    private String nombre;
    private double energia;
    private int edad;
    private boolean viva;
    private int fila = -1;
    private int columna = -1;

    public Entidad(String nombre, double energia, int edad, boolean viva) {
        this.nombre = nombre;
        this.energia = energia;
        this.edad = edad;
        this.viva = viva;
    }

    public int getFila() { return fila; }
    public void setFila(int fila) { this.fila = fila; }
    public int getColumna() { return columna; }
    public void setColumna(int columna) { this.columna = columna; }

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
            this.viva=false;
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
    