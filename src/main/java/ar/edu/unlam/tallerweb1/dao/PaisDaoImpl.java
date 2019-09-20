package ar.edu.unlam.tallerweb1.dao;

import ar.edu.unlam.tallerweb1.modelo.Pais;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository("PaisDao")
public class PaisDaoImpl implements PaisDao {

  @Inject
  private SessionFactory sessionFactory;

  @Override @Transactional
  public ArrayList<Pais> obtenerPaises() {
    final Session session = sessionFactory.getCurrentSession();
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("x-rapidapi-host", "restcountries-v1.p.rapidapi.com");
    headers.set("x-rapidapi-key", "80gNYJiI9Mmsh06oK3FV9320LjXhp1kPE8ujsnjl5SFk72EHGS");

    HttpEntity<Pais> entity = new HttpEntity<>(headers);

    ResponseEntity<ArrayList<Pais>> respEntity = restTemplate
        .exchange("https://restcountries-v1.p.rapidapi.com/all",
            HttpMethod.GET,
            entity,
            new ParameterizedTypeReference<ArrayList<Pais>>() {}
        );

    ArrayList<Pais> paises = respEntity.getBody();
    this.guardarPaises(paises);

    return (ArrayList<Pais>) session.createCriteria(Pais.class).list();
  }

  @Override @Transactional
  public void guardarPaises(ArrayList<Pais> paises) {
    final Session session = sessionFactory.getCurrentSession();
    session.save(paises);
  }
}
