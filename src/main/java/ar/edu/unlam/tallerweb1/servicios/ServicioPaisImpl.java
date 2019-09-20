package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.dao.PaisDao;
import ar.edu.unlam.tallerweb1.modelo.Pais;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service("ServicioPais")
public class ServicioPaisImpl implements ServicioPais {

  @Inject
  private PaisDao paisDao;

  @Override
  public ArrayList<Pais> obtenerPaises() {
    ArrayList<Pais> paises = paisDao.obtenerPaises();
    return paises;
  }

  @Override
  public void guardarPaises(ArrayList<Pais> paises) {
    this.obtenerPaises();
  }
}
