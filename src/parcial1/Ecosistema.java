package parcial1;

import java.util.ArrayList;

public class Ecosistema {

    private ArrayList<Planta> plantas;
    private ArrayList<Conejo> conejos;
    private ArrayList<Lobo> lobos;
    private Clima climaActual;
    private int turnoActual;

    public Ecosistema(
            ArrayList<Planta> plantas,
            ArrayList<Conejo> conejos,
            ArrayList<Lobo> lobos,
            Clima climaInicial) {
        this.plantas = plantas;
        this.conejos = conejos;
        this.lobos = lobos;
        this.climaActual = climaInicial;
        this.turnoActual = 1;
    }

    public void agregarEntidad(String tipo) {
        double energiaBase = 50.0; 
        agregarEntidad(tipo, energiaBase);
    }

    public void agregarEntidad(String tipo, double energiaInicial) {
        tipo = tipo.toLowerCase(); 

    switch (tipo) {
        case "planta":
            // Creamos planta con tamaño 3 por defecto [cite: 76]
            plantas.add(new Planta(3, "Planta_" + (plantas.size() + 1), energiaInicial, 0, true));
            System.out.println("Sistema: Se ha añadido una nueva planta.");
            break;

        case "conejo":
            //ACA ASUMIMOS Q CONEJO VA A SER ALGO ASI:
            conejos.add(new Conejo("Conejo_" + (conejos.size() + 1), energiaInicial, 0, true,10,5.0));
            System.out.println("Sistema: Se ha añadido un nuevo conejo.");
            break;

        case "lobo":
            // VALIDAR MAXIMO 5 LOBOS EN TODA LA SIMULACION
            if (lobos.size() < 5) {
                lobos.add(new Lobo("Lobo_" + (lobos.size() + 1), energiaInicial, 0, true,15,30.0));
                System.out.println("Sistema: Se ha añadido un nuevo lobo.");
            } else {
                System.out.println("ERROR: No se pueden agregar más de 5 lobos en total."); 
            }
            break;

        default:
            System.out.println("Error: Tipo de entidad " + " tipo  " + " no reconocido.");
            break;
        }
    }
    public void procesarTurno() {
        
    }

    public void mostrarEstado() {
        
    }

    public void cambiarClima(Clima nuevo) {
        
    }

    
    public boolean ecosistemaColapsado() {
        
        return false;
    }

    public void generarReporteFinal() {
        
    }

    public ArrayList<Planta> getPlantas() {
        return plantas;
    }

    public void setPlantas(ArrayList<Planta> plantas) {
        this.plantas = plantas;
    }

    public ArrayList<Conejo> getConejos() {
        return conejos;
    }

    public void setConejos(ArrayList<Conejo> conejos) {
        this.conejos = conejos;
    }

    public ArrayList<Lobo> getLobos() {
        return lobos;
    }

    public void setLobos(ArrayList<Lobo> lobos) {
        this.lobos = lobos;
    }

    public Clima getClimaActual() {
        return climaActual;
    }

    public void setClimaActual(Clima climaActual) {
        this.climaActual = climaActual;
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public void setTurnoActual(int turnoActual) {
        this.turnoActual = turnoActual;
    }
}