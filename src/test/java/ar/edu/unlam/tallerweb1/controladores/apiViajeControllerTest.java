package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.servicios.*;
import com.google.maps.errors.ApiException;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class apiViajeControllerTest {
  private ControladorApiViaje controladorApiViaje = new ControladorApiViaje();


  @Test
  public void creacionDeViajeExitosa () throws InterruptedException, ApiException, IOException {
    ControladorApiViaje sut = new ControladorApiViaje();
    ViajeDto viajeDto = new ViajeDto();
    Viaje viaje = new Viaje();
    List<Foto> fotos;

    ServicioViaje servicioViaje = mock(ServicioViaje.class); //reemplaza el servicio real por uno falso
    sut.setServicioViaje(servicioViaje);

    ServicioRegistroUsuario servicioRegistroUsuario = mock(ServicioRegistroUsuario.class);
    sut.setServicioRegistroUsuario(servicioRegistroUsuario);

    ServicioEmail servicioEmail = mock(ServicioEmail.class);
    sut.setServicioEmail(servicioEmail);

    when(servicioViaje.crearViaje(viajeDto.getTitulo(),viajeDto.getFechaInicio(),viajeDto.getFechaFin(),
      viajeDto.getPrivacidad(),viajeDto.getDestinos(),viajeDto.getUsuarios())).thenReturn(null);

    when(servicioRegistroUsuario.obtenerUsuarioPorMail(null)).thenReturn(null);

    //ejecucion
    viajeDto = sut.crearViaje(viajeDto);

    //verificacion
    assertThat(viajeDto.getId()).isNull();

  }

  @Test
  public void obtenerListaVaciaDeComentariosPorViajeId () throws InterruptedException, ApiException,
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
  @Test
  public void obtenerListaConComentariosPorViajeId () throws InterruptedException, ApiException,
    IOException {
    ControladorApiViaje sut = new ControladorApiViaje();

    ComentarioDto comentarioDto = new ComentarioDto();
    Viaje viaje = new Viaje();
    Comentario comentario = new Comentario();
    ArrayList<Comentario> listaComentarios = new ArrayList<Comentario>();
    listaComentarios.add(comentario);

    HttpServletRequest request = null;
    ServicioViaje servicioViaje = mock(ServicioViaje.class); //reemplaza el servicio real por uno falso
    sut.setServicioViaje(servicioViaje);

    ServicioComentario servicioComentario = mock(ServicioComentario.class); //reemplaza el servicio real por uno falso
    sut.setServicioComentario(servicioComentario);

    when(servicioComentario.obtenerComentariosPorViajeId(viaje.getId())).thenReturn(listaComentarios);

    //ejecucion
    List<Comentario> comentarios = sut.obtenerComentariosPorViajeId(viaje.getId(), request);

    //verificacion
    assertThat(comentarios).isEqualTo(listaComentarios);
  }
}




