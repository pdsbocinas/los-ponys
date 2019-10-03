package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Destino;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Repository("DestinoDao")
@Transactional
public class DestinoDaoImpl implements DestinoDao {
  @Inject
  private SessionFactory sessionFactory;

  @Override
  public void guardarDestinos(List<Destino> destinos) {
    final Session session = sessionFactory.getCurrentSession();
    session.save(destinos);
  }

}
