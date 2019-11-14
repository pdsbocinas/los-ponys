package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Comentario;
import ar.edu.unlam.tallerweb1.modelo.ComentarioDto;
import ar.edu.unlam.tallerweb1.modelo.Viaje;
import ar.edu.unlam.tallerweb1.servicios.ServicioViaje;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class viajeControllerTest {

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


}
