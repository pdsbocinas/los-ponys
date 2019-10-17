package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.dao.AlojamientoDao;
import ar.edu.unlam.tallerweb1.modelo.Alojamiento;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Service("servicioAlojamiento")
@Transactional
public class ServicioAlojamientoImpl implements ServicioAlojamiento {

  @Inject
  AlojamientoDao alojamientoDao;

  @Override
  public List<Alojamiento> obtenerTodosLosAlojamientos() {
    return alojamientoDao.obtenerTodosLosAlojamientos();
  }

  @Override
  public List<Alojamiento> obtenerPorFechas(Date desde, Date hasta) {
    return alojamientoDao.obtenerPorFechas(desde, hasta);
  }

  @Override
  public List<Alojamiento> obtenerPorPrecio(Integer desde, Integer hasta) {
    return alojamientoDao.obtenerPorPrecio(desde, hasta);
  }

  @Override
  public List<Alojamiento> obtenerPorRating(Integer rating) {
    return alojamientoDao.obtenerPorRating(rating);
  }

  @Override
  public List<Alojamiento> obtenerAquellosConReserva(Boolean bookeable) {
    return alojamientoDao.obtenerAquellosConReserva(bookeable);
  }

  @Override
  public List<Alojamiento> obtenerAquellosConDescuento(Integer descuento) {
    return alojamientoDao.obtenerAquellosConDescuento(descuento);
  }
}
