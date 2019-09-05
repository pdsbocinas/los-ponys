package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Mail;

import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioEmail;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorEmail {
 /*   @Inject
    private ServicioTorneo servicioTorneo;

    @Inject
    private ServicioEmail servicioEmail;*/

    /*@RequestMapping(value = "/send-mail", method = RequestMethod.POST)
    public ModelAndView sendMail (@ModelAttribute("torneo") Torneo torneo, HttpServletRequest request) {

       /* ModelMap model = new ModelMap();

        HttpSession session = request.getSession();
        Usuario userName = (Usuario) session.getAttribute("USER");
        String rol = session.getAttribute("ROL").toString();

        Torneo t = servicioTorneo.consultarTorneoPorId(torneo.getId());

        if (rol != "ORGANIZADOR") {
            Mail mail = new Mail();
            mail.setUsuario(userName);
            mail.setPara(userName.getEmail());
            mail.setContenido("Te anotaste para jugar: " + t.getJuego().getDescripcion() + " el día " + t.getFechaDDMMAAAA()
                    + " a las " + t.getHorarioHHss() + "." );
            mail.setAsunto("Te anotaste exitosamente para jugar: " + t.getJuego().getDescripcion());

            t.getUsuarios().add(userName);
            servicioTorneo.update(t);
            servicioEmail.mandarMail(mail);
            servicioEmail.guardarEmail(mail);
            model.put("torneo", t.getId());
            model.put("enviado", "El mail se mando exitosamente");
        } else {
            model.put("error", "El mail no se ha enviado o usted es organizador");
        }

        return new ModelAndView("mail/succesful", model);
    }*/

}
