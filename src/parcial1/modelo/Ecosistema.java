package parcial1.modelo;

import java.io.Serializable;
import parcial1.modelo.Conejo;
import parcial1.modelo.Clima;
import java.util.ArrayList;

public class Ecosistema implements Serializable{

    private ArrayList<Planta> plantas;
    private ArrayList<Conejo> conejos;
    private ArrayList<Lobo> lobos;
    private Clima climaActual;
    private int turnoActual;
    
    // Estadísticas
    private int nacimientosPlantas = 0, muertesPlantas = 0;
    private int nacimientosConejos = 0, muertesConejos = 0;
    private int nacimientosLobos = 0, muertesLobos = 0;
    private int turnoMayorActividad = 0;
    private int maxEventos = -1;
    private int eventosTurnoActual = 0;

    public Ecosistema(ArrayList<Planta> plantas, ArrayList<Conejo> conejos, ArrayList<Lobo> lobos, Clima climaInicial) {
        this.plantas = plantas;
        this.conejos = conejos;
        this.lobos = lobos;
        this.climaActual = climaInicial;
        this.turnoActual = 1;
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

    public void registrarEvento(String evento) {
        System.out.println("  -> " + evento);
        eventosTurnoActual++;
    }

    public void registrarNacimiento(String tipo) {
        if (tipo.equals("planta")) nacimientosPlantas++;
        else if (tipo.equals("conejo")) nacimientosConejos++;
        else if (tipo.equals("lobo")) nacimientosLobos++;
    }

    public void registrarMuerte(String tipo) {
        if (tipo.equals("planta")) muertesPlantas++;
        else if (tipo.equals("conejo")) muertesConejos++;
        else if (tipo.equals("lobo")) muertesLobos++;
    }

    public void agregarEntidad(String tipo) {
        agregarEntidad(tipo, 50.0);
    }

    public void agregarEntidad(String tipo, double energiaInicial) {
        tipo = tipo.toLowerCase(); 
        switch (tipo) {
            case "planta":
                plantas.add(new Planta(3, "Planta_" + (plantas.size() + 1), energiaInicial, 0, true));
                registrarEvento("Intervención: Se ha añadido una nueva planta.");
                break;
            case "conejo":
                conejos.add(new Conejo("Conejo_" + (conejos.size() + 1), energiaInicial, 0, true, 10, 5.0));
                registrarEvento("Intervención: Se ha añadido un nuevo conejo.");
                break;
            case "lobo":
                if (lobos.size() < 5) {
                    lobos.add(new Lobo("Lobo_" + (lobos.size() + 1), energiaInicial, 0, true, 15, 30.0));
                    registrarEvento("Intervención: Se ha añadido un nuevo lobo.");
                } else {
                    System.out.println("ERROR: No se pueden agregar más de 5 lobos en total."); 
                }
                break;
            default:
                System.out.println("Error: Tipo de entidad \"" + tipo + "\" no reconocido.");
                break;
        }
    }
    public void procesarTurno() {
        System.out.println("\n=== INICIO TURNO " + turnoActual + " | Clima: " + climaActual + " ===");
        eventosTurnoActual = 0;

        // 1. Iterar Reproducibles usando Polimorfismo
        ArrayList<Reproducible> reproducibles = new ArrayList<>();
        for (Planta p : plantas) if (p.isViva()) reproducibles.add(p);
        for (Conejo c : conejos) if (c.isViva()) reproducibles.add(c);
        
        for (Reproducible r : reproducibles) {
            r.intentarReproduccion(this);
        }

        // 2. Conejos comen
        for (Conejo c : conejos) {
            if (c.isViva()) c.comer(this);
        }

        // 3. Lobos cazan
        for (Lobo l : lobos) {
            if (l.isViva()) l.actuar(this);
        }

        // 4. Envejecer y morir
        ArrayList<Entidad> todas = new ArrayList<>();
        todas.addAll(plantas);
        todas.addAll(conejos);
        todas.addAll(lobos);

        for (Entidad e : todas) {
            if (e.isViva()) {
                e.envejecer();
                
                // Efectos climáticos de energía para conejos
                if (e instanceof Conejo) {
                    switch (climaActual) {
                        case SOLEADO: e.setEnergia(e.getEnergia() + 5); break;
                        case LLUVIOSO: e.setEnergia(e.getEnergia() + 3); break;
                        case SEQUIA: e.setEnergia(e.getEnergia() - 5); break;
                        case INVIERNO: e.setEnergia(e.getEnergia() - 8); break;
                    }
                }

                // Verificar muerte
                if (e.getEnergia() <= 0) {
                    e.setViva(false);
                    if (e instanceof Planta) registrarMuerte("planta");
                    else if (e instanceof Conejo) registrarMuerte("conejo");
                    else if (e instanceof Lobo) registrarMuerte("lobo");
                    registrarEvento(e.getNombre() + " murió por falta de energía o vejez.");
                }
            }
        }

        // Actualizar estadísticas del turno
        if (eventosTurnoActual > maxEventos) {
            maxEventos = eventosTurnoActual;
            turnoMayorActividad = turnoActual;
        }

        mostrarEstado();
        turnoActual++;
    }

    public void mostrarEstado() {
        long plantasVivas = plantas.stream().filter(Planta::isViva).count();
        long conejosVivos = conejos.stream().filter(Conejo::isViva).count();
        long lobosVivos = lobos.stream().filter(Lobo::isViva).count();
        System.out.println("--- ESTADO: Plantas: " + plantasVivas + " | Conejos: " + conejosVivos + " | Lobos: " + lobosVivos + " ---");
    }

    public void cambiarClima(Clima nuevo) {
        this.climaActual = nuevo;
        registrarEvento("El clima ha cambiado a " + nuevo);
    }

    
    public boolean ecosistemaColapsado() {
        long plantasVivas = plantas.stream().filter(Planta::isViva).count();
        long conejosVivos = conejos.stream().filter(Conejo::isViva).count();
        long lobosVivos = lobos.stream().filter(Lobo::isViva).count();
        return plantasVivas == 0 || conejosVivos == 0 || lobosVivos == 0;
    }

    public void generarReporteFinal(String causa) {
        System.out.println("\n================ REPORTE FINAL ================");
        System.out.println("Causa de fin: " + causa);
        System.out.println("Total de turnos ejecutados: " + (turnoActual - 1));
        System.out.println("Turno de mayor actividad: Turno " + turnoMayorActividad + " (con " + maxEventos + " eventos)");
        
        // Entidades más longevas
        Planta pLongeva = null; Conejo cLongevo = null; Lobo lLongevo = null;
        for (Planta p : plantas) if (pLongeva == null || p.getEdad() > pLongeva.getEdad()) pLongeva = p;
        for (Conejo c : conejos) if (cLongevo == null || c.getEdad() > cLongevo.getEdad()) cLongevo = c;
        for (Lobo l : lobos) if (lLongevo == null || l.getEdad() > lLongevo.getEdad()) lLongevo = l;

        if (pLongeva != null) System.out.println("Planta más longeva: " + pLongeva.getNombre() + " (Edad: " + pLongeva.getEdad() + ")");
        if (cLongevo != null) System.out.println("Conejo más longevo: " + cLongevo.getNombre() + " (Edad: " + cLongevo.getEdad() + ")");
        if (lLongevo != null) System.out.println("Lobo más longevo: " + lLongevo.getNombre() + " (Edad: " + lLongevo.getEdad() + ")");
        
        // Lobo con más cacerías
        Lobo mejorLobo = null;
        for (Lobo l : lobos) {
            if (mejorLobo == null || l.getExitosCaza() > mejorLobo.getExitosCaza()) mejorLobo = l;
        }
        if (mejorLobo != null) System.out.println("Lobo más cazador: " + mejorLobo.getNombre() + " con " + mejorLobo.getExitosCaza() + " éxitos");

        System.out.println("\nEstadísticas Totales:");
        System.out.println("- Plantas: " + nacimientosPlantas + " nacimientos, " + muertesPlantas + " muertes.");
        System.out.println("- Conejos: " + nacimientosConejos + " nacimientos, " + muertesConejos + " muertes.");
        System.out.println("- Lobos: " + nacimientosLobos + " nacimientos, " + muertesLobos + " muertes.");
        System.out.println("===============================================");
    }

    
}