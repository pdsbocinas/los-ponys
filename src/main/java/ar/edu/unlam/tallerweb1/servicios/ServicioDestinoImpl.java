package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.dao.DestinoDao;
import ar.edu.unlam.tallerweb1.modelo.Destino;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service("servicioDestino")
@Transactional
public class ServicioDestinoImpl implements ServicioDestino {

  @Inject
  private DestinoDao destinoDao;

  @Override
  public void guardarDestinos(List<Destino> destinos) {
    destinoDao.guardarDestinos(destinos);
  }
}
