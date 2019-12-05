package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Alojamiento;
import ar.edu.unlam.tallerweb1.modelo.Reserva;
import com.google.maps.errors.ApiException;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface ServicioAlojamiento {
  List<Alojamiento> obtenerTodosLosAlojamientos();
  List<Alojamiento> obtenerPorFechas(Date desde, Date hasta);
  List<Alojamiento> obtenerAlojamientosParametrizados(Date desde, Date hasta, Integer precioDesde, Integer precioHasta, Integer rating, Boolean ofertas, Integer descuento, Integer offset, Integer size);
  Alojamiento obtenerAlojamientoPorId(Integer id);

  void guardarAlojamientos(String ciudad) throws InterruptedException, ApiException, IOException;

  void borrarReservas(List<Reserva> reservas);

  List<Reserva> obtenerReservasPorUsuario(Integer userId);
}
