package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Comentario;
import ar.edu.unlam.tallerweb1.modelo.Destino;
import com.google.maps.errors.ApiException;

import java.io.IOException;
import java.util.List;

public interface ComentarioDao {
  void guardarComentario(Comentario comentario);

  List<Comentario> obtenerComentariosPorViajeId(Long viaje_id) throws InterruptedException, ApiException, IOException;


}
