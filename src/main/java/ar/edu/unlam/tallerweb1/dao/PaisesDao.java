package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Pais;

import java.util.List;

public interface PaisesDao {
  void guardarPaises(List<Pais> paises);
  List<Pais> obtenerPaises();
}
