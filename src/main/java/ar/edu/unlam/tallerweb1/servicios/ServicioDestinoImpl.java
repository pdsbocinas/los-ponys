package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.dao.DestinoDao;
import ar.edu.unlam.tallerweb1.modelo.Destino;

import javax.inject.Inject;
import java.util.List;

public class ServicioDestinoImpl implements ServicioDestino {

  @Inject
  private DestinoDao destinoDao;

  @Override
  public void guardarDestinos(List<Destino> destinos) {
    destinoDao.guardarDestinos(destinos);
  }
}
