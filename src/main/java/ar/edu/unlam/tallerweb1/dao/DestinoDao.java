package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Destino;
import com.google.maps.errors.ApiException;

import java.io.IOException;
import java.util.List;

public interface DestinoDao {

  void guardarDestinos(List<Destino> destinos);
  void guardarDestino(Destino destino);

  Destino obtenerDestinoPorId(Integer id);

  List<Destino> obtenerTodosPorId(List<String> destinosId) throws InterruptedException, ApiException, IOException;

  List<Destino> obtenerDestinosDeViajes(Long id);

    void guardarFecha(Destino destino, Long destino_id);
}
