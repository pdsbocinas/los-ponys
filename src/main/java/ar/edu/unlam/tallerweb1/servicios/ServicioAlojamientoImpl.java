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
  public List<Alojamiento> obtenerAlojamientosParametrizados(Date desde, Date hasta, Integer precioDesde, Integer precioHasta, Integer rating, Boolean ofertas, Integer descuento, Integer offset, Integer size) {
    return alojamientoDao.obtenerAlojamientosParametrizados(desde, hasta, precioDesde, precioHasta, rating, ofertas, descuento, offset, size);
  }
}
