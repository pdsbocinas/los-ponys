package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Alojamiento;
import com.google.maps.GeoApiContext;
import com.google.maps.TextSearchRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Repository("AlojamientoDao")
@Transactional
public class AlojamientoDaoImpl implements AlojamientoDao {

  @Inject
  private SessionFactory sessionFactory;

  @Value("${datasource.apiKey}")
  private String apiKey;

  public Criteria objetoCriteria() {
    Session session = sessionFactory.getCurrentSession();
    Criteria criteria = session.createCriteria(Alojamiento.class);
    return criteria;
  }

  @Override
  public List<Alojamiento> obtenerTodosLosAlojamientos() {
    return this.objetoCriteria().list();
  }

  @Override
  public List<Alojamiento> obtenerPorFechas(Date desde, Date hasta) {
    Criteria criteria = this.objetoCriteria();
      criteria
        .add(Restrictions.ge("desde", desde))
        .add(Restrictions.le("hasta", hasta));
    List<Alojamiento> list = criteria.list();
    return list;
  }

  @Override
  public List<Alojamiento> obtenerAlojamientosParametrizados(Date desde, Date hasta, Integer precioDesde, Integer precioHasta, Integer rating, Boolean ofertas, Integer descuento, Integer offset, Integer size) {
    Criteria criteria = this.objetoCriteria();

    if (desde != null && hasta != null) {
      criteria
        .add(Restrictions.ge("desde", desde))
        .add(Restrictions.le("hasta", hasta));
    }

    if (precioDesde != null && precioHasta != null) {
      criteria
        .add(Restrictions.ge("precio", precioDesde))
        .add(Restrictions.le("precio", precioHasta));
    }

    if (rating != null) {
      criteria
        .add(Restrictions.eq("rating", rating));

    }

    if (ofertas != null) {
      criteria
        .add(Restrictions.eq("bookeable", ofertas));
    }

    if (descuento != null) {
      criteria
        .add(Restrictions.ge("descuento", descuento));

    }

    criteria.setFirstResult(offset).setFetchSize(size);
    List results = criteria.list();

    return results;
  }

  @Override
  public Alojamiento obtenerAlojamientoPorId(Integer id) {
    Criteria criteria = this.objetoCriteria();
    Alojamiento alojamiento = (Alojamiento) criteria.add(Restrictions.eq("id", id))
        .uniqueResult();

    return alojamiento;
  }

  @Override
  public void guardarAlojamientos(String ciudad) throws InterruptedException, ApiException, IOException {

    Session session = sessionFactory.getCurrentSession();
    List<Alojamiento> alojamientos = this.obtenerTodosLosAlojamientos();

    for(Alojamiento alojamiento : alojamientos) {
      session.delete(alojamiento);
    }

    GeoApiContext context = new GeoApiContext.Builder()
        .apiKey(apiKey)
        .build();
    List<Alojamiento> listaDeAlojamientos = new ArrayList<>();

    TextSearchRequest request = new TextSearchRequest(context);
    PlacesSearchResponse response = request.query(ciudad).await();
    for (PlacesSearchResult alojDto : response.results) {
      String tokenPage = response.nextPageToken;
      Alojamiento alojamiento = new Alojamiento();
      alojamiento.setNombre(alojDto.name);
      alojamiento.setDescripcion(alojDto.formattedAddress);
      alojamiento.setPrecio(priceRandom());
      alojamiento.setBookeable(getRandomBoolean());
      alojamiento.setRating((int)alojDto.rating);
      listaDeAlojamientos.add(alojamiento);
    }

    this.guardarAlojamientosEnLabase(listaDeAlojamientos);
  }

  @Override
  public void guardarAlojamientosEnLabase(List<Alojamiento> alojamientos) {
    final Session session = sessionFactory.getCurrentSession();

    for(Alojamiento alojamiento : alojamientos) {
      session.saveOrUpdate(alojamiento);
    }
  }

  static Integer priceRandom() {
    double randomDouble = Math.random();
    randomDouble = randomDouble * 9000 + 1;
    int randomInt = (int) randomDouble;
    return randomInt;
  }

  public boolean getRandomBoolean() {
    Random random = new Random();
    return random.nextBoolean();
  }
}
