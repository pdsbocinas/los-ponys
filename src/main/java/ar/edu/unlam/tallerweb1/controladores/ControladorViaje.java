package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.servicios.ServicioEmail;
import ar.edu.unlam.tallerweb1.servicios.ServicioRegistroUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioViaje;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.TextSearchRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlacesSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@PropertySource(value= {"classpath:application.properties"})
public class ControladorViaje  {

  @Inject
  private ServicioViaje servicioViaje;

  @Inject
  private ServicioEmail servicioEmail;

  @Inject
  private ServicioRegistroUsuario servicioRegistroUsuario;

  @Value("${datasource.apiKey}")
  private String apiKey;

  @RequestMapping("/viajes")
  public ModelAndView homeViaje (HttpServletRequest request) {
    ModelMap modelos = new ModelMap();
    ObjectMapper Obj = new ObjectMapper();
    // List<Viaje> viajes = servicioViaje.obtenerViajes();
    Usuario usuario = (Usuario) request.getSession().getAttribute("USER");

    if (usuario != null) {
      Integer userId = usuario.getId();
      String email = usuario.getEmail();
      modelos.put("user_id", userId);
      modelos.put("email", email);
      List<Viaje> viajes = servicioViaje.obtenerViajesPorUsuario(userId);
      modelos.put("viajes", viajes);
      return new ModelAndView("viajes/travel", modelos);
    } else {
      modelos.put("user", null);
      modelos.put("notFound", "Por favor logueate o registrate");
    }

    return new ModelAndView("redirect:/home", modelos);

/*    try {
      String jsonStr = Obj.writeValueAsString(usuario);
      modelos.put("usuario", jsonStr);
      // Displaying JSON String
      System.out.println(jsonStr);
    }

    catch (IOException e) {
      e.printStackTrace();
    }*/

  }

  @RequestMapping(path = "/eliminar-viaje", method = RequestMethod.GET)
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public ModelAndView borrarViaje(@RequestParam(value = "id") Long viajeId, HttpServletResponse response) {
    servicioViaje.borrarViaje(viajeId);
    return new ModelAndView("redirect:/home");
  }

  @RequestMapping(path = {"/viajes/{id}"}, method = RequestMethod.GET)
  public ModelAndView crearViajeView (@PathVariable("id") Integer id, HttpServletRequest request) {
    ModelMap modelos = new ModelMap();

    return new ModelAndView("viajes/create");
  }

  @RequestMapping(path = {"/viajes/{id}/recorridos"}, method = RequestMethod.GET)
  @CrossOrigin
  public ModelAndView crearRecorrido () {
    return new ModelAndView("viajes/recorridos");
  }

  @RequestMapping(path = {"/api/destinos"}, method = RequestMethod.GET)
  @ResponseBody
  public Object obtenerDestinos(HttpServletRequest request,
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

  @RequestMapping(path = {"/api/viajes"}, method = RequestMethod.POST)
  @ResponseBody
  public ViajeDto crearViaje(@RequestBody ViajeDto viajeDto) throws InterruptedException, ApiException, IOException {

    // todo agregar validaciones de los datos que vienen, por ejemplo que no sean null, cosas por el estilo
    // ViajeDto es el modelo que me viene del Frontend, una clase DTO es para mapear un Json que no corresponde a mi modelo de negocio
    // el servicio crearViaje me va devolver el id del viaje, por ende se lo seteo al controller
    viajeDto.setId(
        servicioViaje.crearViaje(
            viajeDto.getTitulo(),
            viajeDto.getFechaInicio(),
            viajeDto.getFechaFin(),
            viajeDto.getPrivacidad(),
            viajeDto.getDestinos(),
            viajeDto.getUsuarios()
        )
    );

    for (String email : viajeDto.getUsuarios()) {
      Mail mail = new Mail();
      Usuario usuario = servicioRegistroUsuario.obtenerUsuarioPorMail(email);

      if (usuario != null) {
        mail.setUsuario(usuario);
        mail.setPara(email);
        mail.setContenido("te agregaron exitosamente");
        mail.setAsunto("Anotado para viajar!!");
        servicioEmail.mandarMail(mail);
        servicioEmail.guardarEmail(mail);
      }
    }

    return viajeDto;
  }

  @RequestMapping(path = {"/api/viajes/{id}/destinos"}, method = RequestMethod.POST)
  @ResponseBody
  public DestinoDto guardarOActualizarDestinosPorViaje(@PathVariable Long id, @RequestBody DestinoDto destinosDto) throws InterruptedException, ApiException, IOException {
    servicioViaje.guardarDestinosPorViaje(id, destinosDto.getDestinos());
    return destinosDto;
  }

  @RequestMapping(path = {"/api/viajes/{id}/obtener-destinos"}, method = RequestMethod.GET)
  @ResponseBody
  public List<Destino> obtenerDestinosPorViaje(@PathVariable Long id){
    List<Destino> destinos = servicioViaje.obtenerDestinosPorViaje(id);
    return destinos;
  }

  @RequestMapping(path = {"/api/mis-viajes"}, method = RequestMethod.GET)
  @ResponseBody
  public List<Viaje> obtenerMisViajes(HttpServletRequest request){
    Usuario usuario = (Usuario) request.getSession().getAttribute("USER");
    Integer userId = usuario.getId();
    List<Viaje> viajes = servicioViaje.obtenerViajesPorUsuario(userId);
    return viajes;
  }
}
