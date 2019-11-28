package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Destino;
import ar.edu.unlam.tallerweb1.modelo.DestinoDto;
import ar.edu.unlam.tallerweb1.modelo.Foto;
import ar.edu.unlam.tallerweb1.modelo.Viaje;
import com.google.maps.errors.ApiException;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface ServicioViaje {
  String iniciarViaje(Date inicio, Date fin, String destino);
  List<Viaje> obtenerViajes();
  Long crearViaje(String titulo, Date fechaInicio, Date fechaFin, String privacidad, List<String> destinos, List<String> usuarios) throws InterruptedException, ApiException, IOException;
  Viaje obtenerViajePorId(Long id);
  Long guardarDestinosPorViaje(Long id, List<String> destinosId) throws InterruptedException, ApiException, IOException;
  List<Destino> obtenerDestinosPorViaje(Long id);
  List<Viaje> obtenerViajesPorUsuario(Integer userId);
  void borrarViaje(Viaje viaje);
  void guardarDestinos(Long id, List<DestinoDto> destinos);
  void guardarFoto(Foto foto);

  String validaFecha(Destino destino, List<Destino> destinos,Viaje viaje, Date inicio, Date fin);
}
