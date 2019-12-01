package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.servicios.*;
import com.google.maps.errors.ApiException;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class viajeControllerTest {
  private ControladorViaje controladorViaje = new ControladorViaje();


/*

  @Test
  public void MostrarViajesAUsuarioLogueado ()  {
    ControladorViaje sut = new ControladorViaje();


    ViajeDto viajeDto = new ViajeDto();
    Viaje viaje = new Viaje();
    Usuario usuario = new Usuario();

    ServicioViaje servicioViaje = mock(ServicioViaje.class);
    sut.setServicioViaje(servicioViaje);

    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    sut.setHttpServletRequest(httpServletRequest);

    when(httpServletRequest.getSession().getAttribute("USER")).thenReturn(null);

    //ejecucion
    ModelAndView mav = sut.homeViaje(httpServletRequest);

    //verificacion
    assertThat(mav.getViewName()).isEqualTo("redirect:/home");
    assertThat(mav.getModel()).containsKey("user");
    assertThat(mav.getModel().get("user")).isEqualTo(null);

  }
*/

  @Test
  public void creacionDeViajeExitosa () throws InterruptedException, ApiException, IOException {
    ControladorViaje sut = new ControladorViaje();
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

  @Test
  public void cambiarEstadoDeComentarioALeidoExitoso () {
    ControladorViaje sut = new ControladorViaje();
    ArrayList<Comentario> comentarios = new ArrayList();
    Usuario usuario = new Usuario();
    Comentario comentario = new Comentario ();
    comentarios.add(comentario);

    ServicioComentario servicioComentario = mock(ServicioComentario.class); //reemplaza el servicio real por uno falso
    sut.setServicioComentario(servicioComentario);

    when(servicioComentario.obtenerComentariosNoLeidos(usuario.getId())).thenReturn(comentarios);


    //ejecucion
    sut.cambiarEstadoDeComentariosNoLeidos(usuario.getId());

    //verificacion
    assertThat(comentario.getEstado()).isEqualTo("leido");
  }

  @Test
  public void cambiarEstadoDeComentarioALeidoSinTenerComentarios () {
    ControladorViaje sut = new ControladorViaje();
    ArrayList<Comentario> comentarios = new ArrayList();
    Usuario usuario = new Usuario();
    Comentario comentario = new Comentario ();


    ServicioComentario servicioComentario = mock(ServicioComentario.class); //reemplaza el servicio real por uno falso
    sut.setServicioComentario(servicioComentario);

    when(servicioComentario.obtenerComentariosNoLeidos(usuario.getId())).thenReturn(comentarios);


    //ejecucion
    sut.cambiarEstadoDeComentariosNoLeidos(usuario.getId());

    //verificacion
    assertThat(!comentarios.isEmpty());
  }

  @Test
  public void elegirFechaDeDestinoAUnDestinoConFechaCargada () {
    ControladorViaje sut = new ControladorViaje();

    Viaje viaje = new Viaje();
    Destino destino = new Destino();

    ServicioViaje servicioViaje = mock(ServicioViaje.class); //reemplaza el servicio real por uno falso
    sut.setServicioViaje(servicioViaje);

    ServicioDestino servicioDestino = mock(ServicioDestino.class); //reemplaza el servicio real por uno falso
    sut.setServicioDestino(servicioDestino);

    when(servicioDestino.obtenerDestinoPorId(destino.getId())).thenReturn(destino);
    when(servicioViaje.obtenerViajePorId(viaje.getId())).thenReturn(viaje);

    destino.setFechaInicio(new Date());

    //ejecucion
    ModelAndView mav = sut.elegirFechaDeUnDestino(destino.getId(),viaje.getId());

    //verificacion
    assertThat(mav.getViewName()).isEqualTo("destino/fecha");
    assertThat(mav.getModel()).containsKey("fechaInicio");
  }

  @Test
  public void elegirFechaDeDestinoAUnDestinoSinFechaCargada () {
    ControladorViaje sut = new ControladorViaje();

    Viaje viaje = new Viaje();
    Destino destino = new Destino();

    ServicioViaje servicioViaje = mock(ServicioViaje.class); //reemplaza el servicio real por uno falso
    sut.setServicioViaje(servicioViaje);

    ServicioDestino servicioDestino = mock(ServicioDestino.class); //reemplaza el servicio real por uno falso
    sut.setServicioDestino(servicioDestino);

    when(servicioDestino.obtenerDestinoPorId(destino.getId())).thenReturn(destino);
    when(servicioViaje.obtenerViajePorId(viaje.getId())).thenReturn(viaje);


    //ejecucion
    ModelAndView mav = sut.elegirFechaDeUnDestino(destino.getId(),viaje.getId());

    //verificacion
    assertThat(mav.getViewName()).isEqualTo("destino/fecha");
    assertThat(mav.getModel()).doesNotContainKey("fechaIncio");
  }


  @Test
  public void mostrarFotosDeLosDestinosParaElegirPortadaDeViajeSinFotosCargadas(){
    ControladorViaje sut = new ControladorViaje();
    Foto foto = new Foto();
    Viaje viaje = new Viaje();
    List<Foto> fotos;

    ServicioViaje servicioViaje = mock(ServicioViaje.class); //reemplaza el servicio real por uno falso
    sut.setServicioViaje(servicioViaje);

    ServicioFoto servicioFoto = mock(ServicioFoto.class); //reemplaza el servicio real por uno falso
    sut.setServicioFoto(servicioFoto);

    when(servicioViaje.obtenerViajePorId(viaje.getId())).thenReturn(null);
    when(servicioFoto.obtenerFotosDeDestinosDelViaje(viaje.getId())).thenReturn(null);

    //ejecucion
    ModelAndView mav = sut.mostrarTodasLasFotosDeLosDestinosDelViaje(viaje.getId());

    //verificacion
    assertThat(mav.getViewName()).isEqualTo("viajes/elegirFotoDePortada");
    assertThat(mav.getModel()).containsKey("fotos");
    assertThat(mav.getModel().get("fotos")).isEqualTo(null);
  }

  @Test
  public void mostrarFotosDeLosDestinosParaElegirPortadaDeViajeConFotosCargadas(){
    ControladorViaje sut = new ControladorViaje();
    Foto foto = new Foto();
    Viaje viaje = new Viaje();
    List<Foto> fotos = null;

    ServicioViaje servicioViaje = mock(ServicioViaje.class); //reemplaza el servicio real por uno falso
    sut.setServicioViaje(servicioViaje);

    ServicioFoto servicioFoto = mock(ServicioFoto.class); //reemplaza el servicio real por uno falso
    sut.setServicioFoto(servicioFoto);

    when(servicioViaje.obtenerViajePorId(viaje.getId())).thenReturn(null);
    when(servicioFoto.obtenerFotosDeDestinosDelViaje(viaje.getId())).thenReturn(fotos);

    //ejecucion
    ModelAndView mav = sut.mostrarTodasLasFotosDeLosDestinosDelViaje(viaje.getId());

    //verificacion
    assertThat(mav.getViewName()).isEqualTo("viajes/elegirFotoDePortada");
    assertThat(mav.getModel()).containsKey("fotos");
    assertThat(mav.getModel().get("fotos")).isEqualTo(fotos);
  }


  @Test
  public void seleccionarFotoDePortadaDeViajeError(){
    ControladorViaje sut = new ControladorViaje();
    Foto foto = new Foto();
    Long viajeId = null;

    ServicioFoto servicioFoto = mock(ServicioFoto.class); //reemplaza el servicio real por uno falso
    sut.setServicioFoto(servicioFoto);

    when(servicioFoto.obtenerFoto(foto)).thenReturn(null);
    when(servicioFoto.elegirFotoComoPortada(foto)).thenReturn(false);

    //ejecucion
    ModelAndView mav = sut.seleccionarFotoDePortada(foto, viajeId);

    //verificacion
    assertThat(mav.getViewName()).isEqualTo("redirect:/viajes/"+viajeId+"/destino");
    assertThat(mav.getModel()).containsKey("errorFotoPortada");
    assertThat(mav.getModel().get("errorFotoPortada")).isEqualTo("No se pudo seleccionar la foto de portada");
  }

  @Test
  public void seleccionarFotoDePortadaDeViajeOK(){
    ControladorViaje sut = new ControladorViaje();
    Foto foto = new Foto();
    Long viajeId = null;

    ServicioFoto servicioFoto = mock(ServicioFoto.class); //reemplaza el servicio real por uno falso
    sut.setServicioFoto(servicioFoto);

    when(servicioFoto.obtenerFoto(foto)).thenReturn(foto);
    when(servicioFoto.elegirFotoComoPortada(foto)).thenReturn(true);

    //ejecucion
    ModelAndView mav = sut.seleccionarFotoDePortada(foto, viajeId);

    //verificacion
    assertThat(mav.getViewName()).isEqualTo("redirect:/viajes/"+viajeId+"/destino");
    assertThat(mav.getModel()).doesNotContainKey("errorFotoPortada");
  }


 /* @Test
  public void validaUnaFechaCorrecta() throws ParseException {
    ControladorViaje sut = new ControladorViaje();
    Viaje viaje = new Viaje();
    Destino destino = new Destino();
    ArrayList<Destino> destinos = new ArrayList<Destino>();
    Date inicio = new Date();
    Date fin = new Date();
    HttpServletRequest req = null;

    ServicioViaje servicioViaje = mock(ServicioViaje.class);
    sut.setServicioViaje(servicioViaje);

    ServicioDestino servicioDestino = mock(ServicioDestino.class);
    sut.setServicioDestino(servicioDestino);

    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    sut.setHttpServletRequest(httpServletRequest);


    when(servicioViaje.obtenerViajePorId(viaje.getId())).thenReturn(null);
    when(servicioDestino.obtenerDestinoPorId(destino.getId())).thenReturn(null);
    when(servicioViaje.obtenerDestinosPorViaje(viaje.getId())).thenReturn(null);
    when(httpServletRequest.getParameter("fechaInicio")).thenReturn(null);
    when(httpServletRequest.getParameter("fechaHasta")).thenReturn(null);
    when(servicioViaje.validaFecha(destino,destinos,viaje,inicio,fin)).thenReturn("Ok");



    //ejecucion
    ModelAndView mav = controladorViaje.guardarFechasDeDestinoPorViaje(req,destino.getId(),viaje.getId());

    //verificacion
    assertThat(mav.getViewName()).isEqualTo("/destino/vista");
    assertThat(mav.getModel()).containsKey("error");
  }
*/
}




