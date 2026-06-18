package parcial1.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import parcial1.modelo.Clima;
import parcial1.modelo.Ecosistema;
import parcial1.modelo.Planta;
import parcial1.modelo.PlantaVenenosa;
import parcial1.modelo.Conejo;
import parcial1.modelo.Lobo;
import parcial1.dao.EcosistemaDAO;
import parcial1.dao.EcosistemaDAOImpl;
import parcial1.vista.PantallaSimulador;
import parcial1.vista.EcosistemaGridPanel;
import parcial1.vista.PanelReporte;

public class SimuladorController implements ActionListener {

    private Ecosistema modelo;
    private PantallaSimulador configVista;
    private EcosistemaGridPanel simVista;
    private EcosistemaDAO dao;
    private Timer timerAuto;
    private boolean simIniciada = false;

    public SimuladorController(Ecosistema modelo, PantallaSimulador configVista) {
        this.modelo = modelo;
        this.configVista = configVista;
        this.dao = new EcosistemaDAOImpl();

        // Inicializar Timer para avance automático (cada 1.5 segundos)
        this.timerAuto = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                avanzarTurno();
            }
        });

        // Conectar botones de la Pantalla de Configuración
        this.configVista.btnIniciarSim.addActionListener(this);
        this.configVista.btnCargarSim.addActionListener(this);
    }

    private void prepararYMostrarSimVista() {
        if (simVista != null) {
            simVista.dispose();
        }
        simVista = new EcosistemaGridPanel();
        
        // Registrar action listeners en simVista
        simVista.btnAvanzarTurno.addActionListener(this);
        simVista.btnCambiarClima.addActionListener(this);
        simVista.btnAgregarEspecie.addActionListener(this);
        simVista.btnTerminarSim.addActionListener(this);
        
        simVista.mnuNueva.addActionListener(this);
        simVista.mnuGuardar.addActionListener(this);
        simVista.mnuCargar.addActionListener(this);
        simVista.mnuAutoSim.addActionListener(this);
        simVista.mnuReporte.addActionListener(this);
        simVista.mnuEstadoActual.addActionListener(this);
        
        // Actualizar interfaz y ocultar configVista, mostrar simVista
        simVista.actualizarInterfaz(modelo);
        configVista.setVisible(false);
        simVista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        // 1. Botón Iniciar Simulación
        if (src == configVista.btnIniciarSim) {
            int plantas = (Integer) configVista.spnPlantas.getValue();
            int conejos = (Integer) configVista.spnConejos.getValue();
            int lobos = (Integer) configVista.spnLobos.getValue();
            int turnos = (Integer) configVista.spnTurnos.getValue();
            Clima clima = (Clima) configVista.cmbClima.getSelectedItem();

            iniciarNuevaSimulacion(lobos, conejos, plantas, turnos, clima);
        }
        
        // 2. Botón Cargar Simulación (Configuración o Menú)
        else if (src == configVista.btnCargarSim || (simVista != null && src == simVista.mnuCargar)) {
            if (simIniciada) {
                int res = JOptionPane.showConfirmDialog(simVista, 
                    "Hay una simulación en curso. ¿Desea descartarla y cargar la partida guardada?", 
                    "Confirmar Carga", JOptionPane.YES_NO_OPTION);
                if (res != JOptionPane.YES_OPTION) return;
            }
            cargarPartida();
        }

        // 3. Botón Avanzar Turno
        else if (simVista != null && src == simVista.btnAvanzarTurno) {
            avanzarTurno();
        }

        // 4. Menú Simulación Automática (Auto)
        else if (simVista != null && src == simVista.mnuAutoSim) {
            if (timerAuto.isRunning()) {
                timerAuto.stop();
                simVista.mnuAutoSim.setText("Simulación Auto / Pausar 🔄");
            } else {
                timerAuto.start();
                simVista.mnuAutoSim.setText("Pausar Simulación ⏸");
            }
        }

        // 5. Menú Guardar
        else if (simVista != null && src == simVista.mnuGuardar) {
            guardarPartida();
        }

        // 6. Menú Nueva Simulación
        else if (simVista != null && src == simVista.mnuNueva) {
            int res = JOptionPane.showConfirmDialog(simVista, 
                "¿Desea volver al menú principal para iniciar otra simulación?", 
                "Nueva Simulación", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                timerAuto.stop();
                simVista.mnuAutoSim.setText("Simulación Auto / Pausar 🔄");
                simIniciada = false;
                simVista.dispose();
                simVista = null;
                configVista.setVisible(true);
            }
        }

        // 7. Botón Cambiar Clima
        else if (simVista != null && src == simVista.btnCambiarClima) {
            Clima[] climas = Clima.values();
            Clima nuevoClima = (Clima) JOptionPane.showInputDialog(simVista,
                    "Seleccione el nuevo clima para el ecosistema:",
                    "Cambiar Clima",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    climas,
                    modelo.getClimaActual());
            if (nuevoClima != null) {
                modelo.cambiarClima(nuevoClima);
                simVista.actualizarInterfaz(modelo);
            }
        }

        // 8. Botón Agregar Especie
        else if (simVista != null && src == simVista.btnAgregarEspecie) {
            if (modelo.getEntidadesVivas().size() >= 225) {
                JOptionPane.showMessageDialog(simVista, 
                        "No se pueden agregar más seres vivos. La grilla está llena.", 
                        "Grilla Llena", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String[] entidades = {"Planta", "Conejo", "Lobo"};
            String seleccionEntidad = (String) JOptionPane.showInputDialog(simVista,
                    "¿Qué especie desea agregar?",
                    "Agregar Especie",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    entidades,
                    entidades[0]);
            
            if (seleccionEntidad != null) {
                seleccionEntidad = seleccionEntidad.toLowerCase();
                
                // Validación de lobos
                if (seleccionEntidad.equals("lobo") && modelo.getLobos().stream().filter(Lobo::isViva).count() >= 5) {
                    JOptionPane.showMessageDialog(simVista, 
                            "No se pueden agregar más de 5 lobos en total por motivos de equilibrio.", 
                            "Acción Cancelada", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                modelo.agregarEntidad(seleccionEntidad);
                modelo.asegurarPosiciones();
                simVista.actualizarInterfaz(modelo);
            }
        }

        // 9. Botón Terminar Simulación o Menú Reporte
        else if (simVista != null && (src == simVista.btnTerminarSim || src == simVista.mnuReporte)) {
            timerAuto.stop();
            String causa = "Finalizada por el usuario.";
            JOptionPane.showMessageDialog(simVista, 
                    "=== SIMULACIÓN FINALIZADA ===\nCausa: " + causa, 
                    "Fin de la Simulación", JOptionPane.INFORMATION_MESSAGE);
            mostrarReporteFinal(causa);
        }

        // 10. Menú Estado Actual
        else if (simVista != null && src == simVista.mnuEstadoActual) {
            if (modelo == null) {
                JOptionPane.showMessageDialog(simVista, "No hay ninguna simulación activa.", "Estado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            long pVivas = modelo.getPlantas().stream().filter(Planta::isViva).count();
            long cVivas = modelo.getConejos().stream().filter(Conejo::isViva).count();
            long lVivas = modelo.getLobos().stream().filter(Lobo::isViva).count();
            String msg = String.format("Estado actual:\n- Clima: %s\n- Turno: %d\n- Plantas vivas: %d\n- Conejos vivos: %d\n- Lobos vivos: %d",
                    modelo.getClimaActual(), modelo.getTurnoActual(), pVivas, cVivas, lVivas);
            JOptionPane.showMessageDialog(simVista, msg, "Estado Actual", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void iniciarNuevaSimulacion(int numLobos, int numConejos, int numPlantas, int totalTurnos, Clima climaInicial) {
        Random rand = new Random();
        ArrayList<Planta> plantasIniciales = new ArrayList<>();
        ArrayList<Conejo> conejosIniciales = new ArrayList<>();
        ArrayList<Lobo> lobosIniciales = new ArrayList<>();
        
        // Crear las entidades iniciales con energía aleatoria razonable
        for (int i = 0; i < numPlantas; i++) {
            if (rand.nextInt(100) < 20) {
                plantasIniciales.add(new PlantaVenenosa(3, "P_Venenosa_" + (i+1), 30 + rand.nextInt(51), 0, true));
            } else {
                plantasIniciales.add(new Planta(3, "Planta_" + (i+1), 30 + rand.nextInt(51), 0, true));
            }
        }
        for (int i = 0; i < numConejos; i++) {
            conejosIniciales.add(new Conejo("Conejo_" + (i+1), 50 + rand.nextInt(51), 0, true, 10, 5.0));
        }
        for (int i = 0; i < numLobos; i++) {
            lobosIniciales.add(new Lobo("Lobo_" + (i+1), 80 + rand.nextInt(71), 0, true, 15, 30.0));
        }

        modelo = new Ecosistema(plantasIniciales, conejosIniciales, lobosIniciales, climaInicial);
        modelo.setTotalTurnos(totalTurnos);
        modelo.asegurarPosiciones();

        simIniciada = true;
        
        // Crear y mostrar simVista
        prepararYMostrarSimVista();
    }

    private void avanzarTurno() {
        if (modelo == null || simVista == null) return;

        // Validar fin de simulación antes de avanzar
        if (comprobarFinDeJuego()) {
            return;
        }

        // Ejecutar turno del modelo
        modelo.procesarTurno();

        // Actualizar la interfaz gráfica
        simVista.actualizarInterfaz(modelo);

        // Comprobar fin de juego después del turno
        if (comprobarFinDeJuego()) {
            return;
        }

        // Sistema de Intervención cada 3 turnos
        int proximoTurno = modelo.getTurnoActual();
        if (proximoTurno > 1 && proximoTurno % 3 == 1) {
            timerAuto.stop();
            simVista.mnuAutoSim.setText("Simulación Auto / Pausar 🔄");
            
            gestionarIntervencion();
        }
    }

    private void gestionarIntervencion() {
        if (simVista == null) return;
        String[] opciones = {"Cambiar Clima", "Agregar Entidad", "Solo Avanzar"};
        int seleccion = JOptionPane.showOptionDialog(simVista,
                "--- SISTEMA DE INTERVENCIÓN ---\n¿Qué desea hacer en este turno?",
                "Intervención del Usuario",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]);

        if (seleccion == 0) { // Cambiar Clima
            Clima[] climas = Clima.values();
            Clima nuevoClima = (Clima) JOptionPane.showInputDialog(simVista,
                    "Seleccione el nuevo clima para el ecosistema:",
                    "Cambiar Clima",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    climas,
                    modelo.getClimaActual());
            if (nuevoClima != null) {
                int conf = JOptionPane.showConfirmDialog(simVista, 
                        "¿Confirmar cambio de clima a " + nuevoClima + "?", 
                        "Confirmar Acción", JOptionPane.YES_NO_OPTION);
                if (conf == JOptionPane.YES_OPTION) {
                    modelo.cambiarClima(nuevoClima);
                    simVista.actualizarInterfaz(modelo);
                }
            }
        } else if (seleccion == 1) { // Agregar Entidad
            String[] entidades = {"Planta", "Conejo", "Lobo"};
            String seleccionEntidad = (String) JOptionPane.showInputDialog(simVista,
                    "¿Qué entidad desea agregar?",
                    "Agregar Entidad",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    entidades,
                    entidades[0]);
            
            if (seleccionEntidad != null) {
                seleccionEntidad = seleccionEntidad.toLowerCase();
                
                // Validación de lobos
                if (seleccionEntidad.equals("lobo") && modelo.getLobos().stream().filter(Lobo::isViva).count() >= 5) {
                    JOptionPane.showMessageDialog(simVista, 
                            "No se pueden agregar más de 5 lobos en total por motivos de equilibrio.", 
                            "Acción Cancelada", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int conf = JOptionPane.showConfirmDialog(simVista, 
                        "¿Confirmar que desea añadir un/a " + seleccionEntidad + "?", 
                        "Confirmar Acción", JOptionPane.YES_NO_OPTION);
                
                if (conf == JOptionPane.YES_OPTION) {
                    modelo.agregarEntidad(seleccionEntidad);
                    modelo.asegurarPosiciones();
                    simVista.actualizarInterfaz(modelo);
                }
            }
        }
    }

    private boolean comprobarFinDeJuego() {
        if (modelo == null || simVista == null) return false;

        boolean colapso = modelo.ecosistemaColapsado();
        boolean finTurnos = (modelo.getTurnoActual() - 1) >= modelo.getTotalTurnos();

        if (colapso || finTurnos) {
            timerAuto.stop();
            simVista.mnuAutoSim.setText("Simulación Auto / Pausar 🔄");
            simVista.btnAvanzarTurno.setEnabled(false);
            simVista.btnCambiarClima.setEnabled(false);
            simVista.btnAgregarEspecie.setEnabled(false);
            simVista.btnTerminarSim.setEnabled(false);

            String causa = "";
            if (colapso) {
                long p = modelo.getPlantas().stream().filter(Planta::isViva).count();
                long c = modelo.getConejos().stream().filter(Conejo::isViva).count();
                long l = modelo.getLobos().stream().filter(Lobo::isViva).count();
                
                String extinta = "";
                if (p == 0) extinta = "Plantas";
                else if (c == 0) extinta = "Conejos";
                else if (l == 0) extinta = "Lobos";
                
                causa = "Colapso del ecosistema (la población de " + extinta + " se extinguió).";
            } else {
                causa = "Se ha completado el límite máximo de " + modelo.getTotalTurnos() + " turnos.";
            }

            JOptionPane.showMessageDialog(simVista, 
                    "=== SIMULACIÓN FINALIZADA ===\nCausa: " + causa, 
                    "Fin de la Simulación", JOptionPane.INFORMATION_MESSAGE);

            mostrarReporteFinal(causa);
            return true;
        }
        return false;
    }

    private void mostrarReporteFinal(String causa) {
        timerAuto.stop();
        
        PanelReporte panelReporte = new PanelReporte(modelo, causa);
        panelReporte.btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                panelReporte.dispose();
            }
        });
        panelReporte.setVisible(true);
    }

    private void cargarPartida() {
        Ecosistema ecoCargado = dao.cargarEstado();
        if (ecoCargado != null) {
            modelo = ecoCargado;
            modelo.asegurarPosiciones(); // Garantiza que tengan coordenadas válidas
            simIniciada = true;
            
            prepararYMostrarSimVista();
            
            JOptionPane.showMessageDialog(simVista, "Partida cargada exitosamente.", "Carga Completa", JOptionPane.INFORMATION_MESSAGE);
            
            comprobarFinDeJuego();
        } else {
            JOptionPane.showMessageDialog(simIniciada ? simVista : configVista, 
                    "No se encontró ninguna partida guardada anterior.", 
                    "Error de Carga", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void guardarPartida() {
        if (simVista == null) return;
        if (modelo == null) {
            JOptionPane.showMessageDialog(simVista, "No hay ninguna simulación activa para guardar.", "Error al Guardar", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        dao.guardarEstado(modelo);
        JOptionPane.showMessageDialog(simVista, "El estado del ecosistema ha sido guardado.", "Guardado Exitoso", JOptionPane.INFORMATION_MESSAGE);
    }
}