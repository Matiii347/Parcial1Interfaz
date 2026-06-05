package parcial1.dao;

import parcial1.modelo.Ecosistema;

public interface EcosistemaDAO {
    void guardarEstado(Ecosistema ecosistema);
    Ecosistema cargarEstado();
}
