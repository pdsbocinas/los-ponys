package ar.edu.unlam.tallerweb1.controladores;

import javax.inject.Inject;
import javax.servlet.http.*;

import ar.edu.unlam.tallerweb1.modelo.UsuarioDto;
import com.google.maps.errors.ApiException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioLogin;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class ControladorLogin {

  // La anotacion @Inject indica a Spring que en este atributo se debe setear (inyeccion de dependencias)
  // un objeto de una clase que implemente la interface ServicioLogin, dicha clase debe estar anotada como
  // @Service o @Repository y debe estar en un paquete de los indicados en applicationContext.xml
  @Inject
  private ServicioLogin servicioLogin;

  // Este metodo escucha la URL localhost:8080/NOMBRE_APP/login si la misma es invocada por metodo http GET
  @RequestMapping("/login")
    public ModelAndView irALogin(HttpServletRequest request) {
//    si existe una sesion, debe redireccionarlo a countries
        HttpSession session = request.getSession();
        Usuario user = (Usuario)session.getAttribute("USER");
        if(user != null){
            return new ModelAndView("redirect:/home");
        }
        ModelMap modelo = new ModelMap();
        // Se agrega al modelo un objeto del tipo Usuario con key 'usuario' para que el mismo sea asociado
        // al model attribute del form que esta definido en la vista 'login'
        Usuario usuario = new Usuario();
        modelo.put("usuario", usuario);
        // Se va a la vista login (el nombre completo de la lista se resuelve utilizando el view resolver definido en el archivo spring-servlet.xml)
        // y se envian los datos a la misma  dentro del modelo
        return new ModelAndView("login", modelo);
    }

  // Este metodo escucha la URL validar-login siempre y cuando se invoque con metodo http POST
  // El método recibe un objeto Usuario el que tiene los datos ingresados en el form correspondiente y se corresponde con el modelAttribute definido en el
  // tag form:form
  @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
  public ModelAndView validarLogin(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) {
    ModelMap model = new ModelMap();
    // invoca el metodo consultarUsuario del servicio y hace un redirect a la URL /home, esto es, en lugar de enviar a una vista
    // hace una llamada a otro action a través de la URL correspondiente a ésta
    Usuario usuarioBuscado = servicioLogin.consultarUsuario(usuario);

    if (usuarioBuscado != null) {
      HttpSession session = request.getSession();
      session.setAttribute("ROL", usuarioBuscado.getRol());
      session.setAttribute("USER", usuarioBuscado);
        model.put("user", usuarioBuscado);
      return new ModelAndView("home");
    } else {
      // si el usuario no existe agrega un mensaje de error en el modelo.
      model.put("error", "Usuario o clave incorrecta");
    }
    return new ModelAndView("login", model);
  }


    @RequestMapping(path = {"/validar-login2"}, method = RequestMethod.POST)
    @ResponseBody
    public String validaLogin(@RequestBody UsuarioDto usuarioDto, HttpServletRequest request) throws InterruptedException, ApiException, IOException {
        ModelMap model = new ModelMap();

        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setPassword(usuarioDto.getPassword());

        Usuario usuarioBuscado = servicioLogin.consultarUsuario(usuario);

        if (usuarioBuscado != null) {
            HttpSession session = request.getSession();
            session.setAttribute("ROL", usuarioBuscado.getRol());
            session.setAttribute("USER", usuarioBuscado);
            model.put("user", usuarioBuscado);
//          return new ModelAndView("redirect:/home",model);
            return "correcto";
        } else {
            // si el usuario no existe agrega un mensaje de error en el modelo.
            model.put("error", "Usuario o clave incorrecta");
//            return new ModelAndView("redirect:/home",model);
            return "error";

        }


    }



  // Escucha la URL /home por GET, y redirige a una vista.
  @RequestMapping(path = "/home")
  public ModelAndView irAHome(HttpServletRequest request,
                              ModelMap modelo,
                              @ModelAttribute("error") String error1,
                              @ModelAttribute("exito") String exito,
                              @ModelAttribute("errorLogin") String errorLogin,
                              @ModelAttribute("login") String login,
                              @ModelAttribute("notFound") String notFound) {

      HttpSession session = request.getSession();
      Usuario user = (Usuario)session.getAttribute("USER");
      String error = (String)session.getAttribute("ERROR");

      ModelMap model = new ModelMap();

      if (notFound != "") {
        model.put("notFound",notFound);
      }
      if(error1 != ""){
          model.put("error1",error1);
      }
      if(exito != ""){
          model.put("exito",exito);
      }
      if(errorLogin != ""){
          model.put("errorLogin",errorLogin);
      }
      if(login == "true"){
          model.put("login","true");
      }

      if (user != null) {
        model.put("email",user.getEmail());
          model.put("id",user.getId());
          model.put("login","true");
        return new ModelAndView("home",model);
      }else{
          if(error =="duplicado"){
              model.put("duplicado","duplicado");
              return new ModelAndView("home", model);
          }
          model.put("email",null);
          return new ModelAndView("home");
      }
//      ModelMap model = new ModelMap();
//      model.put("email",user.getEmail());

  }

  // Escucha la url /, y redirige a la URL /login, es lo mismo que si se invoca la url /login directamente.
  @RequestMapping(path = "/")
  public ModelAndView inicio() {
    return new ModelAndView("redirect:/home");
  }

  public void setServicioLogin(ServicioLogin servicioLogin) {
    this.servicioLogin = servicioLogin;
  }

    @RequestMapping("/LoginOK")
    public ModelAndView loginOK(RedirectAttributes ra) {
        ModelMap model = new ModelMap();
        ra.addFlashAttribute("login","true");

        return new ModelAndView("redirect:/home");
    }

    @RequestMapping("/LoginError")
    public ModelAndView loginError(RedirectAttributes ra) {
        ModelMap model = new ModelMap();
        ra.addFlashAttribute("errorLogin","Usuario y/o contraseña invalidos");

        return new ModelAndView("redirect:/home");
    }

    @RequestMapping("/cerrarSesion")
    public ModelAndView cerrarSesion(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        model.put("email","");

        HttpSession session = request.getSession();
        session.removeAttribute("USER");

        return new ModelAndView("home",model);

    }

}

