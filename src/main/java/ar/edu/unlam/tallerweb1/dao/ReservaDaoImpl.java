package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Reserva;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Repository("reservaDao")
@Transactional
public class ReservaDaoImpl implements ReservaDao {

  @Inject
  private SessionFactory sessionFactory;

  @Override
  public void crearReservaParaAlojamiento(Reserva reserva) {
    Session session = sessionFactory.getCurrentSession();
    session.saveOrUpdate(reserva);
  }
}
