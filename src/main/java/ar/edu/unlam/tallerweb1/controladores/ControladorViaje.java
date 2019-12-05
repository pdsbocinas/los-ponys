package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.servicios.*;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;

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
  private HttpServletRequest httpServletRequest;
  private HttpSession httpSession;

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

  public void setHttpSession(HttpSession httpSession) {
      this.httpSession = httpSession;
  }

  @RequestMapping("/viajes")
  public ModelAndView homeViaje(HttpServletRequest request) throws JsonProcessingException {
    ModelMap modelos = new ModelMap();
    Usuario usuario = (Usuario) request.getSession().getAttribute("USER");
    // Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("USER");
    ObjectMapper destinosJson = new ObjectMapper();

    if (usuario != null) {
      Integer userId = usuario.getId();
      String email = usuario.getEmail();
      modelos.put("usuarioJson", destinosJson.writeValueAsString(usuario));
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
  public ModelAndView borrarViaje(@ModelAttribute("viaje") Viaje viaje, HttpServletRequest request, HttpServletResponse response) {
    Usuario usuario = (Usuario) request.getSession().getAttribute("USER");
    Integer userId = usuario.getId();
    Long id = viaje.getId();
    Viaje v = servicioViaje.obtenerViajePorId(id);
    List<Comentario> comentarios = servicioComentario.obtenerComentariosPorViajeId(v.getId());
    List<Mail> mails = servicioEmail.obtenerEmailsPorUsuario(userId);
    List<Foto> fotos = servicioFoto.obtenerFotosDeDestinosDelViaje(v.getId());
    servicioFoto.borrarFotos(fotos);
    servicioEmail.borrarEmails(mails);
    servicioComentario.borrarComentarios(comentarios);
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
      modelo.put("viajeFotoPortada",servicioFoto.obtenerFotoDePortada(viaje.getId()).getName());
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
    /*Long viaje_id = comentarioDto.getViaje_id();
    String email_usuario = comentarioDto.getUsuario_email();
    String comment = comentarioDto.getTexto();
    String estado = "no leido";

    // TODO meter todo esto en el servicio de guardar comentario
    Viaje viaje = servicioViaje.obtenerViajePorId(viaje_id);
    Usuario usuario = servicioRegistroUsuario.obtenerUsuarioPorMail(email_usuario);

    comentario.setTexto(comment);
    comentario.setUsuario(usuario);
    comentario.setViaje(viaje);
    comentario.setEstado(estado);*/
    comentario = servicioComentario.convertirComentarioDtoAComentario(comentarioDto);
    servicioComentario.guardarComentario(comentario);
    return new ModelAndView("viajes/comentar");
  }

  @RequestMapping(path = {"/viajes/ver-comentarios"}, method = RequestMethod.GET)
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void cambiarEstadoDeComentariosNoLeidos(@RequestParam(value = "id") Integer id) {

    List<Comentario> comentarios = servicioComentario.obtenerComentariosNoLeidos(id);
    if(!comentarios.isEmpty()){
      for (Comentario comentario : comentarios) {
        String leido = "leido";
        comentario.setEstado(leido);
        servicioComentario.guardarComentario(comentario);
      }
    }

  }

  @RequestMapping(path = {"viajes/{viaje_id}/destino/{destino_id}/fecha"}, method = RequestMethod.GET)
  @ResponseBody
  public ModelAndView elegirFechaDeUnDestino(@PathVariable("destino_id") Integer destino_id,
                                        @PathVariable("viaje_id") Long viaje_id) {

    Destino destino = servicioDestino.obtenerDestinoPorId(destino_id);
    Viaje viaje = servicioViaje.obtenerViajePorId(viaje_id);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    ModelMap modelo = new ModelMap();

    modelo.put("viaje_id", viaje_id);
    modelo.put("destino_id", destino_id);
    modelo.put("ciudad", destino.getCiudad());
    modelo.put("nombre", destino.getNombre());
    modelo.put("fechaDesdeViaje", viaje.getFechaInicio());
    modelo.put("fechaHastaViaje", viaje.getFechaFin());

    if(destino.getFechaInicio()!= null){
      String fechaInicioFormateada = formatter.format(destino.getFechaInicio());
      modelo.put("fechaInicio", fechaInicioFormateada);
    }
    if(destino.getFechaHasta()!=null){
      String fechaHastaFormateada = formatter.format(destino.getFechaHasta());
      modelo.put("fechaHasta", fechaHastaFormateada);
    }

    return new ModelAndView("destino/fecha", modelo);
  }

  @RequestMapping(path = {"viajes/{viaje_id}/destino/{destino_id}/vista"}, method = RequestMethod.GET)
  @ResponseBody
  public ModelAndView vistaDeUnDestino(@PathVariable("destino_id") Integer destino_id,
                                       @PathVariable("viaje_id") Long viaje_id,
                                       HttpServletRequest request) {
    ModelMap modelo = new ModelMap();
    Viaje viaje = servicioViaje.obtenerViajePorId(viaje_id);
    setHttpServletRequest(request);
    Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("USER");
    List<Usuario> usuarios = viaje.getUsuarios();
    for (Usuario u: usuarios
         ) {
      if (usuario.getId() == u.getId()){
        modelo.put("permisoUsuario", true);
      }
    }

    Destino destino = new Destino();
    destino = servicioDestino.obtenerDestinoPorId(destino_id);
    List<Foto> fotos = servicioFoto.obtenerFotosPorDestinoId(destino_id);

    modelo.put("viaje_id", viaje_id);
    modelo.put("destino_id", destino_id);
    modelo.put("ciudad", destino.getCiudad());
    modelo.put("nombre", destino.getNombre());
    modelo.put("fotos", fotos);

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    String fechaDesdeNormal = formatter.format(destino.getFechaInicio());
    String fechaHastaNormal = formatter.format(destino.getFechaHasta());
    modelo.put("fechaInicio", fechaDesdeNormal);
    modelo.put("fechaHasta", fechaHastaNormal);

    return new ModelAndView("destino/vista", modelo);
  }

  @RequestMapping(path = {"/viajes/{viajeId}/destino"}, method = RequestMethod.GET)
  public ModelAndView vistaDelViaje (@PathVariable("viajeId") Long viajeId,
                                                     @ModelAttribute("errorFotoPortada") String errorFotoPortada) throws JsonProcessingException {

    ModelMap modelo = new ModelMap();
    Viaje viaje = servicioViaje.obtenerViajePorId(viajeId);
    List<Destino> destinos = servicioViaje.obtenerDestinosPorViaje(viajeId);
    Foto foto =  servicioFoto.obtenerFotoDePortada(viajeId);

    modelo.put("viajeId", viajeId);
    modelo.put("viajeTitulo", viaje.getTitulo());
    modelo.put("destinos", destinos);
    modelo.put("errorFotoPortada", errorFotoPortada);
    if(foto != null){
      modelo.put("fotoPortada",foto.getName());
    }

    ObjectMapper destinosJson = new ObjectMapper();
    modelo.put("destinosJson", destinosJson.writeValueAsString(destinos));

    return new ModelAndView("viajes/mis-destinos", modelo);
  }

  @RequestMapping(path = {"viajes/{viaje_id}/destino/{destino_id}/guardarFechas"}, method = RequestMethod.POST)
  public ModelAndView guardarFechasDeDestinoPorViaje(HttpServletRequest req,
                                                     @PathVariable("destino_id") Integer destino_id,
                                                     @PathVariable("viaje_id") Long viaje_id) throws ParseException {

    Viaje viaje = servicioViaje.obtenerViajePorId(viaje_id);

    Destino destino = new Destino();
    destino = servicioDestino.obtenerDestinoPorId(destino_id);

    List<Destino> destinos = servicioViaje.obtenerDestinosPorViaje(viaje_id);

    String fechaDesde = req.getParameter("fechaInicio");
    String fechaHasta = req.getParameter("fechaHasta");

    ModelMap modelo = new ModelMap();

    modelo.put("ciudad", destino.getCiudad());
    modelo.put("fechaDesdeViaje", viaje.getFechaInicio());
    modelo.put("fechaHastaViaje", viaje.getFechaFin());

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Date fechaDesdeFormateada = sdf.parse(fechaDesde);
    Date fechaHastaFormateada = sdf.parse(fechaHasta);

    String mensajeGuardarFecha = servicioViaje.validaFecha(destino,destinos,viaje,fechaDesdeFormateada,fechaHastaFormateada);
    if(mensajeGuardarFecha!= "Ok"){
      modelo.put("error", mensajeGuardarFecha);
      return new ModelAndView("/destino/fecha", modelo);
    }

    destino.setFechaInicio(fechaDesdeFormateada);
    destino.setFechaHasta(fechaHastaFormateada);

    servicioDestino.guardarDestino(destino);

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    String fechaDesdeNormal = formatter.format(fechaDesdeFormateada);
    String fechaHastaNormal = formatter.format(fechaHastaFormateada);
    modelo.put("ciudad", destino.getCiudad());
    modelo.put("fechaInicio", fechaDesdeNormal);
    modelo.put("fechaHasta", fechaHastaNormal);

    return new ModelAndView("destino/vista", modelo);
  }

  @RequestMapping("viajes/{viajeId}/fotodeportada")
  public ModelAndView mostrarTodasLasFotosDeLosDestinosDelViaje(@PathVariable ("viajeId") Long viajeId) {

    Viaje viaje = servicioViaje.obtenerViajePorId(viajeId);
    List<Foto> fotos = servicioFoto.obtenerFotosDeDestinosDelViaje(viajeId);
    ModelMap modelo = new ModelMap();
    if(fotos != null){
      modelo.put("fotos", fotos);
    }else{
      modelo.put("fotos",null);
    }

    Foto foto =new Foto();

    modelo.addAttribute("foto", foto);
    return new ModelAndView("viajes/elegirFotoDePortada", modelo);
  }

  @RequestMapping("viajes/{viajeId}/submitForm")
  public ModelAndView seleccionarFotoDePortada(@ModelAttribute("foto") Foto foto,
                           @PathVariable("viajeId") Long viajeId){

    Foto fotoPortada = servicioFoto.obtenerFoto(foto);
    Boolean result = servicioFoto.elegirFotoComoPortada(fotoPortada);
    ModelMap modelo = new ModelMap();
    if (!result){
      modelo.put("errorFotoPortada","No se pudo seleccionar la foto de portada");
    }

    return new ModelAndView("redirect:/viajes/"+viajeId+"/destino",modelo);

  }

  public ViajeDto crearViaje(ViajeDto viajeDto) {
    return viajeDto;
  }
}
