package ar.edu.unlam.tallerweb1.dao;


import ar.edu.unlam.tallerweb1.modelo.Destino;
import ar.edu.unlam.tallerweb1.modelo.Foto;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Repository("fotoDao")
@Transactional
public class FotoDaoImpl implements FotoDao {
  @Inject
  private SessionFactory sessionFactory;

  @Value("${datasource.apiKey}")
  private String apiKey;

  @Override
  public void guardarFoto(Foto foto) {
    final Session session = sessionFactory.getCurrentSession();
    session.saveOrUpdate(foto);
  }

  @Override
  public List<Foto> obtenerFotosPorDestinoId(Integer destino_id) {
    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Foto.class)
      .createAlias("destino", "d")
      .add(Restrictions.eq("d.id", destino_id));

    List<Foto> list = criteria.list();

    return list;
  }
}
