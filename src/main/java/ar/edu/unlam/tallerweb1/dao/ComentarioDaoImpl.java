package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Comentario;

import ar.edu.unlam.tallerweb1.modelo.Viaje;
import com.google.maps.GeoApiContext;
import com.google.maps.PlaceDetailsRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlaceDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
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

      session.save(comentario);

  }

  @Override
  public List<Comentario> obtenerComentariosPorViajeId(Long viaje_id) throws InterruptedException, ApiException, IOException {

      Session session = sessionFactory.getCurrentSession();
      List<Comentario> c = session.createCriteria(Comentario.class)
              .add(Restrictions.eq("viaje_id", viaje_id))
              .list();

      return c;
  }


}
