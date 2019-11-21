package ar.edu.unlam.tallerweb1.servicios;
import ar.edu.unlam.tallerweb1.dao.DestinoDao;
import ar.edu.unlam.tallerweb1.dao.FotoDao;
import ar.edu.unlam.tallerweb1.modelo.Destino;
import ar.edu.unlam.tallerweb1.modelo.FileFormBean;
import ar.edu.unlam.tallerweb1.modelo.Foto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service("servicioFoto")
@Transactional
public class ServicioFotoImpl implements ServicioFoto {

  @Inject
  DestinoDao destinoDao;
  @Inject
  FotoDao fotoDao;
  @Override
  public String subirFotoAUnDestino(FileFormBean fileFormBean, Integer destino_id) throws IOException {

    CommonsMultipartFile uploaded = fileFormBean.getFichero();
    String lugar = System.getProperty("user.home");
    String path = lugar + "/documents\\UNLAM\\taller web\\proyecto app\\los-ponys\\src\\main\\webapp\\images\\destinos\\" + uploaded.getOriginalFilename();
    File localFile = new File(path);

    FileOutputStream os = null;

    try {
      os = new FileOutputStream(localFile);
      os.write(uploaded.getBytes());
    } finally {
      if (os != null) {
        try {
          os.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return uploaded.getOriginalFilename();
  }


  @Override
  public String subirFotoDePortadaAViaje(FileFormBean fileFormBean, Long viajeId) throws IOException {

    CommonsMultipartFile uploaded = fileFormBean.getFichero();
    String lugar = System.getProperty("user.home");
    String path = lugar + "/documents\\UNLAM\\taller web\\proyecto app\\los-ponys\\src\\main\\webapp\\images\\viajes\\portada\\" + uploaded.getOriginalFilename();
    File localFile = new File(path);

    FileOutputStream os = null;

    try {
      os = new FileOutputStream(localFile);
      os.write(uploaded.getBytes());
    } finally {
      if (os != null) {
        try {
          os.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return uploaded.getOriginalFilename();
  }

  @Override
  public List<Foto> obtenerFotosPorDestinoId(Integer destino_id) {
    List<Foto> fotos = fotoDao.obtenerFotosPorDestinoId(destino_id);
    return fotos;
  }

  @Override
  public List<Foto> obtenerFotosDeDestinosDelViaje(Long viajeId) {
    List<Foto> fotos = fotoDao.obtenerFotosDeDestinosDelViaje(viajeId);
    return fotos;
  }

  @Override
  public Boolean elegirFotoComoPortada(Foto foto) {
    return fotoDao.elegirFotoComoPortada(foto);
  }

  @Override
  public Foto obtenerFotoDePortada(Long viajeId) {
    Foto foto = fotoDao.obtenerFotoDePortada(viajeId);
    return foto;
  }

  @Override
  public Foto obtenerFoto(Foto foto) {
    return fotoDao.obtenerFoto(foto);

  }

}