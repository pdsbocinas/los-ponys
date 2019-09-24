package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Pais;

import java.util.List;

public interface ServicioPais {
  void guardarPaises();
  List<Pais> obtenerPaises ();
}
