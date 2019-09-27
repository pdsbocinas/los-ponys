package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Border;
import ar.edu.unlam.tallerweb1.modelo.Pais;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Repository("paisesDao")
@Transactional
public class PaisesDaoImpl implements PaisesDao {

  @Inject
  private SessionFactory sessionFactory;

  @Override
  public void guardarPaises(Pais pais) {
    // guardar los paises en la base de datos con hibernate Criteria
    Session session = sessionFactory.getCurrentSession();
    // Transaction tx = session.beginTransaction();
    session.save(pais);
    // tx.commit();
    // session.close();
  }

  @Override
  public List<Pais> obtenerPaises() {
    // hacer la consulta a la base de datos para obtener los paises con Criteria
    final Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Pais.class);
    List<Pais> paises = criteria.list();
    return  paises;
  }

  @Override
  public String obtenerCapital(Pais pais) {
    // hacer la consulta a la base de datos para obtener los paises con Criteria
    final Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Pais.class);

    criteria.add(Restrictions.eq("capital", pais.getCapital()));
    Pais paisDevuelto = (Pais) criteria.uniqueResult();
    return paisDevuelto.getCapital();
  }

}

