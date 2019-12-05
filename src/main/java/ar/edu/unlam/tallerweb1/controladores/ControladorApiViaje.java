package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.servicios.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.TextSearchRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlacesSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller("/api")
@PropertySource(value = {"classpath:application.properties"})
public class ControladorApiViaje {

  @Inject
  private ServicioViaje servicioViaje;

  @Inject
  private ServicioEmail servicioEmail;

  @Inject
  private ServicioRegistroUsuario servicioRegistroUsuario;

  @Inject
  private ServicioComentario servicioComentario;

  @Inject
  private ServicioDestino servicioDestino;

  @Inject
  private ServicioFoto servicioFoto;

  @Value("${datasource.apiKey}")
  private String apiKey;
  private HttpServletRequest httpServletRequest;

  public void setServicioViaje(ServicioViaje servicioViaje) {
    this.servicioViaje = servicioViaje;
  }

  public void setServicioDestino(ServicioDestino servicioDestino) {
    this.servicioDestino = servicioDestino;
  }

  public void setServicioFoto(ServicioFoto servicioFoto) {
    this.servicioFoto = servicioFoto;
  }

  public void setServicioRegistroUsuario(ServicioRegistroUsuario servicioRegistroUsuario) {
    this.servicioRegistroUsuario = servicioRegistroUsuario;
  }

  public void setServicioEmail(ServicioEmail servicioEmail) {
    this.servicioEmail = servicioEmail;
  }

  public void setServicioComentario(ServicioComentario servicioComentario) {
    this.servicioComentario = servicioComentario;
  }

  public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
    this.httpServletRequest = httpServletRequest;
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
    // llevar logica de envio de mail al servicio

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
    if(viajeDto.getUsuarios() != null){
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
    }
    return viajeDto;
  }

  @RequestMapping(path = {"/api/viajes/{id}/destinos"}, method = RequestMethod.POST)
  @ResponseBody
  public DestinoDto guardarOActualizarDestinosPorViaje(@PathVariable Long id, @RequestBody DestinoDto destinosDto) throws InterruptedException, ApiException, IOException {
    servicioViaje.guardarDestinosPorViaje(id, destinosDto.getDestinos());
    return destinosDto;
  }

  @RequestMapping(path = {"/api/viajes/{id}/guardarDestinos"}, method = RequestMethod.POST)
  @ResponseBody
  public void guardarOActualizarDestinos(@PathVariable Long id, @RequestBody List<DestinoDto> destinosDto) throws InterruptedException, ApiException, IOException {
    servicioViaje.guardarDestinos(id, destinosDto);
  }

  @RequestMapping(path = {"/api/viajes/{id}/obtener-destinos"}, method = RequestMethod.GET)
  @ResponseBody
  public List<Destino> obtenerDestinosPorViaje(@PathVariable Long id) {
    List<Destino> destinos = servicioViaje.obtenerDestinosPorViaje(id);
    return destinos;
  }

  @RequestMapping(path = {"/api/mis-viajes"}, method = RequestMethod.GET)
  @ResponseBody
  public List<Viaje> obtenerMisViajes(HttpServletRequest request) {
    Usuario usuario = (Usuario) request.getSession().getAttribute("USER");
    Integer userId = usuario.getId();
    List<Viaje> viajes = servicioViaje.obtenerViajesPorUsuario(userId);
    return viajes;
  }

  @RequestMapping(path = {"/api/viajes-publicos"}, method = RequestMethod.GET)
  @ResponseBody
  public List<Viaje> obtenerViajesPublicos(HttpServletRequest request) {
    List<Viaje> viajes = servicioViaje.obtenerViajes();
    return viajes;
  }


  @RequestMapping(path = {"/api/viajes/{id}/comentarios"}, method = RequestMethod.POST)
  @ResponseBody
  public List<Comentario> obtenerComentariosPorViajeId(@PathVariable("id") Long viaje_id, HttpServletRequest request) throws InterruptedException, ApiException, IOException {
    Long id = viaje_id;
    List<Comentario> comentarios = servicioComentario.obtenerComentariosPorViajeId(viaje_id);
    return comentarios;
  }

  @RequestMapping(path = {"/api/viajes/comentarios/no-leido"}, method = RequestMethod.GET)
  @ResponseBody
  public List<Comentario> obtenerComentariosNoLeidos(@RequestParam(value = "id") Integer userId) {
    List<Comentario> comentarios = servicioComentario.obtenerComentariosNoLeidos(userId);
    return comentarios;
  }


}
