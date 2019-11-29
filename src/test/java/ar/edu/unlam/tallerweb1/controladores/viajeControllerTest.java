package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.*;
import ar.edu.unlam.tallerweb1.servicios.ServicioDestino;
import ar.edu.unlam.tallerweb1.servicios.ServicioFoto;
import ar.edu.unlam.tallerweb1.servicios.ServicioViaje;
import org.junit.Test;
import org.springframework.http.HttpRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class viajeControllerTest {
  private ControladorViaje controladorViaje = new ControladorViaje();

  @Test
  public void creacionDeViajeExitosa () {
    ControladorViaje controladorViaje = new ControladorViaje();

  }

  @Test
  public void enviarExitosoDeComentario () {
    ControladorViaje sut = new ControladorViaje();

    ComentarioDto comentarioDto = new ComentarioDto();

    ServicioViaje servicioLogin = mock(ServicioViaje.class); //reemplaza el servicio real por uno falso
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




