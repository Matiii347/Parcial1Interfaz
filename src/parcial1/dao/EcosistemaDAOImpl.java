package parcial1.dao;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import parcial1.modelo.Ecosistema;

public class EcosistemaDAOImpl implements EcosistemaDAO{
    // El archivo se guardará en la carpeta raíz del proyecto, como pide la rúbrica
    private final String RUTA_ARCHIVO = "ecosistema_guardado.txt";

    @Override
    public void guardarEstado(Ecosistema ecosistema) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(RUTA_ARCHIVO))) {
            oos.writeObject(ecosistema);
            System.out.println("Ecosistema guardado exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    @Override
    public Ecosistema cargarEstado() {
        Ecosistema ecoCargado = null;
        File archivo = new File(RUTA_ARCHIVO);
        
        // Verificamos si el archivo existe antes de intentar leerlo
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(RUTA_ARCHIVO))) {
                ecoCargado = (Ecosistema) ois.readObject();
                System.out.println("Ecosistema cargado desde el archivo.");
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al cargar el archivo: " + e.getMessage());
            }
        }
        return ecoCargado; // Retorna null si es la primera vez que se abre el juego
    }
}
