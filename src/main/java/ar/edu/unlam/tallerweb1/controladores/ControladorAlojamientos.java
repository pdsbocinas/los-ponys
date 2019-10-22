package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Alojamiento;
import ar.edu.unlam.tallerweb1.modelo.Viaje;
import ar.edu.unlam.tallerweb1.servicios.ServicioAlojamiento;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Controller
@PropertySource(value= {"classpath:application.properties"})
public class ControladorAlojamientos {

  @Inject
  private ServicioAlojamiento servicioAlojamiento;

  @RequestMapping(path = "/api/alojamientos", method = RequestMethod.GET)
  @ResponseBody
  public List<Alojamiento> homeAlojamientos (
      @RequestParam(value="desde", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date desde,
      @RequestParam(value="hasta", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date hasta,
      @RequestParam(value="precioDesde", required=false) Integer precioDesde,
      @RequestParam(value="precioHasta", required=false) Integer precioHasta,
      @RequestParam(value="rating", required=false) Integer rating,
      @RequestParam(value="bookeable", required=false) Boolean bookeable,
      @RequestParam(value="descuento", required=false) Integer descuento,
      @RequestParam(value="offset", required = false, defaultValue = "0") Integer offset,
      @RequestParam(value="size", required= false, defaultValue = "10") Integer size,
      HttpServletRequest request
      ) {

    // String queries = request.getQueryString();

    List<Alojamiento> alojamientos = servicioAlojamiento.obtenerAlojamientosParametrizados(desde, hasta, precioDesde, precioHasta, rating, bookeable, descuento, offset, size);

    return alojamientos;
  }

  @RequestMapping(path = {"/alojamientos/{id}"}, method = RequestMethod.GET)
  public ModelAndView alojamientoView (@PathVariable("id") Integer id, HttpServletRequest request) {
    ModelMap modelos = new ModelMap();

    return new ModelAndView("alojamientos/detail");
  }


  public void setServicioAlojamiento(ServicioAlojamiento servicioAlojamiento) {
    this.servicioAlojamiento = servicioAlojamiento;
  }
}
