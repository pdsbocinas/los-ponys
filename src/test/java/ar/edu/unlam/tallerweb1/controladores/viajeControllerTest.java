package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.servicios.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.maps.errors.ApiException;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class viajeControllerTest {
  private ControladorViaje controladorViaje = new ControladorViaje();

  @Test
  public void MostrarViajesAUsuarioNoLogueado ()  {
    ControladorViaje sut = new ControladorViaje();

    ViajeDto viajeDto = new ViajeDto();
    Viaje viaje = new Viaje();
    Usuario usuario = new Usuario();

    ServicioViaje servicioViaje = mock(ServicioViaje.class);
    sut.setServicioViaje(servicioViaje);

    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    sut.setHttpServletRequest(httpServletRequest);

    HttpSession httpSession = mock(HttpSession.class);
    sut.setHttpSession(httpSession);

    when(httpServletRequest.getSession()).thenReturn(httpSession);
    when(httpSession.getAttribute("USER")).thenReturn(null);


    //ejecucion
    ModelAndView mav = null;
    try {
      mav = sut.homeViaje(httpServletRequest);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    //verificacion
    assertThat(mav.getViewName()).isEqualTo("redirect:/home");
    assertThat(mav.getModel()).containsKey("user");
    assertThat(mav.getModel().get("user")).isEqualTo(null);

  }

  @Test
  public void MostrarViajesAUsuarioLogueado () throws JsonProcessingException {
    ControladorViaje sut = new ControladorViaje();

    ViajeDto viajeDto = new ViajeDto();
    Viaje viaje = new Viaje();
    Usuario usuario = new Usuario();

    ServicioViaje servicioViaje = mock(ServicioViaje.class);
    sut.setServicioViaje(servicioViaje);

    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    sut.setHttpServletRequest(httpServletRequest);

    HttpSession httpSession = mock(HttpSession.class);
    sut.setHttpSession(httpSession);

    when(httpServletRequest.getSession()).thenReturn(httpSession);
    when(httpSession.getAttribute("USER")).thenReturn(usuario);


    //ejecucion
    ModelAndView mav = sut.homeViaje(httpServletRequest);

    //verificacion
    assertThat(mav.getViewName()).isEqualTo("viajes/travel");
    assertThat(mav.getModel()).containsKey("user_id");
  }

  @Test
  public void MostrarViajesAUsuarioLogueadoSinViajesCreados () throws JsonProcessingException {
    ControladorViaje sut = new ControladorViaje();

    ViajeDto viajeDto = new ViajeDto();
    Viaje viaje = new Viaje();
    Usuario usuario = new Usuario();

    ServicioViaje servicioViaje = mock(ServicioViaje.class);
    sut.setServicioViaje(servicioViaje);

    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    sut.setHttpServletRequest(httpServletRequest);

    HttpSession httpSession = mock(HttpSession.class);
    sut.setHttpSession(httpSession);

    when(httpServletRequest.getSession()).thenReturn(httpSession);
    when(httpSession.getAttribute("USER")).thenReturn(usuario);
    when(servicioViaje.obtenerViajesPorUsuario(usuario.getId())).thenReturn(null);


    //ejecucion
    ModelAndView mav = sut.homeViaje(httpServletRequest);

    //verificacion
    assertThat(mav.getViewName()).isEqualTo("viajes/travel");
    assertThat(mav.getModel()).containsKey("user_id");
    assertThat(mav.getModel()).containsKey("viajes");
    assertThat(mav.getModel().get("viajes")).isEqualTo(null);
  }

  @Test
  public void MostrarViajesAUsuarioLogueadoConViajesCreados () throws JsonProcessingException {
    ControladorViaje sut = new ControladorViaje();

    ViajeDto viajeDto = new ViajeDto();
    Viaje viaje = new Viaje();
    ArrayList<Viaje> viajes = new ArrayList<Viaje>();
    viajes.add(viaje);
    Usuario usuario = new Usuario();

    ServicioViaje servicioViaje = mock(ServicioViaje.class);
    sut.setServicioViaje(servicioViaje);

    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    sut.setHttpServletRequest(httpServletRequest);

    HttpSession httpSession = mock(HttpSession.class);
    sut.setHttpSession(httpSession);

    when(httpServletRequest.getSession()).thenReturn(httpSession);
    when(httpSession.getAttribute("USER")).thenReturn(usuario);
    when(servicioViaje.obtenerViajesPorUsuario(usuario.getId())).thenReturn(viajes);


    //ejecucion
    ModelAndView mav = sut.homeViaje(httpServletRequest);

    //verificacion
    assertThat(mav.getViewName()).isEqualTo("viajes/travel");
    assertThat(mav.getModel()).containsKey("user_id");
    assertThat(mav.getModel()).containsKey("viajes");
    assertThat(mav.getModel().get("viajes")).isEqualTo(viajes);
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
  public void vistaDeUnDestinoParaUnUsuarioQuePerteneceAlViaje () throws JsonProcessingException {
    ControladorViaje sut = new ControladorViaje();

    Viaje viaje = new Viaje();
    Destino destino = new Destino();
    Usuario usuario = new Usuario();
    List<Foto> fotos;
    ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

    usuarios.add(usuario);
    viaje.setUsuarios(usuarios);
    destino.setFechaInicio(new Date());
    destino.setFechaHasta(new Date());

    ServicioViaje servicioViaje = mock(ServicioViaje.class); //reemplaza el servicio real por uno falso
    sut.setServicioViaje(servicioViaje);

    ServicioDestino servicioDestino = mock(ServicioDestino.class); //reemplaza el servicio real por uno falso
    sut.setServicioDestino(servicioDestino);

    ServicioFoto servicioFoto = mock(ServicioFoto.class); //reemplaza el servicio real por uno falso
    sut.setServicioFoto(servicioFoto);

    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    sut.setHttpServletRequest(httpServletRequest);

    HttpSession httpSession = mock(HttpSession.class);
    sut.setHttpSession(httpSession);

    when(httpServletRequest.getSession()).thenReturn(httpSession);
    when(httpSession.getAttribute("USER")).thenReturn(usuario);


    when(servicioViaje.obtenerViajePorId(viaje.getId())).thenReturn(viaje);
    when(servicioDestino.obtenerDestinoPorId(destino.getId())).thenReturn(destino);
    when(servicioFoto.obtenerFotosPorDestinoId(destino.getId())).thenReturn(null);



    //ejecucion
    ModelAndView mav = sut.vistaDeUnDestino(destino.getId(),viaje.getId(),httpServletRequest);

    //verificacion
    assertThat(mav.getViewName()).isEqualTo("destino/vista");
    assertThat(mav.getModel()).containsKey("permisoUsuario");
    assertThat(mav.getModel().get("permisoUsuario")).isEqualTo(true);
  }

  @Test
  public void vistaDeUnDestinoParaUnUsuarioQueNoPerteneceAlViaje () throws JsonProcessingException {
    ControladorViaje sut = new ControladorViaje();

    Viaje viaje = new Viaje();
    Destino destino = new Destino();
    Usuario usuario = new Usuario();
    List<Foto> fotos;
    ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

    viaje.setUsuarios(usuarios);
    destino.setFechaInicio(new Date());
    destino.setFechaHasta(new Date());

    ServicioViaje servicioViaje = mock(ServicioViaje.class); //reemplaza el servicio real por uno falso
    sut.setServicioViaje(servicioViaje);

    ServicioDestino servicioDestino = mock(ServicioDestino.class); //reemplaza el servicio real por uno falso
    sut.setServicioDestino(servicioDestino);

    ServicioFoto servicioFoto = mock(ServicioFoto.class); //reemplaza el servicio real por uno falso
    sut.setServicioFoto(servicioFoto);

    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    sut.setHttpServletRequest(httpServletRequest);

    HttpSession httpSession = mock(HttpSession.class);
    sut.setHttpSession(httpSession);

    when(httpServletRequest.getSession()).thenReturn(httpSession);
    when(httpSession.getAttribute("USER")).thenReturn(usuario);


    when(servicioViaje.obtenerViajePorId(viaje.getId())).thenReturn(viaje);
    when(servicioDestino.obtenerDestinoPorId(destino.getId())).thenReturn(destino);
    when(servicioFoto.obtenerFotosPorDestinoId(destino.getId())).thenReturn(null);

    //ejecucion
    ModelAndView mav = sut.vistaDeUnDestino(destino.getId(),viaje.getId(),httpServletRequest);

    //verificacion
    assertThat(mav.getViewName()).isEqualTo("destino/vista");
    assertThat(mav.getModel()).doesNotContainKey("permisoUsuario");

  }

  @Test
  public void vistaDeUnViaje () throws JsonProcessingException {
    ControladorViaje sut = new ControladorViaje();

    Viaje viaje = new Viaje();
    Destino destino = new Destino();
    Usuario usuario = new Usuario();

    String errorFotoPortada = null;

    ServicioViaje servicioViaje = mock(ServicioViaje.class); //reemplaza el servicio real por uno falso
    sut.setServicioViaje(servicioViaje);

    ServicioDestino servicioDestino = mock(ServicioDestino.class); //reemplaza el servicio real por uno falso
    sut.setServicioDestino(servicioDestino);

    ServicioFoto servicioFoto = mock(ServicioFoto.class); //reemplaza el servicio real por uno falso
    sut.setServicioFoto(servicioFoto);

    when(servicioViaje.obtenerViajePorId(viaje.getId())).thenReturn(viaje);
    when(servicioViaje.obtenerDestinosPorViaje(viaje.getId())).thenReturn(null);
    when(servicioFoto.obtenerFotoDePortada(viaje.getId())).thenReturn(null);

    //ejecucion
    ModelAndView mav = sut.vistaDelViaje(viaje.getId(),errorFotoPortada);

    //verificacion
    assertThat(mav.getViewName()).isEqualTo("viajes/mis-destinos");
  }

  @Test
  public void vistaDeUnViajeConFotoDePortadaSeleccionada () throws JsonProcessingException {
    ControladorViaje sut = new ControladorViaje();

    Viaje viaje = new Viaje();
    Destino destino = new Destino();
    Usuario usuario = new Usuario();
    Foto foto = new Foto();
    foto.setName("nombreFoto");
    String errorFotoPortada = null;

    ServicioViaje servicioViaje = mock(ServicioViaje.class); //reemplaza el servicio real por uno falso
    sut.setServicioViaje(servicioViaje);

    ServicioDestino servicioDestino = mock(ServicioDestino.class); //reemplaza el servicio real por uno falso
    sut.setServicioDestino(servicioDestino);

    ServicioFoto servicioFoto = mock(ServicioFoto.class); //reemplaza el servicio real por uno falso
    sut.setServicioFoto(servicioFoto);

    when(servicioViaje.obtenerViajePorId(viaje.getId())).thenReturn(viaje);
    when(servicioViaje.obtenerDestinosPorViaje(viaje.getId())).thenReturn(null);
    when(servicioFoto.obtenerFotoDePortada(viaje.getId())).thenReturn(foto);

    //ejecucion
    ModelAndView mav = sut.vistaDelViaje(viaje.getId(),errorFotoPortada);

    //verificacion
    assertThat(mav.getViewName()).isEqualTo("viajes/mis-destinos");
    assertThat(mav.getModel()).containsKey("fotoPortada");
    assertThat(mav.getModel().get("fotoPortada")).isNotNull();
  }

  @Test
  public void vistaDeUnViajeSinFotoDePortadaSeleccionada () throws JsonProcessingException {
    ControladorViaje sut = new ControladorViaje();

    Viaje viaje = new Viaje();
    Destino destino = new Destino();
    Usuario usuario = new Usuario();

    String errorFotoPortada = null;

    ServicioViaje servicioViaje = mock(ServicioViaje.class); //reemplaza el servicio real por uno falso
    sut.setServicioViaje(servicioViaje);

    ServicioDestino servicioDestino = mock(ServicioDestino.class); //reemplaza el servicio real por uno falso
    sut.setServicioDestino(servicioDestino);

    ServicioFoto servicioFoto = mock(ServicioFoto.class); //reemplaza el servicio real por uno falso
    sut.setServicioFoto(servicioFoto);

    when(servicioViaje.obtenerViajePorId(viaje.getId())).thenReturn(viaje);
    when(servicioViaje.obtenerDestinosPorViaje(viaje.getId())).thenReturn(null);
    when(servicioFoto.obtenerFotoDePortada(viaje.getId())).thenReturn(null);

    //ejecucion
    ModelAndView mav = sut.vistaDelViaje(viaje.getId(),errorFotoPortada);

    //verificacion
    assertThat(mav.getViewName()).isEqualTo("viajes/mis-destinos");
    assertThat(mav.getModel()).doesNotContainKey("fotoPortada");
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


/*  @Test
  public void validaUnaFechaCorrecta() throws ParseException {
    ControladorViaje sut = new ControladorViaje();
    Viaje viaje = new Viaje();
    Destino destino = new Destino();
    ArrayList<Destino> destinos = new ArrayList<>();
    destinos.add(destino);
    Date inicio = new Date();
    Date fin = new Date();

    String fechaInicioString = "2019-10-10";
    String fechaFinString = "2019-11-10";
    destino.setFechaInicio(inicio);
    destino.setFechaHasta(fin);

    ServicioViaje servicioViaje = mock(ServicioViaje.class);
    sut.setServicioViaje(servicioViaje);

    ServicioDestino servicioDestino = mock(ServicioDestino.class);
    sut.setServicioDestino(servicioDestino);

    HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);
    sut.setHttpServletRequest(httpServletRequest);

    when(servicioViaje.obtenerViajePorId(viaje.getId())).thenReturn(viaje);
    when(servicioDestino.obtenerDestinoPorId(destino.getId())).thenReturn(destino);
    when(servicioViaje.obtenerDestinosPorViaje(viaje.getId())).thenReturn(destinos);
    when(httpServletRequest.getParameter("fechaInicio")).thenReturn(fechaInicioString);
    when(httpServletRequest.getParameter("fechaHasta")).thenReturn(fechaFinString);
    when(servicioViaje.validaFecha(destino,destinos,viaje,inicio,fin)).thenReturn("Ok");

    //ejecucion
    ModelAndView mav = sut.guardarFechasDeDestinoPorViaje(httpServletRequest,destino.getId(),viaje.getId());

    //verificacion
    assertThat(mav.getViewName()).isEqualTo("/destino/vista");
    assertThat(mav.getModel()).containsKey("error");
  }*/

}




