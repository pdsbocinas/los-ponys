package ar.edu.unlam.tallerweb1.controladores;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@PropertySource(value= {"classpath:application.properties"})
public class ControladorRestaurantes {

  @RequestMapping("/restaurantes")
  public ModelAndView homeRestaurantes (HttpServletRequest request) {
    return new ModelAndView("restaurantes/index");
  }
}
