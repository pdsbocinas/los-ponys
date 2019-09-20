package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Pais;

import java.util.ArrayList;
import java.util.List;

public interface ServicioPais {
  ArrayList<Pais> obtenerPaises();
  void guardarPaises(ArrayList<Pais> paises);
}
