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
    Session session = sessionFactory.getCurrentSession();
    Criteria cr = session.createCriteria(Alojamiento.class)
        .add(Restrictions.ge("desde", desde))
        .add(Restrictions.le("hasta", hasta));
    List<Alojamiento> list = cr.list();
    return list;
  }

  @Override
  public List<Alojamiento> obtenerPorPrecio(Integer desde, Integer hasta) {
    return this.objetoCriteria()
        .add(Restrictions.ge("precio", desde))
        .add(Restrictions.le("precio", hasta))
        .list();
  }

  @Override
  public List<Alojamiento> obtenerPorRating(Integer rating) {
    return this.objetoCriteria()
        .add(Restrictions.eq("rating", rating))
        .list();
  }

  @Override
  public List<Alojamiento> obtenerAquellosConReserva(Boolean bookeable) {
    return this.objetoCriteria()
        .add(Restrictions.eq("bookeable", bookeable))
        .list();
  }

  @Override
  public List<Alojamiento> obtenerAquellosConDescuento(Integer descuento) {
    return this.objetoCriteria()
        .add(Restrictions.eq("descuento", descuento))
        .list();
  }

  @Override
  public List<Alojamiento> obtenerAlojamientosParametrizados(Date desde, Date hasta, Integer precioDesde, Integer precioHasta, Integer rating, Boolean bookeable, Integer descuento, Integer offset, Integer size) {
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

    if (bookeable != null) {
      criteria
        .add(Restrictions.eq("bookeable", bookeable));
    }

    if (descuento != null) {
      criteria
        .add(Restrictions.ge("descuento", descuento));

    }

    //criteria.setFirstResult(offset).setFetchSize(size);
    List results = criteria.list();

    return results;
  }


}
