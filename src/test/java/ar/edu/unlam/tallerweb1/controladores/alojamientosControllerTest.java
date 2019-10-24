package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Alojamiento;
import ar.edu.unlam.tallerweb1.servicios.ServicioAlojamiento;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.registerCustomDateFormat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class alojamientosControllerTest {

  @Test
  public void validarFiltroPorFecha () {

    List<Alojamiento> lista = new ArrayList<>();

    Alojamiento alojamientoConPrecio = new Alojamiento();
    alojamientoConPrecio.setId(1);
    alojamientoConPrecio.setNombre("Hotel Los Angeles");
    alojamientoConPrecio.setPrecio(2500);

    lista.add(alojamientoConPrecio);

    ControladorAlojamientos sut = new ControladorAlojamientos();
    HttpServletRequest req = mock(HttpServletRequest.class);

    String queries = req.getQueryString();

    ServicioAlojamiento servicioAlojamiento = mock(ServicioAlojamiento.class);
    sut.setServicioAlojamiento(servicioAlojamiento);
    when(servicioAlojamiento.obtenerAlojamientosParametrizados(null,null, 1000, 3000, null, null, null
        , 1, 10)).thenReturn(lista);

    List<Alojamiento> mav = sut.homeAlojamientos(null,null, 1000, 3000, null, null, null
    , 1, 10, req);

    assertThat(mav.size()).isEqualTo(1);
  }

}
