package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.dao.AlojamientoDao;
import ar.edu.unlam.tallerweb1.modelo.Alojamiento;
import ar.edu.unlam.tallerweb1.modelo.Reserva;
import com.google.maps.errors.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;
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

  @Override
  public Alojamiento obtenerAlojamientoPorId(Integer id) {
    return alojamientoDao.obtenerAlojamientoPorId(id);
  }

  @Override
  public void guardarAlojamientos(String ciudad) throws InterruptedException, ApiException, IOException {
    alojamientoDao.guardarAlojamientos(ciudad);
  }

  @Override
  public List<Reserva> obtenerReservasPorUsuario(Integer userId) {
    return alojamientoDao.obtenerReservasPorUsuario(userId);
  }

  @Override
  public void borrarReservas(List<Reserva> reservas) {
    alojamientoDao.borrarReservas(reservas);
  }
}
