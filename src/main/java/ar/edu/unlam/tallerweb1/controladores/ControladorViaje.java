package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.servicios.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.inject.Inject;
import javax.servlet.ServletException;
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

  @Inject
  private ServicioComentario servicioComentario;

  @Inject
  private ServicioDestino servicioDestino;


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

  @RequestMapping(path = {"/api/viajes/{id}/guardarDestinos"}, method = RequestMethod.POST)
  @ResponseBody
  public void guardarOActualizarDestinos(@PathVariable Long id, @RequestBody  List<DestinoDto> destinosDto) throws InterruptedException, ApiException, IOException {
    servicioViaje.guardarDestinos(id, destinosDto);

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

  @RequestMapping(path = {"/api/viajes-publicos"}, method = RequestMethod.GET)
  @ResponseBody
  public List<Viaje> obtenerViajesPublicos(HttpServletRequest request){
    List<Viaje> viajes = servicioViaje.obtenerViajes();
    return viajes;
  }

  @RequestMapping(path = {"/viajes/{id}/comentar"}, method = RequestMethod.GET)
  @ResponseBody
  public ModelAndView comentarViaje(@PathVariable("id") Long id, HttpServletRequest request) throws InterruptedException, ApiException, IOException {
    ModelMap modelo = new ModelMap();
    modelo.put("id",id);

    Viaje viaje = servicioViaje.obtenerViajePorId(id);
    Usuario usuario = (Usuario) request.getSession().getAttribute("USER");

    modelo.put("usuario_email", usuario.getEmail());
    modelo.put("viaje_id",viaje.getId());
    modelo.put("viaje_fechaInicio",viaje.getFechaInicio());
    modelo.put("viaje_fechaFin",viaje.getFechaFin());
    modelo.put("viaje_titulo",viaje.getTitulo());


    return new ModelAndView("viajes/comentar", modelo);
  }

  @RequestMapping(path = {"/viajes/comentar/enviar-comentario"}, method = RequestMethod.POST)
  @ResponseBody
  public ModelAndView enviarComentario(@RequestBody Comentario comentario) {
    ModelMap modelos = new ModelMap();
    servicioComentario.guardarComentario(comentario);
    return new ModelAndView("viajes/comentar");
  }

  @RequestMapping(path = {"/api/viajes/{id}/comentarios"}, method = RequestMethod.GET)
  @ResponseBody
  public List<Comentario> obtenerComentariosPorViajeId(@PathVariable("id") Long viaje_id, HttpServletRequest request) throws InterruptedException, ApiException, IOException {

    List<Comentario> comentarios = servicioComentario.obtenerComentariosPorViajeId(viaje_id);
    return comentarios;
  }

  @RequestMapping(path = {"api/viajes/{viaje_id}/destino/{destino_id}/guardar-fecha"}, method = RequestMethod.POST)
  @ResponseBody
  public Integer guardarFechaDeUnDestino(@PathVariable("viaje_id") Long viaje_id,
                                              @PathVariable("destino_id") Integer destino_id,
                                              @RequestBody DestinoDto destinodto) {
    ModelMap modelos = new ModelMap();
    Viaje viaje = new Viaje();
    viaje = servicioViaje.obtenerViajePorId(viaje_id);
    //Date fechaInicio = new Date();
    if(destinodto.getFechaInicio().compareTo(viaje.getFechaInicio()) < 0 ||
            destinodto.getFechaInicio().compareTo(viaje.getFechaFin()) > 0){
      return 0;
    }
    if(destinodto.getFechaHasta().compareTo(viaje.getFechaInicio()) < 0 ||
            destinodto.getFechaHasta().compareTo(viaje.getFechaFin()) > 0){
      return 0;
    }
    servicioDestino.guardarFecha(destinodto, destino_id);
    Destino destino = new Destino();
    destino = servicioDestino.obtenerDestinoPorId(destino_id);

    return destino.getId();
  }

  @RequestMapping(path = {"viajes/{viaje_id}/destino/{destino_id}/fecha"}, method = RequestMethod.GET)
  @ResponseBody
  public ModelAndView fechaDeUnDestino(@PathVariable("destino_id") Integer destino_id, @PathVariable("viaje_id") Long viaje_id) {

    Destino destino = new Destino();
    destino = servicioDestino.obtenerDestinoPorId(destino_id);
      Viaje viaje = new Viaje();
      viaje = servicioViaje.obtenerViajePorId(viaje_id);

    ModelMap modelo = new ModelMap();
    modelo.put("viaje_id",viaje_id);
    modelo.put("destino_id", destino_id);
    modelo.put("ciudad", destino.getCiudad());
    modelo.put("nombre", destino.getNombre());
    modelo.put("fechaInicio", destino.getFechaInicio());
    modelo.put("fechaHasta", destino.getFechaHasta());
      modelo.put("fechaDesdeViaje", viaje.getFechaInicio());
      modelo.put("fechaHastaViaje", viaje.getFechaFin());

    return new ModelAndView("destino/fecha", modelo);
  }

  @RequestMapping(path = {"viajes/{viaje_id}/destino/{destino_id}/vista"}, method = RequestMethod.GET)
  @ResponseBody
  public ModelAndView vistaDeUnDestino(@PathVariable("destino_id") Integer destino_id, @PathVariable("viaje_id") Long viaje_id) {

    Destino destino = new Destino();
    destino = servicioDestino.obtenerDestinoPorId(destino_id);



    ModelMap modelo = new ModelMap();
    modelo.put("viaje_id",viaje_id);
    modelo.put("destino_id", destino_id);
    modelo.put("ciudad", destino.getCiudad());
    modelo.put("nombre", destino.getNombre());
    /*modelo.put("fechaInicio", destino.getFechaInicio());
    modelo.put("fechaHasta", destino.getFechaHasta());*/
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
      String fechaDesdeNormal = formatter.format(destino.getFechaInicio());
      String fechaHastaNormal = formatter.format(destino.getFechaHasta());
      modelo.put("fechaInicio", fechaDesdeNormal);
      modelo.put("fechaHasta", fechaHastaNormal);

    return new ModelAndView("destino/vista", modelo);
  }

    @RequestMapping(path = {"/viajes/{viaje_id}/destino"}, method = RequestMethod.GET)
    public ModelAndView elegirFechasDeDestinosPorViaje (@PathVariable ("viaje_id") Long viaje_id){
        ModelMap modelo = new ModelMap();

        List<Destino> destinos = servicioViaje.obtenerDestinosPorViaje(viaje_id);
        modelo.put("destinos", destinos);
      return new ModelAndView("viajes/mis-destinos", modelo);
    }

  /*@RequestMapping(path = {"viajes/{id}/guardarFechas"}, method = RequestMethod.POST)
    public ModelAndView guardarFechasdeDestinoPorViaje( @RequestBody DestinoDto destinodto){

      Date fechaDesde = destinodto.getFechaInicio();
      Date fechaHasta = destinodto.getFechaHasta();

      ModelMap modelo = new ModelMap();

      modelo.put("fechaDesde", fechaDesde);
      modelo.put("fechaHasta",fechaHasta);

    return new ModelAndView("viajes/fechas", modelo);
    }*/
  @RequestMapping(path = {"viajes/{viaje_id}/destino/{destino_id}/guardarFechas"}, method = RequestMethod.POST)
  public ModelAndView guardarFechasdeDestinoPorViaje (HttpServletRequest req,
                                                      @PathVariable("destino_id") Integer destino_id,
                                                      @PathVariable("viaje_id") Long viaje_id) throws ParseException {

    Viaje viaje = new Viaje();
    viaje = servicioViaje.obtenerViajePorId(viaje_id);

    Destino destino = new Destino();
    destino = servicioDestino.obtenerDestinoPorId(destino_id);

    String fechaDesde = req.getParameter("fechaInicio");
    String fechaHasta = req.getParameter("fechaHasta");


    ModelMap modelo = new ModelMap();


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Date fechaDesdeFormateada = sdf.parse(fechaDesde);
    Date fechaHastaFormateada = sdf.parse(fechaHasta);

    modelo.put("ciudad", destino.getCiudad());

    modelo.put("fechaDesdeViaje", viaje.getFechaInicio());
    modelo.put("fechaHastaViaje", viaje.getFechaFin());

    if(fechaDesdeFormateada.compareTo(viaje.getFechaInicio()) < 0 ||
            fechaDesdeFormateada.compareTo(viaje.getFechaFin()) > 0){
      modelo.put("error","Te lo dijimos... tené en cuenta la fecha de tu viaje");
      return new ModelAndView("/destino/fecha",modelo);
    }
    if(fechaHastaFormateada.compareTo(viaje.getFechaInicio()) < 0 ||
            fechaHastaFormateada.compareTo(viaje.getFechaFin()) > 0){
      modelo.put("error","Te lo dijimos... tené en cuenta la fecha de tu viaje");
      return new ModelAndView("destino/fecha",modelo);
    }

    DestinoDto destinoDto = new DestinoDto();
    destinoDto.setId(destino_id);
    destinoDto.setFechaInicio(fechaDesdeFormateada);
    destinoDto.setFechaHasta(fechaHastaFormateada);

    servicioDestino.guardarFecha(destinoDto, destino_id);

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
      String fechaDesdeNormal = formatter.format(fechaDesdeFormateada);
      String fechaHastaNormal = formatter.format(fechaHastaFormateada);
    modelo.put("ciudad", destino.getCiudad());
    modelo.put("fechaInicio", fechaDesdeNormal);
    modelo.put("fechaHasta",fechaHastaNormal);


    return new ModelAndView("destino/vista", modelo);
  }


}
