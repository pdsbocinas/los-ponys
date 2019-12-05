package ar.edu.unlam.tallerweb1.servicios;


import ar.edu.unlam.tallerweb1.dao.DestinoDao;
import ar.edu.unlam.tallerweb1.dao.FotoDao;
import ar.edu.unlam.tallerweb1.dao.UsuarioDao;
import ar.edu.unlam.tallerweb1.dao.ViajeDao;
import ar.edu.unlam.tallerweb1.modelo.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.Photo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("servicioViaje")
@Transactional
public class ServicioViajeImpl implements ServicioViaje {

    @Inject
    private ViajeDao viajeDao;

    @Inject
    private DestinoDao destinoDao;

    @Inject
    private UsuarioDao usuarioDao;

    @Inject
    private FotoDao fotoDao;


    @Override
    public String iniciarViaje(Date inicio, Date fin, String destino) {
        return "Inicio";
    }

    @Override
    public List<Viaje> obtenerViajes() {
        return viajeDao.obtenerViajes();
    }

    @Override
    public Long crearViaje(String titulo, Date fechaInicio, Date fechaFin, String privacidad, List<String> destinosId, List<String> usuariosId) throws InterruptedException, ApiException, IOException {

        List<Destino> destinos = destinoDao.obtenerTodosPorId(destinosId);
        List<Usuario> usuarios = usuarioDao.obtenerTodosPorId(usuariosId);

        Viaje viaje = new Viaje();
        viaje.setTitulo(titulo);
        viaje.setFechaInicio(fechaInicio);
        viaje.setFechaFin(fechaFin);
        viaje.setDestinos(destinos);
        viaje.setUsuarios(usuarios);
        viaje.setPrivacidad(privacidad);
        viajeDao.guardarViaje(viaje);
        return viaje.getId();
    }

    @Override
    public Viaje obtenerViajePorId(Long id) {
        Viaje viaje = viajeDao.obtenerViajePorId(id);
        return viaje;
    }

    @Override
    public Long guardarDestinosPorViaje(Long id, List<String> destinosId) throws InterruptedException, ApiException, IOException {
        List<Destino> result = destinoDao.obtenerTodosPorId(destinosId);
        Viaje viaje = viajeDao.obtenerViajePorId(id);
        viaje.setDestinos(result);
        viajeDao.guardarViaje(viaje);
        return viaje.getId();
    }

    @Override
    public List<Destino> obtenerDestinosPorViaje(Long id) {
        List<Destino> destinos = destinoDao.obtenerDestinosDeViajes(id);
        return destinos;
    }

    @Override
    public List<Viaje> obtenerViajesPorUsuario(Integer userId) {
        List<Viaje> viajes = viajeDao.obtenerViajesPorUsuario(userId);
        return viajes;
    }

    @Override
    public void borrarViaje(Viaje viaje) {
        viajeDao.borrarViaje(viaje);
    }

    @Override
    public void guardarDestinos(Long id, List<DestinoDto> destinos) {
        Viaje viaje = viajeDao.obtenerViajePorId(id);
        List<Destino> listaDestinos = new ArrayList<>();
        List<Destino> destinosActuales = destinoDao.obtenerDestinosDeViajes(id);

        for (Destino destino : destinosActuales) {
            listaDestinos.add(destino);
        }

        for (DestinoDto destinoDto : destinos) {
            Destino destino = new Destino();

            destino.setId(destinoDto.getId());
            if(destinoDto.getName() != null){
                destino.setCiudad(destinoDto.getName());
            }else{
                destino.setCiudad(destinoDto.getCiudad());
            }

            if (destinoDto.photos != null) {
                for (Photo photo : destinoDto.photos) {
                    destino.setPhotoReferences(photo.photoReference);
                }
            }
            destino.setPlaceId(destinoDto.getPlaceId());
            destino.setFechaInicio(destinoDto.getFechaInicio());
            destino.setFechaFin(destinoDto.getFechaHasta());
            listaDestinos.add(destino);
        }
        destinoDao.guardarDestinos(listaDestinos);
        viaje.setDestinos(listaDestinos);
        viajeDao.guardarViaje(viaje);

    }

    @Override
    public void guardarFoto(Foto foto) {
        fotoDao.guardarFoto(foto);
    }

    @Override
    public String validaFecha(Destino destino, List<Destino>destinos, Viaje viaje, Date inicio, Date fin) {
        ValidacionFechaNoVacia v1 = new ValidacionFechaNoVacia();
        ValidacionInicioFinDelDestino v2 = new ValidacionInicioFinDelDestino();
        ValidacionInicioDelViaje v3 = new ValidacionInicioDelViaje(viaje.getFechaInicio());
        ValidacionFinDelViaje v4 = new ValidacionFinDelViaje(viaje.getFechaFin());
        ValidacionDestinoConDestino v5 = new ValidacionDestinoConDestino(destino, destinos);

        v1.proximaValidacion(v2);
        v2.proximaValidacion(v3);
        v3.proximaValidacion(v4);
        v4.proximaValidacion(v5);
        v5.proximaValidacion(null);
        try {
            v1.validar(inicio, fin);
            return "Ok";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

}