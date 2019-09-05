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
import static org.mockito.Mockito.*;

public class loginControllerTest {

    @Test
    public void validarLoginErroneo() {

        ControladorLogin sut = new ControladorLogin();
        Usuario usuario = null;
        HttpServletRequest request = null;

        ServicioLogin servicioLogin = mock(ServicioLogin.class);
        sut.setServicioLogin(servicioLogin);
        when(servicioLogin.consultarUsuario(usuario)).thenReturn(null);

        //ejecucion
        ModelAndView mav = sut.validarLogin(usuario, request);

        assertThat(mav.getViewName()).isEqualTo("login");
        assertThat(mav.getModel()).containsKey("error");
        assertThat(mav.getModel().get("error")).isEqualTo("Usuario o clave incorrecta");
    }
}
