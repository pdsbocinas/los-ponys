package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.dao.ComentarioDao;
import ar.edu.unlam.tallerweb1.dao.DestinoDao;
import ar.edu.unlam.tallerweb1.modelo.Comentario;
import ar.edu.unlam.tallerweb1.modelo.Destino;
import com.google.maps.errors.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Service("servicioComentario")
@Transactional
public class ServicioComentarioImpl implements ServicioComentario {

  @Inject
  private ComentarioDao comentarioDao;

  @Override
  public void guardarComentario(Comentario comentario) {
    comentarioDao.guardarComentario(comentario);
  }

  @Override
  public List<Comentario> obtenerComentariosPorViajeId(Long viaje_id) {
    return comentarioDao.obtenerComentariosPorViajeId(viaje_id);
  }

  @Override
  public List<Comentario> obtenerComentariosNoLeidos(Integer id) {
    return comentarioDao.obtenerComentariosNoLeidos(id);
  }
}
