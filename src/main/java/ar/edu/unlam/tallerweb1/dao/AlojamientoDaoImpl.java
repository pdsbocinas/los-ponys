package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Alojamiento;
import ar.edu.unlam.tallerweb1.modelo.Viaje;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Repository("AlojamientoDao")
@Transactional
public class AlojamientoDaoImpl implements AlojamientoDao {

  @Inject
  private SessionFactory sessionFactory;

  public Criteria objetoCriteria() {
    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Alojamiento.class);
    return criteria;
  }

  @Override
  public List<Alojamiento> obtenerTodosLosAlojamientos() {
    return this.objetoCriteria().list();
  }

  @Override
  public List<Alojamiento> obtenerPorFechas(Date desde, Date hasta) {
    Criteria criteria = this.objetoCriteria();
      criteria
        .add(Restrictions.ge("desde", desde))
        .add(Restrictions.le("hasta", hasta));
    List<Alojamiento> list = criteria.list();
    return list;
  }

  @Override
  public List<Alojamiento> obtenerAlojamientosParametrizados(Date desde, Date hasta, Integer precioDesde, Integer precioHasta, Integer rating, Boolean ofertas, Integer descuento, Integer offset, Integer size) {
    Criteria criteria = this.objetoCriteria();

    if (desde != null && hasta != null) {
      criteria
        .add(Restrictions.ge("desde", desde))
        .add(Restrictions.le("hasta", hasta));
    }

    if (precioDesde != null && precioHasta != null) {
      criteria
        .add(Restrictions.ge("precio", precioDesde))
        .add(Restrictions.le("precio", precioHasta));
    }

    if (rating != null) {
      criteria
        .add(Restrictions.eq("rating", rating));

    }

    if (ofertas != null) {
      criteria
        .add(Restrictions.eq("bookeable", ofertas));
    }

    if (descuento != null) {
      criteria
        .add(Restrictions.ge("descuento", descuento));

    }

    criteria.setFirstResult(offset).setFetchSize(size);
    List results = criteria.list();

    return results;
  }

  @Override
  public Alojamiento obtenerAlojamientoPorId(Integer id) {
    Criteria criteria = this.objetoCriteria();
    Alojamiento alojamiento = (Alojamiento) criteria.add(Restrictions.eq("id", id))
        .uniqueResult();

    return alojamiento;
  }


}