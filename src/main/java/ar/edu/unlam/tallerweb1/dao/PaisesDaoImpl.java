package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Pais;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("paisesDao")
public class PaisesDaoImpl implements PaisesDao {

  @Override
  public void guardarPaises(List<Pais> paises) {
    // guardar los paises en la base de datos con hibernate Criteria
  }

  @Override
  public List<Pais> obtenerPaises() {
    // hacer la consulta a la base de datos para obtener los paises con Criteria
    return null;
  }
}
