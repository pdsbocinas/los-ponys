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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@PropertySource(value = {"classpath:application.properties"})
public class ControladorViaje {

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

  @RequestMapping("/viajes")
  public ModelAndView homeViaje(HttpServletRequest request) {
    ModelMap modelos = new ModelMap();
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
  }

  @RequestMapping(path = "/eliminar-viaje", method = RequestMethod.POST)
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public ModelAndView borrarViaje(@ModelAttribute("viaje") Viaje viaje, HttpServletResponse response) {
    Long id = viaje.getId();
    Viaje v = servicioViaje.obtenerViajePorId(id);
    servicioViaje.borrarViaje(v);
    return new ModelAndView("redirect:/home");
  }

  @RequestMapping(path = {"/viajes/{id}"}, method = RequestMethod.GET)
  public ModelAndView crearViajeView(@PathVariable("id") Integer id, HttpServletRequest request) {
    return new ModelAndView("viajes/create");
  }

  @RequestMapping(path = {"/viajes/{viaje_id}/recorridos"}, method = RequestMethod.GET)
  public ModelAndView crearRecorrido(@PathVariable("viaje_id") Long viaje_id) {
    ModelMap modelo = new ModelMap();
    List<Destino> destinos = servicioViaje.obtenerDestinosPorViaje(viaje_id);

    String origen = "";
    String destino = "";

    // llevar esta logica al servicio
    if (destinos.size() == 2) {
      origen = destinos.get(0).getPlaceId();
      destino = destinos.get(1).getPlaceId();
    } else if (destinos.size() > 2) {
      ArrayList<String> waypoint = new ArrayList<String>();
      for (int i = 1; i <= destinos.size(); i++) {
        if (i == 1) {
          origen = destinos.get(0).getPlaceId();
        } else if (i != destinos.size()) {
          waypoint.add(destinos.get(i - 1).getPlaceId());
        } else if (i == destinos.size()) {
          destino = destinos.get(i - 1).getPlaceId();
        }
      }
      modelo.put("waypoint", waypoint);
    }

    modelo.put("origen", origen);
    modelo.put("destino", destino);
    return new ModelAndView("viajes/recorridos", modelo);
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

  @RequestMapping(path = {"/viajes/{id}/comentar"}, method = RequestMethod.GET)
  @ResponseBody
  public ModelAndView comentarViaje(@PathVariable("id") Long id, HttpServletRequest request) throws InterruptedException, ApiException, IOException {

    ModelMap modelo = new ModelMap();
    modelo.put("id", id);

    Viaje viaje = servicioViaje.obtenerViajePorId(id);
    Usuario usuario = (Usuario) request.getSession().getAttribute("USER");

    if (usuario != null) {
      modelo.put("usuario_email", usuario.getEmail());
      modelo.put("viaje_id", viaje.getId());
      modelo.put("viaje_fechaInicio", viaje.getFechaInicio());
      modelo.put("viaje_fechaFin", viaje.getFechaFin());
      modelo.put("viaje_titulo", viaje.getTitulo());
    } else {
      return new ModelAndView("redirect:/home");
    }

    return new ModelAndView("viajes/comentar", modelo);
  }

  @RequestMapping(path = {"/viajes/comentar/enviar-comentario"}, method = RequestMethod.POST)
  @ResponseBody
  public ModelAndView enviarComentario(@RequestBody ComentarioDto comentarioDto) {
    ModelMap modelos = new ModelMap();

    Comentario comentario = new Comentario();

    Long viaje_id = comentarioDto.getViaje_id();
    String email_usuario = comentarioDto.getUsuario_email();
    String comment = comentarioDto.getTexto();
    String estado = "no leido";

    // TODO meter todo esto en el servicio de guardar comentario
    Viaje viaje = servicioViaje.obtenerViajePorId(viaje_id);
    Usuario usuario = servicioRegistroUsuario.obtenerUsuarioPorMail(email_usuario);

    comentario.setTexto(comment);
    comentario.setUsuario(usuario);
    comentario.setViaje(viaje);
    comentario.setEstado(estado);

    servicioComentario.guardarComentario(comentario);
    return new ModelAndView("viajes/comentar");
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
  public List<Comentario> obtenerComentariosNoLeidos(@RequestParam(value = "id") Integer userId, HttpServletRequest request) {
    List<Comentario> comentarios = servicioComentario.obtenerComentariosNoLeidos(userId);
    return comentarios;
  }

  @RequestMapping(path = {"/viajes/ver-comentarios"}, method = RequestMethod.GET)
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void cambiarEstadoDeComentariosNoLeidos(@RequestParam(value = "id") Integer id) {

    List<Comentario> comentarios = servicioComentario.obtenerComentariosNoLeidos(id);

    for (Comentario comentario : comentarios) {
      String leido = "leido";
      comentario.setEstado(leido);
      servicioComentario.guardarComentario(comentario);
    }
  }



  @RequestMapping(path = {"viajes/{viaje_id}/destino/{destino_id}/fecha"}, method = RequestMethod.GET)
  @ResponseBody
  public ModelAndView fechaDeUnDestino(@PathVariable("destino_id") Integer destino_id, @PathVariable("viaje_id") Long viaje_id) {

    Destino destino = new Destino();
    destino = servicioDestino.obtenerDestinoPorId(destino_id);
    Viaje viaje = new Viaje();
    viaje = servicioViaje.obtenerViajePorId(viaje_id);

    ModelMap modelo = new ModelMap();
    modelo.put("viaje_id", viaje_id);
    modelo.put("destino_id", destino_id);
    modelo.put("ciudad", destino.getCiudad());
    modelo.put("nombre", destino.getNombre());
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String fechaInicioFormateada = formatter.format(destino.getFechaInicio());
    String fechaHastaFormateada = formatter.format(destino.getFechaHasta());
    modelo.put("fechaInicio", fechaInicioFormateada);
    modelo.put("fechaHasta", fechaHastaFormateada);
    //modelo.put("fechaInicio", destino.getFechaInicio());
    //modelo.put("fechaHasta", destino.getFechaHasta());
    modelo.put("fechaDesdeViaje", viaje.getFechaInicio());
    modelo.put("fechaHastaViaje", viaje.getFechaFin());

    return new ModelAndView("destino/fecha", modelo);
  }

  @RequestMapping(path = {"viajes/{viaje_id}/destino/{destino_id}/vista"}, method = RequestMethod.GET)
  @ResponseBody
  public ModelAndView vistaDeUnDestino(@PathVariable("destino_id") Integer destino_id,
                                       @PathVariable("viaje_id") Long viaje_id) {

    Destino destino = new Destino();
    destino = servicioDestino.obtenerDestinoPorId(destino_id);
    List<Foto> fotos = servicioFoto.obtenerFotosPorDestinoId(destino_id);
    ModelMap modelo = new ModelMap();
    modelo.put("viaje_id", viaje_id);
    modelo.put("destino_id", destino_id);
    modelo.put("ciudad", destino.getCiudad());
    modelo.put("nombre", destino.getNombre());
    modelo.put("fotos", fotos);
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
  public ModelAndView elegirFechasDeDestinosPorViaje(@PathVariable("viaje_id") Long viaje_id) {

    ModelMap modelo = new ModelMap();
    List<Destino> destinos = servicioViaje.obtenerDestinosPorViaje(viaje_id);
    modelo.put("viaje_id", viaje_id);
    modelo.put("destinos", destinos);
    return new ModelAndView("viajes/mis-destinos", modelo);
  }

  @RequestMapping(path = {"viajes/{viaje_id}/destino/{destino_id}/guardarFechas"}, method = RequestMethod.POST)
  public ModelAndView guardarFechasdeDestinoPorViaje(HttpServletRequest req,
                                                     @PathVariable("destino_id") Integer destino_id,
                                                     @PathVariable("viaje_id") Long viaje_id) throws ParseException {

    Viaje viaje = new Viaje();
    viaje = servicioViaje.obtenerViajePorId(viaje_id);

    Destino destino = new Destino();
    destino = servicioDestino.obtenerDestinoPorId(destino_id);

    List<Destino> destinos = servicioViaje.obtenerDestinosPorViaje(viaje_id);

    String fechaDesde = req.getParameter("fechaInicio");
    String fechaHasta = req.getParameter("fechaHasta");


    ModelMap modelo = new ModelMap();


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Date fechaDesdeFormateada = sdf.parse(fechaDesde);
    Date fechaHastaFormateada = sdf.parse(fechaHasta);

    modelo.put("ciudad", destino.getCiudad());

    modelo.put("fechaDesdeViaje", viaje.getFechaInicio());
    modelo.put("fechaHastaViaje", viaje.getFechaFin());


    if (fechaDesdeFormateada.compareTo(fechaHastaFormateada) > 0) {
      modelo.put("error", "La fecha de inicio debe ser menor a la de fin");
      return new ModelAndView("/destino/fecha", modelo);
    } else if (fechaDesdeFormateada.compareTo(viaje.getFechaInicio()) < 0 ||
        fechaDesdeFormateada.compareTo(viaje.getFechaFin()) > 0) {
      modelo.put("error", "Te lo dijimos... tené en cuenta la fecha de tu viaje");
      return new ModelAndView("/destino/fecha", modelo);
    } else if (fechaHastaFormateada.compareTo(viaje.getFechaInicio()) < 0 ||
        fechaHastaFormateada.compareTo(viaje.getFechaFin()) > 0) {
      modelo.put("error", "Te lo dijimos... tené en cuenta la fecha de tu viaje");
      return new ModelAndView("destino/fecha", modelo);
    } else if (!destinos.isEmpty()) {
      for (Destino d : destinos
      ) {
        if (destino_id != d.getId()) {
          if (d.getFechaInicio() != null && d.getFechaHasta() != null) {
            if (fechaDesdeFormateada.compareTo(d.getFechaInicio()) > 0 &&
                fechaDesdeFormateada.compareTo(d.getFechaHasta()) < 0) {
              String mensaje = "La fecha se solapa con la de " + d.getCiudad();
              modelo.put("error", mensaje);
              return new ModelAndView("destino/fecha", modelo);
            } else if (fechaHastaFormateada.compareTo(d.getFechaInicio()) > 0 &&
                fechaHastaFormateada.compareTo(d.getFechaHasta()) < 0) {
              String mensaje = "La fecha se solapa con la de " + d.getCiudad();
              modelo.put("error", mensaje);
              return new ModelAndView("destino/fecha", modelo);
            }
          }

        }
      }
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
    modelo.put("fechaHasta", fechaHastaNormal);

    return new ModelAndView("destino/vista", modelo);
  }

}
