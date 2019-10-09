package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Pais;
import ar.edu.unlam.tallerweb1.servicios.ServicioPais;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ControladorPais {

  @Inject
  private ServicioPais servicioPais;

  @RequestMapping(path = {"/countries"}, method = RequestMethod.GET)
  public ModelAndView obtenerPaisesView() {

    ModelMap modelo = new ModelMap();

    // Obtiene los paises de la base de datos
    List<Pais> result = servicioPais.obtenerPaises();

    modelo.put("paises", result);

    return new ModelAndView("countries", modelo);
  }

  @RequestMapping(path = {"/api/countries"}, method = RequestMethod.GET)
  @ResponseBody
  public List<Pais> obtenerPaises(){
    List<Pais> result = servicioPais.obtenerPaises();
    return result;
  }

  @RequestMapping(path = "/countries/{pais}", method = RequestMethod.GET)
  public ModelAndView obtenerPais(@PathVariable("pais") String pais, HttpServletRequest request) {
    ModelMap modelo = new ModelMap();
    modelo.put("country", pais);

    return new ModelAndView("country", modelo);
  }
}
