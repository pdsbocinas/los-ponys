package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Comentario;
import ar.edu.unlam.tallerweb1.modelo.ComentarioDto;

import java.util.List;

public interface ServicioComentario {
  void guardarComentario(Comentario comentario);
  Comentario convertirComentarioDtoAComentario(ComentarioDto comentarioDto);
  List<Comentario> obtenerComentariosPorViajeId (Long viaje_id);

  List<Comentario> obtenerComentariosNoLeidos(Integer id);

  void borrarComentarios(List<Comentario> servicioComentario);
}
