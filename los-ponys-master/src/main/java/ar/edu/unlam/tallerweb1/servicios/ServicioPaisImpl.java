package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.dao.PaisesDao;
import ar.edu.unlam.tallerweb1.modelo.Pais;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;

@Service("servicioPais")
public class ServicioPaisImpl implements ServicioPais {

  @Inject
  private PaisesDao paisesDao;

  // esta anotation se va a ejecutar cuando se restartee la aplicacion. Haciendo esto alimentamos (populamos) la base de datos
  // y desp consumimos desde la base. EL primer request va tardar y desp ya no.
  @PostConstruct
  public void init() {

    // aca hacer la peticion y guardar en la base de datos
    // este servicio se deberia disparar al comenzar la aplicacion

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("x-rapidapi-host", "restcountries-v1.p.rapidapi.com");
    headers.set("x-rapidapi-key", "80gNYJiI9Mmsh06oK3FV9320LjXhp1kPE8ujsnjl5SFk72EHGS");

    HttpEntity<Pais> entity = new HttpEntity<>(headers);

    ResponseEntity<List<Pais>> respEntity = restTemplate
        .exchange("https://restcountries-v1.p.rapidapi.com/all",
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<List<Pais>>() {}
        );

    List<Pais> paises = respEntity.getBody();
    paisesDao.guardarPaises(paises);
  }

  @Override
  public List<Pais> obtenerPaises() {
    return paisesDao.obtenerPaises();
  }
}
