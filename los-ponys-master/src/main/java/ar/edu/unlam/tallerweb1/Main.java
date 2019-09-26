package ar.edu.unlam.tallerweb1;

import ar.edu.unlam.tallerweb1.modelo.Pais;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main (String args[]) {
        /*RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-rapidapi-host", "restcountries-v1.p.rapidapi.com");
        headers.set("x-rapidapi-key", "80gNYJiI9Mmsh06oK3FV9320LjXhp1kPE8ujsnjl5SFk72EHGS");

        HttpEntity<Pais> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Pais>> respEntity = restTemplate.exchange("https://restcountries-v1.p.rapidapi.com/all", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Pais>>(){});

        List<Pais> paises = respEntity.getBody();

        for (final Pais pais : paises) {
            System.out.println(pais.getName());
        }*/
    }
}
