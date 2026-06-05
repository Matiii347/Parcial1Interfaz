package parcial1.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import parcial1.modelo.Ecosistema;
import parcial1.dao.EcosistemaDAO;
import parcial1.dao.EcosistemaDAOImpl;
// import parcial1.vista.PantallaSimulador; // Descomentar cuando la vista exista

public class SimuladorController implements ActionListener {

    private Ecosistema modelo;
    // private PantallaSimulador vista; // Descomentar luego
    private EcosistemaDAO dao;

    // Constructor: Recibe el modelo y la vista para conectarlos
    public SimuladorController(Ecosistema modelo /*, PantallaSimulador vista*/) {
        this.modelo = modelo;
        // this.vista = vista;
        
        // Instanciamos el DAO para poder guardar y cargar
        this.dao = new EcosistemaDAOImpl();
        
        // ACÁ SE ENCHUFAN LOS BOTONES (Ejemplos para cuando la vista esté lista)
        // this.vista.btnAvanzarTurno.addActionListener(this);
        // this.vista.btnGuardar.addActionListener(this);
        // this.vista.btnReporteFinal.addActionListener(this);
    }

    // Este es el método obligatorio de ActionListener que atrapa los clics
    @Override
    public void actionPerformed(ActionEvent e) {
        
        /* EJEMPLO DE CÓMO VA A FUNCIONAR:
        
        // Si el usuario hace clic en "Avanzar Turno"
        if (e.getSource() == vista.btnAvanzarTurno) {
            modelo.procesarTurno(); // Tu lógica avanza el juego
            actualizarInterfaz();   // Actualizamos las tablas y textos
        }
        
        // Si el usuario hace clic en "Guardar y Pausar"
        if (e.getSource() == vista.btnGuardar) {
            dao.guardarEstado(modelo); // Guardamos en el .txt
            System.out.println("Partida guardada con éxito.");
            // vista.mostrarMensaje("Partida guardada!");
        }
        */
    }

    // Método para arrancar de cero si no hay partida guardada
    public void iniciarNuevaSimulacion(int lobos, int conejos, int plantas) {
        // Acá podemos traer ese código viejo del Parcial1.java 
        // que creaba los animales con un for() y los metía al Ecosistema.
        System.out.println("Iniciando ecosistema con: " + lobos + " lobos, " + conejos + " conejos.");
    }
    
    // Método para refrescar las JTable y JList de Swing
    private void actualizarInterfaz() {
        // Acá vas a pedirle al modelo la lista de vivos y la vas a mandar a la vista
    }
}