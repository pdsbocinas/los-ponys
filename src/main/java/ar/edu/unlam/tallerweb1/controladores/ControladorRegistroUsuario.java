package ar.edu.unlam.tallerweb1.controladores;

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
        // Se agrega al modelo un objeto del tipo Usuario con key 'usuario' para que el mismo sea asociado
        // al model attribute del form que esta definido en la vista 'login'
        Usuario usuario = new Usuario();
        modelo.put("usuario", usuario);
        // Se va a la vista login (el nombre completo de la lista se resuelve utilizando el view resolver definido en el archivo spring-servlet.xml)
        // y se envian los datos a la misma  dentro del modelo
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
            //Creo una session para manejar errores
            HttpSession session = request.getSession();
            session.setAttribute("ERROR", "duplicado");
            return "duplicado";
//            return new ModelAndView("redirect:/home",model);
        }

        usuario.setPassword(usuarioDto.getPassword());

        //Guardo el usuario en la base
        usuarioDto.setId(servicioRegistroUsuario.crearUsuario(
                usuarioDto.getEmail(),
                usuarioDto.getPassword()
        ));
        //creo la session y le paso el usuario, a la vez que elimino la sesion de error
        HttpSession session = request.getSession();
        session.setAttribute("USER", usuario);
        session.removeAttribute("ERROR");

        //le mando el mail a la vista asi saludo lo tomo desde react
        model.put("email",usuarioDto.getEmail());
        return "correcto";
//        return new ModelAndView("redirect:/home",model);
    }

    @RequestMapping("/registroOK")
    public ModelAndView registroOK(RedirectAttributes ra) {
        ra.addFlashAttribute("exito","Usuario Creado");
        return new ModelAndView("redirect:/home");
    }

    @RequestMapping("/registroDuplicado")
    public ModelAndView registroDuplicado(RedirectAttributes ra) {
        ra.addFlashAttribute("error","El mail ya existe!!!!");
        return new ModelAndView("redirect:/home");
    }
}
