package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Foto;
import ar.edu.unlam.tallerweb1.dao.DestinoDao;
import ar.edu.unlam.tallerweb1.dao.FotoDao;
import ar.edu.unlam.tallerweb1.modelo.Destino;
import ar.edu.unlam.tallerweb1.modelo.DestinoDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service("servicioDestino")
@Transactional
public class ServicioDestinoImpl implements ServicioDestino {

  @Inject
  private DestinoDao destinoDao;

  @Inject
  private FotoDao fotoDao;

  @Override
  public void guardarDestinos(List<Destino> destinos) {
    destinoDao.guardarDestinos(destinos);
  }

    @Override
    public Destino obtenerDestinoPorId(Integer id) {
        return destinoDao.obtenerDestinoPorId(id);
    }

    @Override
  public void guardarFecha(DestinoDto destinoDto, Integer destino_id) {
    Destino destino = new Destino();
    destino = destinoDao.obtenerDestinoPorId(destinoDto.getId());
    destino.setFechaInicio(destinoDto.getFechaInicio());
    destino.setFechaFin(destinoDto.getFechaHasta());
    destinoDao.guardarDestino(destino);
  }

  @Override
  public void guardarFoto(Foto foto) {
    //destinoDao.guardarFoto(nombreFoto, destino_id);
    fotoDao.guardarFoto(foto);
  }
}
