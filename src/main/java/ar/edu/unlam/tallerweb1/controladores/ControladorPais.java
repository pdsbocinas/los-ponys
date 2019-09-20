package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Pais;
import ar.edu.unlam.tallerweb1.servicios.ServicioPais;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

@Controller
public class ControladorPais {

  @Inject
  private ServicioPais servicioPais;

  @RequestMapping(path = {"/countries"}, method = RequestMethod.GET)
  public ModelAndView getPaises() {
    ModelMap modelo = new ModelMap();
    List<Pais> paises = servicioPais.obtenerPaises();
    modelo.put("paises", paises);

    return new ModelAndView("countries", modelo);
  }
}
