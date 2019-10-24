package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.dao.ReservaDao;
import ar.edu.unlam.tallerweb1.modelo.Reserva;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

@Service("servicioReserva")
@Transactional
public class ServicioReservaImpl implements ServicioReserva {

  @Inject
  private ReservaDao reservaDao;

  @Override
  public void crearReservaParaAlojamiento(Reserva reserva) {
    reservaDao.crearReservaParaAlojamiento(reserva);
  }
}
