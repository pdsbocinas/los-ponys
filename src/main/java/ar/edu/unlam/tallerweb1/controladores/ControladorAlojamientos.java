package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Alojamiento;
import ar.edu.unlam.tallerweb1.servicios.ServicioAlojamiento;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

  @RequestMapping(path = "/alojamientos", method = RequestMethod.GET)
  public ModelAndView homeAlojamientos (
      @RequestParam(value="desde", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date desde,
      @RequestParam(value="hasta", required=false) @DateTimeFormat(pattern="yyyy-MM-dd") Date hasta,
      @RequestParam(value="precioDesde", required=false) Integer precioDesde,
      @RequestParam(value="precioHasta", required=false) Integer precioHasta,
      @RequestParam(value="rating", required=false) Integer rating,
      @RequestParam(value="bookeable", required=false) Boolean bookeable,
      @RequestParam(value="descuento", required=false) Integer descuento,
      HttpServletRequest request
      ) {

    /*ArrayList <Alojamiento> list = new ArrayList<>();

    allParams.entrySet().stream().forEach(
        pair -> {
          if (pair.getKey() == "date") {
            String[] values = pair.getValue().split(",",2);

            List<Alojamiento> alojamientosPorFecha = servicioAlojamiento.obtenerPorFechas(values[0],values[1]);
            list.addAll(alojamientosPorFecha);
          }
        }

    );*/

    String queries = request.getQueryString();

    ArrayList <Alojamiento> alojamientos = new ArrayList<>();
    ModelMap model = new ModelMap();

    List<Alojamiento> todosLosAlojamientos = servicioAlojamiento.obtenerTodosLosAlojamientos();

    if (desde != null && hasta != null) {
      List<Alojamiento> alojamientosPorFecha = servicioAlojamiento.obtenerPorFechas(desde,hasta);
      alojamientos.addAll(alojamientosPorFecha);
    }

    if (precioDesde != null && precioHasta != null) {
      List<Alojamiento> alojamientosPorPrecio = servicioAlojamiento.obtenerPorPrecio(precioDesde, precioHasta);
      alojamientos.addAll(alojamientosPorPrecio);
    }

    if (rating != null) {
      List<Alojamiento> alojamientosPorRating = servicioAlojamiento.obtenerPorRating(rating);
      alojamientos.addAll(alojamientosPorRating);
    }

    if (bookeable != null) {
      List<Alojamiento> alojamientoConCancelacionGratuita = servicioAlojamiento.obtenerAquellosConReserva(bookeable);
      alojamientos.addAll(alojamientoConCancelacionGratuita);
    }

    if (descuento != null) {
      List<Alojamiento> alojamientosConDescuentos = servicioAlojamiento.obtenerAquellosConDescuento(descuento);
      alojamientos.addAll(alojamientosConDescuentos);
    }

    if (queries == null) {
      alojamientos.addAll(todosLosAlojamientos);
    }

    model.put("alojamientos", alojamientos);

    return new ModelAndView("alojamientos/index", model);
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
