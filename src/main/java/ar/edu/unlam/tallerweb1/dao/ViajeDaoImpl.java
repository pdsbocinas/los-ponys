package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Viaje;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
        session.saveOrUpdate(viaje);
        // tx.commit();
        // session.close();
    }

    @Override
    public List<Viaje> obtenerViajes() {
        Session session = sessionFactory.getCurrentSession();
        Criteria cr = session.createCriteria(Viaje.class);
        List results = cr.list();
        return results;
    }

  @Override
  public Viaje obtenerViajePorId(Long id) {
    Session session = sessionFactory.getCurrentSession();
    Viaje v = (Viaje) session.createCriteria(Viaje.class)
        .add(Restrictions.eq("id", id))
        .uniqueResult();
    return v;

  }
}
