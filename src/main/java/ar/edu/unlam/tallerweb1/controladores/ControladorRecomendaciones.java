package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.PuntoDeInteres;
import ar.edu.unlam.tallerweb1.servicios.ServicioRecomendador;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.TextSearchRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlacesSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@PropertySource(value= {"classpath:application.properties"})
public class ControladorRecomendaciones {

  @Inject
  private ServicioRecomendador servicioRecomendador;

  @RequestMapping(path = {"/api/recomendaciones"}, method = RequestMethod.GET)
  @ResponseBody
  public List<PuntoDeInteres> homeRecomendaciones (HttpServletRequest request) throws InterruptedException, ApiException, IOException {
    String tipo = request.getParameter("tipo");
    String destino = request.getParameter("destino");

    List<PuntoDeInteres> puntosDeInteres = servicioRecomendador.obtenerPuntosDeInteres(tipo, destino);
    return puntosDeInteres;
  }
}
