package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.dao.UsuarioDao;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioLogin;
import ar.edu.unlam.tallerweb1.servicios.ServicioLoginImpl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*; //si o si para que funcione el mock, importa todos los metodos estaticos de la clase mockito

public class loginControllerTest {

    private ControladorLogin controladorLogin = new ControladorLogin();

    @Test
    public void validarLoginErroneo() {

        ControladorLogin sut = new ControladorLogin();
        Usuario usuario = null;
        HttpServletRequest request = null;

        ServicioLogin servicioLogin = mock(ServicioLogin.class); //reemplaza el servicio real por uno falso
        sut.setServicioLogin(servicioLogin);
        when(servicioLogin.consultarUsuario(usuario)).thenReturn(null);

        //ejecucion
        ModelAndView mav = sut.validarLogin(usuario, request);

        assertThat(mav.getViewName()).isEqualTo("home");
        assertThat(mav.getModel()).containsKey("error");
        assertThat(mav.getModel().get("error")).isEqualTo("Usuario o clave incorrecta");
    }

    @Test
    public void validarLoginExitoso() {
        // prueba de caja negra
        // pruebas de caja blanca para probar metodos con void
        // private ControladorLogin controladorLogin = new ControladorLogin();
    }

    @Test
    public void validarLoginSiUsuarioNoExisteDeberiaReedirigirALogin(){
        //preparacion
        Usuario usuario = new Usuario();
        HttpServletRequest request = null;

        ServicioLogin servicioLogin = mock(ServicioLogin.class);
        controladorLogin.setServicioLogin(servicioLogin);
        when(servicioLogin.consultarUsuario(usuario)).thenReturn(null);
        //controladorLogin = new ControladorLogin(servicioLogin); //inyeccion de dependencia por constructor

        //ejecucion
        ModelAndView mav = controladorLogin.validarLogin(usuario, request);

        //verificacion
        assertThat(mav.getViewName()).isEqualTo("login");
        assertThat(mav.getModel()).containsKey("error");
        assertThat(mav.getModel().get("error")).isEqualTo("Usuario o clave incorrecta");


        //prueba de caja blanca
       // verify(session, times(1)).setAttribute("rol","as"); //el rol del usuario que devuelve el servicio
    }
}
