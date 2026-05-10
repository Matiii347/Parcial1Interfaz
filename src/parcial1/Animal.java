/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package parcial1;

public abstract class Animal extends Entidad implements Mortal{
    
    protected int velocidad;
    protected double peso;
    
    public Animal (String nombre, double energia, int edad, boolean viva, int velocidad, double peso) { 

        super(nombre, energia, edad, viva);
        
        this.velocidad = velocidad;
        this.peso = peso;
}

    //Metodo Abstracto:
    public abstract void comer(Ecosistema eco);
    
    //Metodo Concreto:
    public void moverse(){
        System.out.println(getNombre() + "se desplazo");
    }
}
