package parcial1.vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import parcial1.modelo.Ecosistema;
import parcial1.modelo.Entidad;
import parcial1.modelo.Planta;
import parcial1.modelo.PlantaVenenosa;
import parcial1.modelo.Conejo;
import parcial1.modelo.Lobo;

public class EcosistemaGridPanel extends javax.swing.JFrame {

    private DefaultTableModel tableModel;
    private DefaultListModel<String> listModelEventos;

    // Iconos de animación para la leyenda
    private ImageIcon iconLobo;
    private ImageIcon iconLoboExtincion;
    private ImageIcon iconConejo;
    private ImageIcon iconConejoExtincion;
    private ImageIcon iconPlanta;
    private ImageIcon iconPlantaExtincion;
    private JLabel lblGifLobo;
    private JLabel lblGifConejo;
    private JLabel lblGifPlanta;

    public EcosistemaGridPanel() {
        initComponents();
        
        // Cargar los GIFs animados para la leyenda
        cargarIconosAnimados();
        configurarLeyendaAnimada();
        
        // Configurar modelo de la JTable
        String[] columnas = {"Tipo", "Nombre", "Energía", "Edad"};
        tableModel = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblEntidades.setModel(tableModel);
        
        // Limpiar lista de eventos al iniciar
        listModelEventos = new DefaultListModel<>();
        lstEventos.setModel(listModelEventos);
        
        // Centrar ventana
        setLocationRelativeTo(null);
    }

    private ImageIcon cargarGif(String nombre) {
        java.net.URL url = getClass().getResource("/parcial1/animaciones gif/" + nombre);
        if (url != null) {
            ImageIcon original = new ImageIcon(url);
            Image scaled = original.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
            return new ImageIcon(scaled);
        }
        return null;
    }

    private void cargarIconosAnimados() {
        iconLobo = cargarGif("lobo.gif");
        iconLoboExtincion = cargarGif("loboextincion.gif");
        iconConejo = cargarGif("conejo.gif");
        iconConejoExtincion = cargarGif("conejoextincion.gif");
        iconPlanta = cargarGif("planta.gif");
        iconPlantaExtincion = cargarGif("plantaextincion.gif");
    }

    private void configurarLeyendaAnimada() {
        // Reemplazar los paneles de color por JLabels con GIFs animados
        lblGifLobo = new JLabel();
        lblGifConejo = new JLabel();
        lblGifPlanta = new JLabel();

        if (iconLobo != null) { lblGifLobo.setIcon(iconLobo); iconLobo.setImageObserver(lblGifLobo); }
        if (iconConejo != null) { lblGifConejo.setIcon(iconConejo); iconConejo.setImageObserver(lblGifConejo); }
        if (iconPlanta != null) { lblGifPlanta.setIcon(iconPlanta); iconPlanta.setImageObserver(lblGifPlanta); }

        lblGifLobo.setPreferredSize(new Dimension(30, 30));
        lblGifConejo.setPreferredSize(new Dimension(30, 30));
        lblGifPlanta.setPreferredSize(new Dimension(30, 30));

        // Reemplazar los marcadores de color cuadrados por los GIFs
        pnlLeyenda.removeAll();
        pnlLeyenda.add(lblGifLobo);
        pnlLeyenda.add(lblLoboLeg);
        pnlLeyenda.add(lblGifConejo);
        pnlLeyenda.add(lblConejoLeg);
        pnlLeyenda.add(lblGifPlanta);
        pnlLeyenda.add(lblPlantaLeg);
        pnlLeyenda.revalidate();
        pnlLeyenda.repaint();
    }

    public void actualizarInterfaz(Ecosistema eco) {
        if (eco == null) return;

        // Actualizar contadores
        lblTurnoVal.setText(String.valueOf(eco.getTurnoActual()));
        lblClimaVal.setText(eco.getClimaActual().name());

        long pVivas = eco.getPlantas().stream().filter(Planta::isViva).count();
        long cVivas = eco.getConejos().stream().filter(Conejo::isViva).count();
        long lVivas = eco.getLobos().stream().filter(Lobo::isViva).count();

        lblPlantasVal.setText(String.valueOf(pVivas));
        lblConejosVal.setText(String.valueOf(cVivas));
        lblLobosVal.setText(String.valueOf(lVivas));

        // Actualizar GIFs de la leyenda según estado de extinción
        if (lblGifLobo != null) {
            if (lVivas == 0 && iconLoboExtincion != null) {
                lblGifLobo.setIcon(iconLoboExtincion);
                lblLoboLeg.setText("Lobo (Extinto)");
                lblLoboLeg.setForeground(new Color(220, 38, 38));
            } else if (iconLobo != null) {
                lblGifLobo.setIcon(iconLobo);
                lblLoboLeg.setText("Lobo (Rojo)");
                lblLoboLeg.setForeground(Color.BLACK);
            }
        }
        if (lblGifConejo != null) {
            if (cVivas == 0 && iconConejoExtincion != null) {
                lblGifConejo.setIcon(iconConejoExtincion);
                lblConejoLeg.setText("Conejo (Extinto)");
                lblConejoLeg.setForeground(new Color(220, 38, 38));
            } else if (iconConejo != null) {
                lblGifConejo.setIcon(iconConejo);
                lblConejoLeg.setText("Conejo (Naranja)");
                lblConejoLeg.setForeground(Color.BLACK);
            }
        }
        if (lblGifPlanta != null) {
            if (pVivas == 0 && iconPlantaExtincion != null) {
                lblGifPlanta.setIcon(iconPlantaExtincion);
                lblPlantaLeg.setText("Planta (Extinta)");
                lblPlantaLeg.setForeground(new Color(220, 38, 38));
            } else if (iconPlanta != null) {
                lblGifPlanta.setIcon(iconPlanta);
                lblPlantaLeg.setText("Planta (Verde)");
                lblPlantaLeg.setForeground(Color.BLACK);
            }
        }

        // Actualizar JTable
        tableModel.setRowCount(0);
        for (Entidad e : eco.getEntidadesVivas()) {
            String tipo = "Planta";
            if (e instanceof PlantaVenenosa) {
                tipo = "Planta Venenosa 💀";
            } else if (e instanceof Conejo) {
                tipo = "Conejo";
            } else if (e instanceof Lobo) {
                tipo = "Lobo";
            }
            Object[] row = {
                tipo,
                e.getNombre(),
                String.format("%.1f", e.getEnergia()),
                e.getEdad()
            };
            tableModel.addRow(row);
        }

        // Actualizar JList
        listModelEventos.clear();
        for (String ev : eco.getEventosTurnoActualList()) {
            listModelEventos.addElement(ev);
        }

        // Actualizar Grilla 2D
        panelGrilla.setEcosistema(eco);
        panelGrilla.revalidate();
        panelGrilla.repaint();
        splitCenter.revalidate();
        splitCenter.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMain = new javax.swing.JPanel();
        pnlNorte = new javax.swing.JPanel();
        pnlDashboard = new javax.swing.JPanel();
        cardTurno = new javax.swing.JPanel();
        lblTurnoTitle = new javax.swing.JLabel();
        lblTurnoVal = new javax.swing.JLabel();
        cardClima = new javax.swing.JPanel();
        lblClimaTitle = new javax.swing.JLabel();
        lblClimaVal = new javax.swing.JLabel();
        cardPlantas = new javax.swing.JPanel();
        lblPlantasTitle = new javax.swing.JLabel();
        lblPlantasVal = new javax.swing.JLabel();
        cardConejos = new javax.swing.JPanel();
        lblConejosTitle = new javax.swing.JLabel();
        lblConejosVal = new javax.swing.JLabel();
        cardLobos = new javax.swing.JPanel();
        lblLobosTitle = new javax.swing.JLabel();
        lblLobosVal = new javax.swing.JLabel();
        pnlLeyenda = new javax.swing.JPanel();
        markerLobo = new javax.swing.JPanel();
        lblLoboLeg = new javax.swing.JLabel();
        markerConejo = new javax.swing.JPanel();
        lblConejoLeg = new javax.swing.JLabel();
        markerPlanta = new javax.swing.JPanel();
        lblPlantaLeg = new javax.swing.JLabel();
        splitCenter = new javax.swing.JSplitPane();
        panelGrilla = new parcial1.vista.MapCanvas();
        scrollTable = new javax.swing.JScrollPane();
        tblEntidades = new javax.swing.JTable();
        scrollEvents = new javax.swing.JScrollPane();
        lstEventos = new javax.swing.JList<>();
        panelSur = new javax.swing.JPanel();
        btnAvanzarTurno = new javax.swing.JButton();
        btnCambiarClima = new javax.swing.JButton();
        btnAgregarEspecie = new javax.swing.JButton();
        btnTerminarSim = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        mnuSimulacion = new javax.swing.JMenu();
        mnuNueva = new javax.swing.JMenuItem();
        mnuGuardar = new javax.swing.JMenuItem();
        mnuCargar = new javax.swing.JMenuItem();
        mnuAutoSim = new javax.swing.JMenuItem();
        mnuReportes = new javax.swing.JMenu();
        mnuReporte = new javax.swing.JMenuItem();
        mnuEstadoActual = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Simulador de Ecosistema 🌿🐰🐺");

        pnlMain.setBackground(new java.awt.Color(243, 244, 246));
        pnlMain.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlMain.setLayout(new java.awt.BorderLayout(10, 10));

        pnlNorte.setLayout(new java.awt.BorderLayout());

        pnlDashboard.setBackground(new java.awt.Color(243, 244, 246));
        pnlDashboard.setLayout(new java.awt.GridLayout(1, 5, 10, 0));

        cardTurno.setBackground(new java.awt.Color(255, 255, 255));
        cardTurno.setLayout(new java.awt.BorderLayout());

        lblTurnoTitle.setFont(new java.awt.Font("SansSerif", 1, 10)); // NOI18N
        lblTurnoTitle.setForeground(new java.awt.Color(156, 163, 175));
        lblTurnoTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTurnoTitle.setText("TURNO");
        cardTurno.add(lblTurnoTitle, java.awt.BorderLayout.NORTH);

        lblTurnoVal.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblTurnoVal.setForeground(new java.awt.Color(17, 24, 39));
        lblTurnoVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTurnoVal.setText("1");
        cardTurno.add(lblTurnoVal, java.awt.BorderLayout.CENTER);

        pnlDashboard.add(cardTurno);

        cardClima.setBackground(new java.awt.Color(255, 255, 255));
        cardClima.setLayout(new java.awt.BorderLayout());

        lblClimaTitle.setFont(new java.awt.Font("SansSerif", 1, 10)); // NOI18N
        lblClimaTitle.setForeground(new java.awt.Color(156, 163, 175));
        lblClimaTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblClimaTitle.setText("CLIMA");
        cardClima.add(lblClimaTitle, java.awt.BorderLayout.NORTH);

        lblClimaVal.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblClimaVal.setForeground(new java.awt.Color(17, 24, 39));
        lblClimaVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblClimaVal.setText("SOLEADO");
        cardClima.add(lblClimaVal, java.awt.BorderLayout.CENTER);

        pnlDashboard.add(cardClima);

        cardPlantas.setBackground(new java.awt.Color(255, 255, 255));
        cardPlantas.setLayout(new java.awt.BorderLayout());

        lblPlantasTitle.setFont(new java.awt.Font("SansSerif", 1, 10)); // NOI18N
        lblPlantasTitle.setForeground(new java.awt.Color(156, 163, 175));
        lblPlantasTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPlantasTitle.setText("PLANTAS VIVAS");
        cardPlantas.add(lblPlantasTitle, java.awt.BorderLayout.NORTH);

        lblPlantasVal.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblPlantasVal.setForeground(new java.awt.Color(17, 24, 39));
        lblPlantasVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPlantasVal.setText("0");
        cardPlantas.add(lblPlantasVal, java.awt.BorderLayout.CENTER);

        pnlDashboard.add(cardPlantas);

        cardConejos.setBackground(new java.awt.Color(255, 255, 255));
        cardConejos.setLayout(new java.awt.BorderLayout());

        lblConejosTitle.setFont(new java.awt.Font("SansSerif", 1, 10)); // NOI18N
        lblConejosTitle.setForeground(new java.awt.Color(156, 163, 175));
        lblConejosTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConejosTitle.setText("CONEJOS VIVOS");
        cardConejos.add(lblConejosTitle, java.awt.BorderLayout.NORTH);

        lblConejosVal.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblConejosVal.setForeground(new java.awt.Color(17, 24, 39));
        lblConejosVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConejosVal.setText("0");
        cardConejos.add(lblConejosVal, java.awt.BorderLayout.CENTER);

        pnlDashboard.add(cardConejos);

        cardLobos.setBackground(new java.awt.Color(255, 255, 255));
        cardLobos.setLayout(new java.awt.BorderLayout());

        lblLobosTitle.setFont(new java.awt.Font("SansSerif", 1, 10)); // NOI18N
        lblLobosTitle.setForeground(new java.awt.Color(156, 163, 175));
        lblLobosTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLobosTitle.setText("LOBOS VIVOS");
        cardLobos.add(lblLobosTitle, java.awt.BorderLayout.NORTH);

        lblLobosVal.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblLobosVal.setForeground(new java.awt.Color(17, 24, 39));
        lblLobosVal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLobosVal.setText("0");
        cardLobos.add(lblLobosVal, java.awt.BorderLayout.CENTER);

        pnlDashboard.add(cardLobos);

        pnlNorte.add(pnlDashboard, java.awt.BorderLayout.NORTH);

        pnlLeyenda.setBackground(new java.awt.Color(243, 244, 243));
        pnlLeyenda.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 5));

        markerLobo.setBackground(new java.awt.Color(239, 68, 68));
        markerLobo.setPreferredSize(new java.awt.Dimension(15, 15));
        pnlLeyenda.add(markerLobo);

        lblLoboLeg.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblLoboLeg.setText("Lobo (Rojo)");
        pnlLeyenda.add(lblLoboLeg);

        markerConejo.setBackground(new java.awt.Color(249, 115, 22));
        markerConejo.setPreferredSize(new java.awt.Dimension(15, 15));
        pnlLeyenda.add(markerConejo);

        lblConejoLeg.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblConejoLeg.setText("Conejo (Naranja)");
        pnlLeyenda.add(lblConejoLeg);

        markerPlanta.setBackground(new java.awt.Color(34, 197, 94));
        markerPlanta.setPreferredSize(new java.awt.Dimension(15, 15));
        pnlLeyenda.add(markerPlanta);

        lblPlantaLeg.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblPlantaLeg.setText("Planta (Verde)");
        pnlLeyenda.add(lblPlantaLeg);

        pnlNorte.add(pnlLeyenda, java.awt.BorderLayout.SOUTH);

        pnlMain.add(pnlNorte, java.awt.BorderLayout.NORTH);

        splitCenter.setDividerLocation(500);
        splitCenter.setResizeWeight(0.5);

        panelGrilla.setLayout(null);
        splitCenter.setLeftComponent(panelGrilla);

        scrollTable.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Especies"));

        tblEntidades.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        scrollTable.setViewportView(tblEntidades);

        splitCenter.setRightComponent(scrollTable);

        pnlMain.add(splitCenter, java.awt.BorderLayout.CENTER);

        scrollEvents.setPreferredSize(new java.awt.Dimension(280, 0));
        scrollEvents.setBorder(javax.swing.BorderFactory.createTitledBorder("Historial de Eventos del Turno"));

        lstEventos.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
        scrollEvents.setViewportView(lstEventos);

        pnlMain.add(scrollEvents, java.awt.BorderLayout.EAST);

        panelSur.setBackground(new java.awt.Color(243, 244, 246));
        panelSur.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 10));

        btnAvanzarTurno.setBackground(new java.awt.Color(16, 185, 129));
        btnAvanzarTurno.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btnAvanzarTurno.setForeground(new java.awt.Color(0, 0, 0));
        btnAvanzarTurno.setText("Continuar Turno ▶");
        btnAvanzarTurno.setFocusPainted(false);
        btnAvanzarTurno.setPreferredSize(new java.awt.Dimension(180, 35));
        panelSur.add(btnAvanzarTurno);

        btnCambiarClima.setBackground(new java.awt.Color(245, 158, 11));
        btnCambiarClima.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btnCambiarClima.setForeground(new java.awt.Color(0, 0, 0));
        btnCambiarClima.setText("Cambiar Clima ☁️");
        btnCambiarClima.setFocusPainted(false);
        btnCambiarClima.setPreferredSize(new java.awt.Dimension(180, 35));
        panelSur.add(btnCambiarClima);

        btnAgregarEspecie.setBackground(new java.awt.Color(59, 130, 246));
        btnAgregarEspecie.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btnAgregarEspecie.setForeground(new java.awt.Color(0, 0, 0));
        btnAgregarEspecie.setText("Agregar Especie ➕");
        btnAgregarEspecie.setFocusPainted(false);
        btnAgregarEspecie.setPreferredSize(new java.awt.Dimension(180, 35));
        panelSur.add(btnAgregarEspecie);

        btnTerminarSim.setBackground(new java.awt.Color(139, 92, 246));
        btnTerminarSim.setFont(new java.awt.Font("SansSerif", 1, 13)); // NOI18N
        btnTerminarSim.setForeground(new java.awt.Color(0, 0, 0));
        btnTerminarSim.setText("Terminar Simulación ⏹️");
        btnTerminarSim.setFocusPainted(false);
        btnTerminarSim.setPreferredSize(new java.awt.Dimension(180, 35));
        panelSur.add(btnTerminarSim);

        pnlMain.add(panelSur, java.awt.BorderLayout.SOUTH);

        mnuSimulacion.setText("Simulación");

        mnuNueva.setText("Nueva Simulación");
        mnuSimulacion.add(mnuNueva);

        mnuGuardar.setText("Guardar Estado");
        mnuSimulacion.add(mnuGuardar);

        mnuCargar.setText("Cargar Estado");
        mnuSimulacion.add(mnuCargar);

        mnuAutoSim.setText("Simulación Auto / Pausar");
        mnuSimulacion.add(mnuAutoSim);

        menuBar.add(mnuSimulacion);

        mnuReportes.setText("Reportes");

        mnuReporte.setText("Reporte Final");
        mnuReportes.add(mnuReporte);

        mnuEstadoActual.setText("Estado Actual");
        mnuReportes.add(mnuEstadoActual);

        menuBar.add(mnuReportes);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMain, javax.swing.GroupLayout.DEFAULT_SIZE, 650, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAgregarEspecie;
    public javax.swing.JButton btnAvanzarTurno;
    public javax.swing.JButton btnCambiarClima;
    public javax.swing.JButton btnTerminarSim;
    public javax.swing.JPanel cardClima;
    public javax.swing.JPanel cardConejos;
    public javax.swing.JPanel cardLobos;
    public javax.swing.JPanel cardPlantas;
    public javax.swing.JPanel cardTurno;
    public javax.swing.JLabel lblClimaTitle;
    public javax.swing.JLabel lblClimaVal;
    public javax.swing.JLabel lblConejoLeg;
    public javax.swing.JLabel lblConejosTitle;
    public javax.swing.JLabel lblConejosVal;
    public javax.swing.JLabel lblLoboLeg;
    public javax.swing.JLabel lblLobosTitle;
    public javax.swing.JLabel lblLobosVal;
    public javax.swing.JLabel lblPlantaLeg;
    public javax.swing.JLabel lblPlantasTitle;
    public javax.swing.JLabel lblPlantasVal;
    public javax.swing.JLabel lblTurnoTitle;
    public javax.swing.JLabel lblTurnoVal;
    public javax.swing.JList<String> lstEventos;
    public javax.swing.JPanel markerConejo;
    public javax.swing.JPanel markerLobo;
    public javax.swing.JPanel markerPlanta;
    public javax.swing.JMenuBar menuBar;
    public javax.swing.JMenuItem mnuAutoSim;
    public javax.swing.JMenuItem mnuCargar;
    public javax.swing.JMenuItem mnuEstadoActual;
    public javax.swing.JMenuItem mnuGuardar;
    public javax.swing.JMenuItem mnuNueva;
    public javax.swing.JMenuItem mnuReporte;
    public javax.swing.JMenu mnuReportes;
    public javax.swing.JMenu mnuSimulacion;
    public parcial1.vista.MapCanvas panelGrilla;
    public javax.swing.JPanel panelSur;
    public javax.swing.JPanel pnlDashboard;
    public javax.swing.JPanel pnlLeyenda;
    public javax.swing.JPanel pnlMain;
    public javax.swing.JPanel pnlNorte;
    public javax.swing.JScrollPane scrollEvents;
    public javax.swing.JScrollPane scrollTable;
    public javax.swing.JSplitPane splitCenter;
    public javax.swing.JTable tblEntidades;
    // End of variables declaration//GEN-END:variables
}
