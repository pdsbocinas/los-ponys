package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.servicios.ServicioDestino;
import ar.edu.unlam.tallerweb1.servicios.ServicioViaje;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.TextSearchRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlacesSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@PropertySource(value= {"classpath:application.properties"})
public class ControladorViaje  {

  @Inject
  private ServicioViaje servicioViaje;

  @Inject
  private ServicioDestino servicioDestino;

  @Value("${datasource.apiKey}")
  private String apiKey;

/*  @RequestMapping(value= "/viajesget", method = RequestMethod.GET) // agregar: consumes="application/json" si va por POST
  @ResponseBody
  public ModelAndView creaViaje(HttpServletRequest request){

    String titulo = request.getParameter("titulo");

    Viaje viaje = new Viaje();
    viaje.setTitulo(titulo);

    servicioViaje.guardarViaje(viaje);

    ModelAndView modelAndView = new ModelAndView("viajes/create");

    modelAndView.addObject("titulo", viaje.getTitulo());
    return modelAndView;

  }*/

  @RequestMapping("/viajes")
  public ModelAndView homeViaje () {
    // aca hay que mostrar si hay viajes creados

    ModelMap modelos = new ModelMap();
    List<Viaje> viajes = servicioViaje.obtenerViajes();
    modelos.put("viajes", viajes);
    return new ModelAndView("viajes/travel", modelos);
  }

  @RequestMapping(path = {"/viajes/{id}"}, method = RequestMethod.GET)
  public ModelAndView crearViaje (@PathVariable("id") Integer id, HttpServletRequest request) {

    return new ModelAndView("viajes/create");
  }

  @RequestMapping(path = {"/api/getDestinations"}, method = RequestMethod.GET)
  @ResponseBody
  public Object obtenerStringJson(HttpServletRequest request,
                                  HttpServletResponse response) throws InterruptedException, ApiException, IOException {
    GeoApiContext context = new GeoApiContext.Builder()
        .apiKey(apiKey)
        .build();
    String jsonString = request.getParameter("keyword");

    // devuelve un json de resultado aleatorios
    PlacesSearchResponse contextPlaceApi = new TextSearchRequest(context).query(jsonString).await();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    return gson.toJson(contextPlaceApi.results);
  }

  @RequestMapping(path = {"/guardarViaje"}, method = RequestMethod.POST)
  @ResponseBody
  public ViajeDto crearViaje(@RequestBody ViajeDto viajeDto) throws InterruptedException, ApiException, IOException {

    // todo agregar validaciones de los datos que vienen, por ejemplo que no sean null, cosas por el estilo
    viajeDto.setId(servicioViaje.crearViaje(viajeDto.getTitulo(), viajeDto.getFechaInicio(), viajeDto.getFechaFin(), viajeDto.getDestinos(), viajeDto.getUsuarios()));
    return viajeDto;
  }
}
