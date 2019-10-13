package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.dao.PaisesDao;
import ar.edu.unlam.tallerweb1.modelo.Border;
import ar.edu.unlam.tallerweb1.modelo.Pais;
import ar.edu.unlam.tallerweb1.modelo.PaisDto;
import ar.edu.unlam.tallerweb1.modelo.Translations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;

@Service("servicioPais")
public class ServicioPaisImpl implements ServicioPais {

  @Inject
  private PaisesDao paisesDao;

  // esta anotation se va a ejecutar cuando se restartee la aplicacion. Haciendo esto alimentamos (populamos) la base de datos
  // y desp consumimos desde la base. EL primer request va tardar y desp ya no.
  // @PostConstruct
  public void init() {

    // aca hacer la peticion y guardar en la base de datos
    // este servicio se deberia disparar al comenzar la aplicacion
    // tendria que haber una clase que sepa convertir y otra que guarde (DAO)

    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("x-rapidapi-host", "restcountries-v1.p.rapidapi.com");
    headers.set("x-rapidapi-key", "80gNYJiI9Mmsh06oK3FV9320LjXhp1kPE8ujsnjl5SFk72EHGS");

    HttpEntity<PaisDto> entity = new HttpEntity<>(headers);

    ResponseEntity<List<PaisDto>> respEntity = restTemplate
        .exchange("https://restcountries-v1.p.rapidapi.com/all",
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<List<PaisDto>>() {}
        );

    /* Clase que mapea el modelos de la API, en este caso no se mapea todo */
    List<PaisDto> paisesApi = respEntity.getBody();

    // se itera y se va seteando nuestro modelo con los datos
    for (PaisDto paisApi : paisesApi) {
      // aca convierto de ese modelo a nuestro modelo ---> https://www.oscarblancarteblog.com/2019/09/05/java-converter-pattern/ Investigar
      Pais pais = new Pais();
      // se setea tipo string
      pais.setName(paisApi.getName());
      pais.setCapital(paisApi.getCapital());

      List<Border> borders = new ArrayList<>();
      List<Translations> listTranslations = new ArrayList<>();
      // para mapear una estructura tipo mapa
      for (Map.Entry <String, String> entry : paisApi.getTranslations().entrySet()) {
        Translations translations = new Translations();
        translations.setCode(entry.getKey());
        translations.setLanguage(entry.getValue());
        listTranslations.add(translations);
      }
      // para mapear una estructura de tipo lista
      for (String borderApi : paisApi.getBorders()) {
        Border border = new Border();
        border.setName(borderApi);
        borders.add(border);
      }

      pais.setTranslations(listTranslations);
      pais.setBorders(borders);
      paisesDao.guardarPaises(pais);
    }
  }

  @Override
  public List<Pais> obtenerPaises() {
    return paisesDao.obtenerPaises();
  }
}
