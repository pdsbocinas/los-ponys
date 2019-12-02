package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.dao.ComentarioDao;
import ar.edu.unlam.tallerweb1.modelo.Comentario;
import ar.edu.unlam.tallerweb1.modelo.ComentarioDto;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.Viaje;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service("servicioComentario")
@Transactional
public class ServicioComentarioImpl implements ServicioComentario {

  @Inject
  private ComentarioDao comentarioDao;

  @Inject
  private ServicioViaje servicioViaje;

  @Inject
  private ServicioRegistroUsuario servicioRegistroUsuario;

  @Override
  public void guardarComentario(Comentario comentario) {

    comentarioDao.guardarComentario(comentario);
  }

  @Override
  public Comentario convertirComentarioDtoAComentario(ComentarioDto comentarioDto) {
    Comentario comentario = new Comentario();

    Long viaje_id = comentarioDto.getViaje_id();
    String email_usuario = comentarioDto.getUsuario_email();
    String comment = comentarioDto.getTexto();
    String estado = "no leido";

    Viaje viaje = servicioViaje.obtenerViajePorId(viaje_id);
    Usuario usuario = servicioRegistroUsuario.obtenerUsuarioPorMail(email_usuario);

    comentario.setTexto(comment);
    comentario.setUsuario(usuario);
    comentario.setViaje(viaje);
    comentario.setEstado(estado);

    return comentario;
  }

  @Override
  public List<Comentario> obtenerComentariosPorViajeId(Long viaje_id) {
    return comentarioDao.obtenerComentariosPorViajeId(viaje_id);
  }

  @Override
  public List<Comentario> obtenerComentariosNoLeidos(Integer id) {
    return comentarioDao.obtenerComentariosNoLeidos(id);
  }

  @Override
  public void borrarComentarios(List<Comentario> comentarios) {
    comentarioDao.borrarComentarios(comentarios);
  }
}
