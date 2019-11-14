package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.servicios.ServicioDestino;
import ar.edu.unlam.tallerweb1.servicios.ServicioFoto;
import ar.edu.unlam.tallerweb1.servicios.ServicioLogin;
import ar.edu.unlam.tallerweb1.servicios.ServicioViaje;
import com.google.maps.errors.ApiException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@Controller
public class ControladorFotos {

  @Inject
  private ServicioViaje servicioViaje;

  @Inject
  private ServicioDestino servicioDestino;

  @Inject
  private ServicioFoto servicioFoto;


  @RequestMapping("viajes/{viaje_id}/destino/{destino_id}/subirFoto")
  public ModelAndView formulario() {
    return new ModelAndView("uploadFile");
  }

  @RequestMapping(path = "viajes/{viaje_id}/destino/{destino_id}/validar-formulario", method = RequestMethod.POST)
  public ModelAndView guardaFichero(@ModelAttribute FileFormBean fileFormBean,
                                    @PathVariable("destino_id") Integer destino_id) {
    ModelMap modelo = new ModelMap();
    String mensaje = "ok";
    try {
//      grabarFicheroALocal(fileFormBean);
      String nombreFoto = servicioFoto.subirFotoAUnDestino(fileFormBean,destino_id);
      Destino destino = servicioDestino.obtenerDestinoPorId(destino_id);

      Foto foto = new Foto();
      foto.setDestino(destino);
      foto.setName(nombreFoto);
      servicioDestino.guardarFoto(foto);

    } catch (Exception e) {
      StringWriter errors = new StringWriter();
      e.printStackTrace(new PrintWriter(errors));
      mensaje = errors.toString();
      //e.printStackTrace();
      // mensaje = "error";

    }
    modelo.put("mensaje", mensaje);
    return new ModelAndView("uploadFile", modelo);
  }

}

