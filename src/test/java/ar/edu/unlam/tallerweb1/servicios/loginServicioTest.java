package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.SpringTest;
import ar.edu.unlam.tallerweb1.dao.UsuarioDao;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioLoginImpl;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.inject.Inject;

public class loginServicioTest {

    @Test
    public void queUnUsuarioNoSeaNull () {

        Usuario u = new Usuario();
        u.setEmail("sarasa@sarasa.com");
        u.setPassword("12345");

        ServicioLoginImpl sut = new ServicioLoginImpl();
        UsuarioDao usuarioDao = mock(UsuarioDao.class);
        sut.setUsuarioDao(usuarioDao);
        when(usuarioDao.consultarUsuario(u)).thenReturn(new Usuario());

        Usuario b = sut.consultarUsuario(u);

        assertThat(u).isEqualTo(b);
    }
}
