package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Viaje;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Repository("viajeDao")
@Transactional
public class ViajeDaoImpl implements ViajeDao{

    @Inject
    private SessionFactory sessionFactory;

    @Override
    public void guardarViaje(Viaje viaje) {
        // guardar el viaje en la base de datos con hibernate Criteria
        Session session = sessionFactory.getCurrentSession();
        // Transaction tx = session.beginTransaction();
        session.save(viaje);
        // tx.commit();
        // session.close();
    }

    @Override
    public List<Viaje> obtenerViaje() {
        return null;
    }
}
