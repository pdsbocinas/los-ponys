package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Viaje;
import ar.edu.unlam.tallerweb1.servicios.ServicioViaje;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.TextSearchRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlacesSearchResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
public class ControladorViaje  {

  @Inject
  private ServicioViaje servicioViaje;

  @RequestMapping(value= "/viajesget", method = RequestMethod.GET) // agregar: consumes="application/json" si va por POST
  @ResponseBody
  public ModelAndView creaViaje(HttpServletRequest request){

    String titulo = request.getParameter("titulo");

    Viaje viaje = new Viaje();
    viaje.setTitulo(titulo);

    servicioViaje.guardarViaje(viaje);

    ModelAndView modelAndView = new ModelAndView("viajes/create");

    modelAndView.addObject("titulo", viaje.getTitulo());
    return modelAndView;

  }

  @RequestMapping("/viajes")
  public ModelAndView homeViaje () {
    return new ModelAndView("viajes/travel");
  }

  @RequestMapping("/viajes/crear")
  public ModelAndView crearViaje () {
    return new ModelAndView("viajes/create");
  }

  @RequestMapping(path = {"/api/queries"}, method = RequestMethod.GET)
  @ResponseBody
  public Object obtenerStringJson(HttpServletRequest request,
                                  HttpServletResponse response) throws InterruptedException, ApiException, IOException {
    GeoApiContext context = new GeoApiContext.Builder()
        .apiKey("AIzaSyD8feo0IzBJZWjmAEhc2PIPRvBqWhBk2Jg")
        .build();
    String jsonString = request.getParameter("keyword");

    // devuelve un json de resultado aleatorios
    PlacesSearchResponse contextPlaceApi = new TextSearchRequest(context).query(jsonString).await();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(contextPlaceApi.results);
  }
}
