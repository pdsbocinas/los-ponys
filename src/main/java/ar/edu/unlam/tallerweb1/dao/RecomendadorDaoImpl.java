package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.PuntoDeInteres;
import com.google.maps.GeoApiContext;
import com.google.maps.TextSearchRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.Photo;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository("recomendadorDao")
@Transactional
public class RecomendadorDaoImpl implements RecomendadorDao {

  @Value("${datasource.apiKey}")
  private String apiKey;

  @Override
  public List<PuntoDeInteres> obtenerPuntosDeInteres(String tipo, String destino) throws InterruptedException, ApiException, IOException {

    GeoApiContext context = new GeoApiContext.Builder()
        .apiKey(apiKey)
        .build();
    String busqueda = tipo + " " + destino;

    TextSearchRequest request = new TextSearchRequest(context);
    PlacesSearchResponse response = request.query(busqueda).await();

    List<PuntoDeInteres> listaDePuntosDeInteres = new ArrayList<>();

    for (PlacesSearchResult pto : response.results) {
      PuntoDeInteres punto = new PuntoDeInteres();
      punto.setNombre(pto.name);
      punto.setDireccion(pto.formattedAddress);
      punto.setDescripcion(pto.formattedAddress);
      punto.setIcon(pto.icon);
      punto.setRating((int) pto.rating);
      punto.setPlaceId(pto.placeId);

      if (pto.photos != null) {
        for (Photo photo : pto.photos) {
          punto.setPhotoReferences(photo.photoReference);
        }
      }

      listaDePuntosDeInteres.add(punto);
    }

    return listaDePuntosDeInteres;
  }
}
