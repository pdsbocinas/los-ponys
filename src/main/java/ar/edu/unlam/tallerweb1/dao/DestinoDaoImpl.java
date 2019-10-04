package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Destino;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.PlaceDetailsRequest;
import com.google.maps.TextSearchRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlaceDetails;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
      session.save(destino);
    }
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
      dest.setRegion(place.adrAddress);
      listaDestinos.add(dest);
    }

    this.guardarDestinos(listaDestinos);
    // devuelve un json de resultado aleatorios
/*    PlacesSearchResponse contextPlaceApi = new TextSearchRequest(context).query(jsonString).await();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(contextPlaceApi.results);*/
    return listaDestinos;
  }
}
