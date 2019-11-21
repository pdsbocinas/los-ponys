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
  public Foto obtenerFoto(Foto foto) {
    final Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Foto.class)
      .add(Restrictions.eq("id", foto.getId()));

    Foto result = (Foto) criteria.uniqueResult();

    return result;
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

  @Override
  public List<Foto> obtenerFotosDeDestinosDelViaje(Long viajeId) {
    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Foto.class)
      .createAlias("viaje", "v")
      .add(Restrictions.eq("v.id", viajeId));

    List<Foto> list = criteria.list();

    return list;
  }

  @Override
  public Boolean elegirFotoComoPortada(Foto foto) {
    Session session = sessionFactory.getCurrentSession();
    Foto fotoPortadaAnterior = obtenerFotoDePortada(foto.getViaje().getId());
    fotoPortadaAnterior.setPortada(false);
    foto.setPortada(true);
    session.update(foto);
    return foto.getPortada();
  }

  @Override
  public Foto obtenerFotoDePortada(Long viajeId) {
    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Foto.class)
      .createAlias("viaje", "v")
      .add(Restrictions.eq("v.id", viajeId))
      .add(Restrictions.eq("portada", true));

    Foto foto = (Foto) criteria.uniqueResult();
    if(foto != null){
      return foto;
    }else{
      return new Foto();
    }

  }
}
