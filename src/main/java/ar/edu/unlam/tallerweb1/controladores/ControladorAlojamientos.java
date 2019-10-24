package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.servicios.ServicioAlojamiento;
import ar.edu.unlam.tallerweb1.servicios.ServicioRegistroUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioReserva;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
@PropertySource(value= {"classpath:application.properties"})
public class ControladorAlojamientos {

  @Inject
  private ServicioAlojamiento servicioAlojamiento;

  @Inject
  private ServicioReserva servicioReserva;

  @Inject
  private ServicioRegistroUsuario servicioRegistroUsuario;

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
    // String queries = request.getQueryString();
    List<Alojamiento> alojamientos = servicioAlojamiento.obtenerAlojamientosParametrizados(desde, hasta, precioDesde, precioHasta, rating, ofertas, descuento, offset, size);

    return alojamientos;
  }

  @RequestMapping(path = {"/alojamientos"}, method = RequestMethod.GET)
  public ModelAndView alojamientoView (HttpServletRequest request) {
    ModelMap modelos = new ModelMap();
    return new ModelAndView("alojamientos/index");
  }

  @RequestMapping(path = {"/alojamientos/{id}"}, method = RequestMethod.GET)
  public ModelAndView alojamientoView (@PathVariable("id") Integer id, @ModelAttribute("reservaDto") ReservaDto reservaDto, HttpServletRequest request) {
    ModelMap modelos = new ModelMap();
    modelos.put("reservaDto", new ReservaDto());
    HttpSession session = request.getSession();
    modelos.put("usuario", session.getAttribute("USER"));
    Alojamiento alojamiento = servicioAlojamiento.obtenerAlojamientoPorId(id);
    modelos.put("alojamiento", alojamiento);
    return new ModelAndView("alojamientos/detail", modelos);
  }

  @RequestMapping(path = {"/alojamientos/confirm-alojamiento"}, method = RequestMethod.POST)
  public ModelAndView submit(@ModelAttribute("reservaDto") ReservaDto reservaDto, BindingResult result, ModelMap model, HttpServletRequest request) {

    HttpSession session = request.getSession();
    Usuario usuario = (Usuario) session.getAttribute("USER");

    BindingResult r = result;

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
    servicioReserva.crearReservaParaAlojamiento(reserva);

    model.put("reserva", reserva);

    return new ModelAndView("alojamientos/confirm", model);
  }


  public void setServicioAlojamiento(ServicioAlojamiento servicioAlojamiento) {
    this.servicioAlojamiento = servicioAlojamiento;
  }
}
