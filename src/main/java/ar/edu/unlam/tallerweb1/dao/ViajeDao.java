package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Viaje;

import java.util.List;

public interface ViajeDao {
  void guardarViaje(Viaje viaje);
  List<Viaje> obtenerViajes();
  Viaje obtenerViajePorId(Long id);

  List<Viaje> obtenerViajesPorUsuario(Integer id);
}
