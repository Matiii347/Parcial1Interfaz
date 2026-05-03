package parcial1;

public class Planta extends Entidad{
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
        
        
    }

    @Override
    public void mostrarEstado() {
        System.out.println("Nombre :"+getNombre()+",tamanio: "+getTamanio()+" y su energia es de: "+getEnergia());
    }
    
    public int serComida(){
        int valorN=getTamanio()*10;
        this.setEnergia(0);
        return valorN;
    }
    
}
