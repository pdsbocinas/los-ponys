package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Alojamiento;
import ar.edu.unlam.tallerweb1.modelo.Comentario;

import com.google.maps.errors.ApiException;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Repository("ComentarioDao")
@Transactional
public class ComentarioDaoImpl implements ComentarioDao {
  @Inject
  private SessionFactory sessionFactory;

  @Value("${datasource.apiKey}")
  private String apiKey;

  @Override
  public void guardarComentario(Comentario comentario) {
    final Session session = sessionFactory.getCurrentSession();
      session.saveOrUpdate(comentario);
  }

  @Override
  public List<Comentario> obtenerComentariosPorViajeId(Long viaje_id) {
      Session session = sessionFactory.getCurrentSession();
      Criteria criteria = session.createCriteria(Comentario.class)
          .createAlias("viaje", "v")
          .add(Restrictions.eq("v.id", viaje_id));

      List<Comentario> list = criteria.list();

    return list;
  }

  @Override
  public List<Comentario> obtenerComentariosNoLeidos(Integer id) {
    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Comentario.class, "c")
        .createAlias("c.viaje", "v")
        .createAlias("v.usuarios", "vu")
        .add(Restrictions.eq("vu.id", id))
        .add(Restrictions.eq("c.estado", "no leido"));

    List<Comentario> list = criteria.list();

    return list;
  }
}
