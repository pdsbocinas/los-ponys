package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Alojamiento;

import java.util.Date;
import java.util.List;

public interface AlojamientoDao {
  List<Alojamiento> obtenerTodosLosAlojamientos();
  List<Alojamiento> obtenerPorFechas(Date desde, Date hasta);
  List<Alojamiento> obtenerAlojamientosParametrizados(Date desde, Date hasta, Integer precioDesde, Integer precioHasta, Integer rating, Boolean ofertas, Integer descuento, Integer offset, Integer size);
}
