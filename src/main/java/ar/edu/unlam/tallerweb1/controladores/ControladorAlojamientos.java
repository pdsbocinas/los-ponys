package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.servicios.ServicioAlojamiento;
import ar.edu.unlam.tallerweb1.servicios.ServicioDestino;
import ar.edu.unlam.tallerweb1.servicios.ServicioRegistroUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioReserva;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.errors.ApiException;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@PropertySource(value= {"classpath:application.properties"})
public class ControladorAlojamientos {

  @Inject
  private ServicioAlojamiento servicioAlojamiento;

  @Inject
  private ServicioReserva servicioReserva;

  @Inject
  private ServicioDestino servicioDestino;

  @Inject
  private ServicioRegistroUsuario servicioRegistroUsuario;

  public void setServicioRegistroUsuario(ServicioRegistroUsuario servicioRegistroUsuario) {
    this.servicioRegistroUsuario = servicioRegistroUsuario;
  }

  public void setUsuarioAndErrors (HttpServletRequest request, ModelMap model) throws JsonProcessingException {
    ObjectMapper usuarioJson = new ObjectMapper();

    HashMap<String, String> errors = new HashMap<>();
    errors.put("errorLogin", "no hubo errores");

    String errorLogin = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(errors);

    Usuario usuario = (Usuario) request.getSession().getAttribute("USER");
    model.put("usuario", usuarioJson.writerWithDefaultPrettyPrinter().writeValueAsString(usuario));
    model.put("errorLogin", errorLogin);
  }

  @RequestMapping(path = "/api/alojamientos", method = RequestMethod.GET)
  @ResponseBody
  public List<Alojamiento> homeAlojamientos(
      @RequestParam(value = "desde", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date desde,
      @RequestParam(value = "hasta", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date hasta,
      @RequestParam(value = "precioDesde", required = false) Integer precioDesde,
      @RequestParam(value = "precioHasta", required = false) Integer precioHasta,
      @RequestParam(value = "rating", required = false) Integer rating,
      @RequestParam(value = "ofertas", required = false) Boolean ofertas,
      @RequestParam(value = "descuento", required = false) Integer descuento,
      @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
      @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
      HttpServletRequest request
  ) {
    List<Alojamiento> alojamientos = servicioAlojamiento.obtenerAlojamientosParametrizados(desde, hasta, precioDesde, precioHasta, rating, ofertas, descuento, offset, size);

    return alojamientos;
  }

  @RequestMapping(path = {"/alojamientos"}, method = RequestMethod.GET)
  public ModelAndView alojamientoView (HttpServletRequest request) throws JsonProcessingException {
    ModelMap model = new ModelMap();
    this.setUsuarioAndErrors(request, model);
    return new ModelAndView("alojamientos/index");
  }

  @RequestMapping(path = {"viajes/{viaje_id}/destino/{destino_id}/alojamiento"}, method = RequestMethod.GET)
  public ModelAndView alojamientosPorDestino (@PathVariable("viaje_id") Long viaje_id,
                                              @PathVariable("destino_id") Integer destino_id,
                                              HttpServletRequest request) throws InterruptedException, ApiException, IOException {
    ModelMap modelo = new ModelMap();
    this.setUsuarioAndErrors(request, modelo);
    Destino destino = servicioDestino.obtenerDestinoPorId(destino_id);
    String ciudad = "hoteles en " + destino.getCiudad();
    servicioAlojamiento.guardarAlojamientos(ciudad);
    modelo.put("ciudad",destino.getCiudad());
    return new ModelAndView("alojamientos/index", modelo);
  }

  @RequestMapping(path = {"viajes/{viaje_id}/destino/{destino_id}/alojamiento/{id}"}, method = RequestMethod.GET)
  public ModelAndView alojamientoView (@PathVariable("viaje_id") Long viaje_id,@PathVariable("destino_id") Integer destino_id, @PathVariable("id") Integer id, @ModelAttribute("reservaDto") ReservaDto reservaDto, HttpServletRequest request) throws JsonProcessingException {
    ModelMap modelos = new ModelMap();
    this.setUsuarioAndErrors(request, modelos);
    modelos.put("reservaDto", new ReservaDto());
    HttpSession session = request.getSession();
    modelos.put("usuario", session.getAttribute("USER"));
    Alojamiento alojamiento = servicioAlojamiento.obtenerAlojamientoPorId(id);
    modelos.put("alojamiento", alojamiento);
    return new ModelAndView("alojamientos/detail", modelos);
  }

  @RequestMapping(path = {"viajes/{viaje_id}/destino/{destino_id}/alojamiento/confirm-alojamiento"}, method = RequestMethod.GET)
  public ModelAndView crearReservaParaAlojamiento(@PathVariable("viaje_id") Long viaje_id,@PathVariable("destino_id") Integer destino_id, @ModelAttribute("reservaDto") ReservaDto reservaDto, BindingResult result, ModelMap model, HttpServletRequest request) throws JsonProcessingException {

    HttpSession session = request.getSession();
    BindingResult r = result;
    ModelMap modelo = new ModelMap();

    this.setUsuarioAndErrors(request, modelo);
    Integer user_id = reservaDto.getUser_id();
    Integer alojamiento_id = reservaDto.getAlojamiento_id();
    Date checkin = reservaDto.getCheckin();
    Date checkout = reservaDto.getCheckout();

    Usuario usuarioFound = servicioRegistroUsuario.consultarUsuarioPorId(user_id);
    Alojamiento alojamiento = servicioAlojamiento.obtenerAlojamientoPorId(alojamiento_id);

    Reserva reserva = new Reserva();
    reserva.setAlojamiento(alojamiento);
    reserva.setCheckin(checkin);
    reserva.setCheckout(checkout);
    reserva.setUsuario(usuarioFound);
    try {
      servicioReserva.crearReservaParaAlojamiento(reserva);
    } catch (Exception e) {
      return new ModelAndView("error");
    }
    modelo.put("reserva", reserva);

    return new ModelAndView("alojamientos/confirm", modelo);
  }


  public void setServicioAlojamiento(ServicioAlojamiento servicioAlojamiento) {
    this.servicioAlojamiento = servicioAlojamiento;
  }

  public void setServicioReserva(ServicioReserva servicioReserva) {
    this.servicioReserva = servicioReserva;
  }
}
