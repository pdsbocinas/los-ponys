package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.servicios.*;
import com.google.maps.errors.ApiException;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class apiViajeControllerTest {
  private ControladorApiViaje controladorApiViaje = new ControladorApiViaje();


  @Test
  public void obtenerListaDeComentariosPorViajeIdVacia () throws InterruptedException, ApiException,
    IOException {
    ControladorApiViaje sut = new ControladorApiViaje();

    ComentarioDto comentarioDto = new ComentarioDto();
    Viaje viaje = new Viaje();

    HttpServletRequest request = null;
    ServicioViaje servicioViaje = mock(ServicioViaje.class); //reemplaza el servicio real por uno falso
    sut.setServicioViaje(servicioViaje);

    ServicioComentario servicioComentario = mock(ServicioComentario.class); //reemplaza el servicio real por uno falso
    sut.setServicioComentario(servicioComentario);

    when(servicioComentario.obtenerComentariosPorViajeId(viaje.getId())).thenReturn(null);

    //ejecucion
    List<Comentario> comentarios = sut.obtenerComentariosPorViajeId(viaje.getId(), request);

    //verificacion
    assertThat(comentarios).isNull();
  }

 /* @Test
  public void enviarComentarioExitoso () {
    ControladorViaje sut = new ControladorViaje();

    ComentarioDto comentarioDto = new ComentarioDto();
    Viaje viaje = new Viaje();
    ServicioViaje servicioViaje = mock(ServicioViaje.class); //reemplaza el servicio real por uno falso
    sut.setServicioViaje(servicioViaje);

    ServicioRegistroUsuario servicioRegistroUsuario = mock(ServicioRegistroUsuario.class);
    sut.setServicioRegistroUsuario(servicioRegistroUsuario);

    ServicioComentario servicioComentario = mock(ServicioComentario.class); //reemplaza el servicio real por uno falso
    sut.setServicioComentario(servicioComentario);

    when(servicioViaje.obtenerViajePorId(viaje.getId())).thenReturn(null);
    when(servicioRegistroUsuario.obtenerUsuarioPorMail(comentarioDto.getUsuario_email())).thenReturn(null);

    //ejecucion
    ModelAndView mav = sut.enviarComentario(comentarioDto);

    //verificacion
    assertThat(mav.getViewName()).isEqualTo("viajes/comentar");
  }
  */
}




