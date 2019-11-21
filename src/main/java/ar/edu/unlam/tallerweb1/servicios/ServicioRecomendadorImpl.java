package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.dao.RecomendadorDao;
import ar.edu.unlam.tallerweb1.modelo.PuntoDeInteres;
import com.google.maps.errors.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.IOException;
import java.util.List;

@Service("servicioRecomendador")
@Transactional
public class ServicioRecomendadorImpl implements ServicioRecomendador {

  @Inject
  RecomendadorDao recomendadorDao;

  @Override
  public List<PuntoDeInteres> obtenerPuntosDeInteres(String tipo, String destino) throws InterruptedException, ApiException, IOException {
    return recomendadorDao.obtenerPuntosDeInteres(tipo, destino);
  }
}
