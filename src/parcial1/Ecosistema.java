package parcial1;

import java.util.ArrayList;

public class Ecosistema {
    ArrayList<Planta>Plantas;
    ArrayList<Conejo>Conejos;
    ArrayList<Lobo>Lobos;
    Clima climaActual;
    int turnoActaul;

    public Ecosistema(ArrayList<Planta> Plantas, ArrayList<Conejo> Conejos, ArrayList<Lobo> Lobos, Clima climaActual, int turnoActaul) {
        this.Plantas = Plantas;
        this.Conejos = Conejos;
        this.Lobos = Lobos;
        this.climaActual = climaActual;
        this.turnoActaul = turnoActaul;
    }

    public void setPlantas(ArrayList<Planta> Plantas) {
        this.Plantas = Plantas;
    }

    public void setConejos(ArrayList<Conejo> Conejos) {
        this.Conejos = Conejos;
    }

    public void setLobos(ArrayList<Lobo> Lobos) {
        this.Lobos = Lobos;
    }

    public void setClimaActual(Clima climaActual) {
        this.climaActual = climaActual;
    }

    public void setTurnoActaul(int turnoActaul) {
        this.turnoActaul = turnoActaul;
    }

    public ArrayList<Planta> getPlantas() {
        return Plantas;
    }

    public ArrayList<Conejo> getConejos() {
        return Conejos;
    }

    public ArrayList<Lobo> getLobos() {
        return Lobos;
    }

    public Clima getClimaActual() {
        return climaActual;
    }

    public int getTurnoActaul() {
        return turnoActaul;
    }
    
    
    
    
    
}