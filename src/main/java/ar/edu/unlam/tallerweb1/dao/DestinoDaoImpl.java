package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Destino;
import ar.edu.unlam.tallerweb1.modelo.Viaje;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.PlaceDetailsRequest;
import com.google.maps.TextSearchRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import org.hibernate.Criteria;
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

@Repository("DestinoDao")
@Transactional
public class DestinoDaoImpl implements DestinoDao {
  @Inject
  private SessionFactory sessionFactory;

  @Value("${datasource.apiKey}")
  private String apiKey;

  @Override
  public void guardarDestinos(List<Destino> destinos) {
    final Session session = sessionFactory.getCurrentSession();

    for(Destino destino : destinos) {
      session.saveOrUpdate(destino);
    }
  }

  @Override
  public void guardarDestino(Destino destino) {
    final Session session = sessionFactory.getCurrentSession();
    session.saveOrUpdate(destino);
  }

  @Override
  public Destino obtenerDestinoPorId(Integer id) {
    final Session session = sessionFactory.getCurrentSession();
    Destino d = (Destino) session.createCriteria(Destino.class)
            .add(Restrictions.eq("id", id))
            .uniqueResult();
    return d;
  }

  @Override
  public List<Destino> obtenerTodosPorId(List<String> destinosId) throws InterruptedException, ApiException, IOException {
    GeoApiContext context = new GeoApiContext.Builder()
        .apiKey(apiKey)
        .build();
    List<Destino> listaDestinos = new ArrayList<>();

    for (String destino : destinosId) {
      Destino dest = new Destino();
      PlaceDetails place = new PlaceDetailsRequest(context).placeId(destino).await();
      dest.setNombre(place.name);
      dest.setCiudad(place.formattedAddress);
      //dest.setRegion(place.adrAddress);
      dest.setIcon(place.icon);
      dest.setPlaceId(place.placeId);
      dest.setLat(place.geometry.location.lat);
      dest.setLng(place.geometry.location.lng);
      listaDestinos.add(dest);
    }

    this.guardarDestinos(listaDestinos);
    // devuelve un json de resultado aleatorios
/*    PlacesSearchResponse contextPlaceApi = new TextSearchRequest(context).query(jsonString).await();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(contextPlaceApi.results);*/
    return listaDestinos;
  }

  @Override
  public List<Destino> obtenerDestinosDeViajes(Long id) {
    Session session = sessionFactory.getCurrentSession();
    Viaje v = (Viaje) session.createCriteria(Viaje.class)
        .add(Restrictions.eq("id", id))
        .uniqueResult();
    List<Destino> results = v.getDestinos();
    return results;
  }

  @Override
  public void guardarFecha(Destino destino, Long destino_id) {
    Session session = sessionFactory.getCurrentSession();

    session.saveOrUpdate(destino);
  }

  @Override
  public void guardarFoto(String nombreFoto, Integer destino_id) {
    Session session = sessionFactory.getCurrentSession();
    Destino destino = obtenerDestinoPorId(destino_id);
    destino.setFoto(nombreFoto);
    session.saveOrUpdate(destino);
  }
}
