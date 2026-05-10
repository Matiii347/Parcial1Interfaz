package parcial1;

import java.util.ArrayList;

public class Ecosistema {

    private ArrayList<Planta> plantas;
    private ArrayList<Conejo> conejos;
    private ArrayList<Lobo> lobos;
    private Clima climaActual;
    private int turnoActual;
    private int muertesPlantas = 0, muertesConejos = 0, muertesLobos = 0;
    private int nacimientosPlantas = 0, nacimientosConejos = 0, nacimientosLobos = 0;

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
            System.out.println("Sistema: Se ha añadido una nueva planta."); [cite: 170]
            nacimientosPlantas++;
            break;

        case "conejo":
            //ACA ASUMO Q CONEJO VA A SER ALGO ASI:
            conejos.add(new Conejo("Conejo_" + (conejos.size() + 1), energiaInicial, 0, true));
            System.out.println("Sistema: Se ha añadido un nuevo conejo.");
            nacimientosConejos++;
            break;

        case "lobo":
             //VALIDAR MAXIMO 5 LOBOS EN TODA LA SIMULACION
            if (lobos.size() < 5) {
                lobos.add(new Lobo("Lobo_" + (lobos.size() + 1), energiaInicial, 0, true));
                System.out.println("Sistema: Se ha añadido un nuevo lobo.");
                nacimientosLobos++;
            } else {
                System.out.println("ERROR: No se pueden agregar más de 5 lobos en total."); 
            }
            break;

        default:
            System.out.println("Error: Tipo de entidad '" + tipo + "' no reconocido.");
            break;}
    }

    public void procesarTurno() {
        System.out.println("\n=== TURNO " + turnoActual + " | Clima: " + climaActual + " ==="); [cite: 155, 158]

        // 1. Aplicar efectos del clima (Punto 57 - Debe ser cada turno)
        aplicarEfectosClima();

        // 2. POLIMORFISMO OBLIGATORIO (Punto 103 y 137)
        ArrayList<Reproducible> reproductores = new ArrayList<>();
        reproductores.addAll(plantas);
        reproductores.addAll(conejos);

        for (Reproducible r : reproductores) {
            // En invierno las plantas no se reproducen (Punto 57)
            if (climaActual == Clima.INVIERNO && r instanceof Planta) continue;
            r.intentarReproduccion(this);
        }

        // 3. Acciones de alimentación
        for (Conejo c : new ArrayList<>(conejos)) c.actuar(this);
        for (Lobo l : new ArrayList<>(lobos)) l.actuar(this);

        // 4. Envejecimiento y Muerte (Uso de la interfaz Mortal)
        ArrayList<Entidad> todas = new ArrayList<>();
        todas.addAll(plantas);
        todas.addAll(conejos);
        todas.addAll(lobos);

        for (Entidad e : todas) {
            e.envejecer(); [cite: 72]
            if (e instanceof Mortal m) {
                if (m.verificarMuerte()) { 
                    registrarMuerte(e);
                }
            }
        }

        //Limpieza de listas (Punto 53)
        plantas.removeIf(p -> !p.isViva());
        conejos.removeIf(c -> !c.isViva());
        lobos.removeIf(l -> !l.isViva());

        mostrarEstado(); [cite: 110]
        turnoActual++;
    }

    private void aplicarEfectosClima() {
        for (Conejo c : conejos) {
            switch (climaActual) {
                case SOLEADO -> c.setEnergia(c.getEnergia() + 5);
                case LLUVIOSO -> c.setEnergia(c.getEnergia() + 3);
                case SEQUIA -> c.setEnergia(c.getEnergia() - 5);
                case INVIERNO -> c.setEnergia(c.getEnergia() - 8);
            }
        }
        if (climaActual == Clima.LLUVIOSO) {
            for (Lobo l : lobos)
                l.setEnergia(l.getEnergia() - 5);
        }
    }

    private void registrarMuerte(Entidad e) {
        if (e instanceof Planta)
            muertesPlantas++;
        if (e instanceof Conejo)
            muertesConejos++;
        if (e instanceof Lobo)
            muertesLobos++;
    }

    public void mostrarEstado() {
        System.out.println("Estado: Plantas: " + plantas.size() + 
                           " | Conejos: " + conejos.size() + 
                           " | Lobos: " + lobos.size()); 
    }

    public void cambiarClima(Clima nuevo) {

    }

    public boolean ecosistemaColapsado() {
        
        return plantas.isEmpty() || conejos.isEmpty() || lobos.isEmpty(); 
    }

    public void generarReporteFinal() {
        System.out.println("\n=== REPORTE FINAL DE LA SIMULACIÓN ==="); 
        System.out.println("Causa de fin: " + (ecosistemaColapsado() ? "Colapso" : "Turnos completados")); 
        System.out.println("Total Nacimientos - P: " + nacimientosPlantas + " C: " + nacimientosConejos + " L: " + nacimientosLobos); 
        System.out.println("Total Muertes - P: " + muertesPlantas + " C: " + muertesConejos + " L: " + muertesLobos); 
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
