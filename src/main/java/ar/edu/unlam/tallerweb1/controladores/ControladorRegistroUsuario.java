package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Rol;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioDto;
import ar.edu.unlam.tallerweb1.servicios.ServicioRegistroUsuario;
import com.google.maps.errors.ApiException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class ControladorRegistroUsuario {

  @Inject
  private ServicioRegistroUsuario servicioRegistroUsuario;

  @RequestMapping("/autenticacion")
  public ModelAndView irALogin() {

    ModelMap modelo = new ModelMap();
    Usuario usuario = new Usuario();
    modelo.put("usuario", usuario);
    return new ModelAndView("home", modelo);
  }

  @RequestMapping(path = {"/guardarUsuario"}, method = RequestMethod.POST)
  @ResponseBody
  public String crearUsuario(@RequestBody UsuarioDto usuarioDto, HttpServletRequest request, RedirectAttributes ra) throws InterruptedException, ApiException, IOException {

    Usuario usuario = new Usuario();
    usuario.setEmail(usuarioDto.getEmail());

    ModelMap model = new ModelMap();
    ra.addFlashAttribute("flashAttr", "flashAttrVal");

    if(servicioRegistroUsuario.consultarUsuarioPorMail(usuario)){
      usuarioDto.setId(0);
      model.put("email","");
      HttpSession session = request.getSession();
      session.setAttribute("ERROR", "duplicado");
      return "El mail ya existe";
    }

    usuario.setPassword(usuarioDto.getPassword());

    usuarioDto.setId(servicioRegistroUsuario.crearUsuario(
        usuarioDto.getEmail(),
        usuarioDto.getPassword()
    ));

    HttpSession session = request.getSession();
    session.setAttribute("USER", usuario);
    session.removeAttribute("ERROR");

    model.put("email",usuarioDto.getEmail());
    return "correcto";
  }

  @RequestMapping("/registroOK")
  public ModelAndView registroOK(RedirectAttributes ra) {
    ra.addFlashAttribute("exito","Usuario Creado");
    return new ModelAndView("redirect:/home");
  }

}
