package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Alojamiento;
import ar.edu.unlam.tallerweb1.modelo.Reserva;
import ar.edu.unlam.tallerweb1.modelo.ReservaDto;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioAlojamiento;
import ar.edu.unlam.tallerweb1.servicios.ServicioRegistroUsuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioReserva;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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

  @Test
  public void crearReservaExitosa() throws Exception {

    ControladorAlojamientos controladorAlojamientos = new ControladorAlojamientos();

    ReservaDto reservaDto = mock(ReservaDto.class);
    BindingResult result = null;
    ModelMap model = mock(ModelMap.class);
    HttpServletRequest request = mock(HttpServletRequest.class);

    ServicioRegistroUsuario servicioRegistroUsuario = mock(ServicioRegistroUsuario.class);
    controladorAlojamientos.setServicioRegistroUsuario(servicioRegistroUsuario);

    ServicioAlojamiento servicioAlojamiento = mock(ServicioAlojamiento.class);
    controladorAlojamientos.setServicioAlojamiento(servicioAlojamiento);
    Alojamiento alojamiento = new Alojamiento();
    when(servicioAlojamiento.obtenerAlojamientoPorId(anyInt())).thenReturn(alojamiento);

    ServicioReserva servicioReserva = mock(ServicioReserva.class);
    controladorAlojamientos.setServicioReserva(servicioReserva);

    ModelAndView mav = controladorAlojamientos.submit(reservaDto, result, model, request);

    assertThat(mav.getModel().get("reserva")).isInstanceOf(Reserva.class);

    Reserva reserva = (Reserva) mav.getModel().get("reserva");
    assertThat(reserva.getAlojamiento()).isEqualTo(alojamiento);

    assertThat(mav.getViewName()).isEqualTo("alojamientos/confirm");

  }

  @Test
  public void crearReservaConError() throws Exception {

    ControladorAlojamientos controladorAlojamientos = new ControladorAlojamientos();

    ReservaDto reservaDto = mock(ReservaDto.class);
    BindingResult result = null;
    ModelMap model = null;
    HttpServletRequest request = mock(HttpServletRequest.class);

    ServicioRegistroUsuario servicioRegistroUsuario = mock(ServicioRegistroUsuario.class);
    controladorAlojamientos.setServicioRegistroUsuario(servicioRegistroUsuario);

    ServicioAlojamiento servicioAlojamiento = mock(ServicioAlojamiento.class);
    controladorAlojamientos.setServicioAlojamiento(servicioAlojamiento);

    ServicioReserva servicioReserva = mock(ServicioReserva.class);
    controladorAlojamientos.setServicioReserva(servicioReserva);
    doThrow(Exception.class).when(servicioReserva).crearReservaParaAlojamiento(any(Reserva.class));

    ModelAndView mav = controladorAlojamientos.submit(reservaDto, result, model, request);
    assertThat(mav.getViewName()).isEqualTo("error");

  }

  //@Test
  public void validarCreacionDeReserva () throws Exception {
    Reserva reserva = new Reserva();
    Alojamiento alojamiento = new Alojamiento();
    Usuario usuario = new Usuario();

    alojamiento.setId(1);
    alojamiento.setNombre("El hotel mas caro");
    alojamiento.setPrecio(120000);

    usuario.setId(1);
    usuario.setEmail("pds@gmail.com");
    usuario.setPassword("123456");

    reserva.setId(1);
    reserva.setUsuario(usuario);
    reserva.setAlojamiento(alojamiento);
    String sDate1="31/12/1998";
    Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
    String sDate2="31/12/1999";
    Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate2);
    reserva.setCheckin(date1);
    reserva.setCheckout(date2);

    ControladorAlojamientos sut = new ControladorAlojamientos();
    ServicioReserva servicioReserva = mock(ServicioReserva.class);


    sut.setServicioReserva(servicioReserva);
    servicioReserva.crearReservaParaAlojamiento(reserva);


    assertThat(reserva).isEqualTo(reserva);
  }

}
