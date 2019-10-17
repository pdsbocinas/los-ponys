package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Alojamiento;

import java.util.Date;
import java.util.List;

public interface ServicioAlojamiento {
  List<Alojamiento> obtenerTodosLosAlojamientos();
  List<Alojamiento> obtenerPorFechas(Date desde, Date hasta);
  List<Alojamiento> obtenerPorPrecio(Integer desde, Integer hasta);
  List<Alojamiento> obtenerPorRating(Integer rating);
  List<Alojamiento> obtenerAquellosConReserva(Boolean bookeable);
  List<Alojamiento> obtenerAquellosConDescuento(Integer descuento);
}
