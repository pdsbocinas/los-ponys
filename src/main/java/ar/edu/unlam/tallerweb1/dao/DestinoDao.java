package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Destino;
import com.google.maps.errors.ApiException;

import java.io.IOException;
import java.util.List;

public interface DestinoDao {
  void guardarDestinos(List<Destino> destinos);

  List<Destino> obtenerTodosPorId(List<String> destinosId) throws InterruptedException, ApiException, IOException;

  List<Destino> obtenerDestinosDeViajes(Long id);
}
