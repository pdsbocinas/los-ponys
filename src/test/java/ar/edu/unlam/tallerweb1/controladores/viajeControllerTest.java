package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Comentario;
import ar.edu.unlam.tallerweb1.modelo.ComentarioDto;
import ar.edu.unlam.tallerweb1.modelo.Foto;
import ar.edu.unlam.tallerweb1.modelo.Viaje;
import ar.edu.unlam.tallerweb1.servicios.ServicioFoto;
import ar.edu.unlam.tallerweb1.servicios.ServicioViaje;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

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
  public void seleccionarFotoDePortadaDeViaje(){
    ControladorViaje sut = new ControladorViaje();
    Foto foto = new Foto();
    Long viajeId = null;

    ServicioFoto servicioFoto = mock(ServicioFoto.class); //reemplaza el servicio real por uno falso
    sut.setServicioFoto(servicioFoto);

    when(servicioFoto.obtenerFoto(foto)).thenReturn(null);
    when(servicioFoto.elegirFotoComoPortada(foto)).thenReturn(false);

    //ejecucion
    ModelAndView mav = controladorViaje.seleccionarFotoDePortada(foto, viajeId);

    //verificacion
    assertThat(mav.getViewName()).isEqualTo("/viajes/"+viajeId+"/destino");
    assertThat(mav.getModel()).containsKey("error");
    assertThat(mav.getModel().get("error")).isEqualTo("error");
  }


}
