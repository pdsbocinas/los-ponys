package ar.edu.unlam.tallerweb1.controladores;

import ar.edu.unlam.tallerweb1.modelo.Pais;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

@Controller
public class ControladorPais {

    @RequestMapping(path = {"/paises"}, method = RequestMethod.GET)
    public ModelAndView getPaises() {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-rapidapi-host", "restcountries-v1.p.rapidapi.com");
        headers.set("x-rapidapi-key", "80gNYJiI9Mmsh06oK3FV9320LjXhp1kPE8ujsnjl5SFk72EHGS");

        HttpEntity<Pais> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Pais>> respEntity = restTemplate.exchange("https://restcountries-v1.p.rapidapi.com/all", HttpMethod.GET, entity, new ParameterizedTypeReference<List<Pais>>(){});

        List<Pais> paises = respEntity.getBody();
        ModelMap modelo = new ModelMap();
        modelo.put("paises", paises);

        return new ModelAndView("paises", modelo);
    }
}
