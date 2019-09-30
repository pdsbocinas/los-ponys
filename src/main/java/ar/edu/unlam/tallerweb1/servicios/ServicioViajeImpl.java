package ar.edu.unlam.tallerweb1.servicios;


import ar.edu.unlam.tallerweb1.dao.ViajeDao;
import ar.edu.unlam.tallerweb1.modelo.Viaje;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;

@Service("servicioViaje")
public class ServicioViajeImpl implements ServicioViaje{

    @Inject
    private ViajeDao viajeDao;

    @Override
    public String iniciarViaje(Date inicio, Date fin, String destino) {
        return "Inicio";
    }

    @Override
    public void guardarViaje(Viaje viaje) {

        viajeDao.guardarViaje(viaje);
    }


}
