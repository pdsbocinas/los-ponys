package ar.edu.unlam.tallerweb1.controladores;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.TextSearchRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlacesSearchResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class ControladorViaje {

  @RequestMapping(path = {"/viajes"}, method = RequestMethod.GET)
  public ModelAndView homeViaje () {
    return new ModelAndView("travel");
  }

  @RequestMapping(path = {"/viajes/crear"}, method = RequestMethod.GET)
  public ModelAndView crearViaje () {
    return new ModelAndView("create");
  }

  @RequestMapping(path = {"/api/queries"}, method = RequestMethod.GET)
  @ResponseBody
  public Object obtenerStringJson(@RequestParam(value = "keyword") String keyword) throws InterruptedException, ApiException, IOException {
    GeoApiContext context = new GeoApiContext.Builder()
        .apiKey("AIzaSyD8feo0IzBJZWjmAEhc2PIPRvBqWhBk2Jg")
        .build();

    // devuelve un json de resultado aleatorios
    PlacesSearchResponse contextPlaceApi = new TextSearchRequest(context).query(keyword).await();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(contextPlaceApi.results);
  }
}
