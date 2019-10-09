package ar.edu.unlam.tallerweb1.servicios;


import ar.edu.unlam.tallerweb1.dao.DestinoDao;
import ar.edu.unlam.tallerweb1.dao.UsuarioDao;
import ar.edu.unlam.tallerweb1.dao.ViajeDao;
import ar.edu.unlam.tallerweb1.modelo.Destino;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.Viaje;
import com.google.maps.PlaceDetailsRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.PlaceDetails;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service("servicioViaje")
public class ServicioViajeImpl implements ServicioViaje{

    @Inject
    private ViajeDao viajeDao;

    @Inject
    private DestinoDao destinoDao;

    @Inject
    private UsuarioDao usuarioDao;

    @Override
    public String iniciarViaje(Date inicio, Date fin, String destino) {
        return "Inicio";
    }

    @Override
    public List<Viaje> obtenerViajes () {
        return viajeDao.obtenerViajes();
    }

    @Override
    public Long crearViaje(String titulo, Date fechaInicio, Date fechaFin, List<String> destinosId, List<Integer> usuariosId) throws InterruptedException, ApiException, IOException {

        List<Destino> destinos = destinoDao.obtenerTodosPorId(destinosId);
        List<Usuario> usuarios = usuarioDao.obtenerTodosPorId(usuariosId);

        Viaje viaje = new Viaje();
        viaje.setTitulo(titulo);
        viaje.setFechaInicio(fechaInicio);
        viaje.setFechaFin(fechaFin);
        viaje.setDestinos(destinos);
        viaje.setUsuarios(usuarios);
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
        for (Destino destino : result) {
            destino.setViaje(viaje);
        }
        viaje.setDestinos(result);
        return viaje.getId();
    }
}
