package parcial1.modelo;

import parcial1.modelo.Ecosistema;


public interface Reproducible {
    
    void reproducirse (Ecosistema eco);
            
    boolean puedeReproducirse();
        
    default void intentarReproduccion(Ecosistema eco) {
        if (puedeReproducirse()) {
            reproducirse(eco);
        }
    }
      
}

