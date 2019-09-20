package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Pais;

import java.util.ArrayList;
import java.util.List;

public interface PaisDao {
  ArrayList<Pais> obtenerPaises();
  void guardarPaises(ArrayList<Pais> paises);
}
