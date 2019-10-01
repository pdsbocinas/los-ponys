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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ControladorViaje {

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
