package ar.edu.unlam.tallerweb1;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.*;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;

import java.io.IOException;

public class Main {
    public static void main (String args[]) throws InterruptedException, ApiException, IOException {
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

        GeoApiContext context = new GeoApiContext.Builder()
        // Api Pablo : AIzaSyD8feo0IzBJZWjmAEhc2PIPRvBqWhBk2Jg
        // Api Nico : AIzaSyBk95kpZ90NBtlkoHX3MrerMAzHVokLInc
            .apiKey("AIzaSyBk95kpZ90NBtlkoHX3MrerMAzHVokLInc")
            .build();

        // devuelve un json de resultado aleatorios
        // PlacesSearchResponse contextPlaceApi = new TextSearchRequest(context).query("pizzerias").await();

        // LatLng position = new LatLng(-34d,-58d);
        // esta peticion no me esta andando
        // PlacesSearchResponse responseReuqest = new NearbySearchRequest(context).location(position).keyword("pizzerias").await();

        try {
            PlaceDetails resultDetails = new PlaceDetailsRequest(context).placeId("ChIJRVY_etDX3IARGYLVpoq7f68").await();
        } catch (ApiException e) {
            e.printStackTrace();
        }

        DirectionsResult resultDirection = null;

        try {
            resultDirection = new DirectionsApiRequest(context).origin("El Cabildo").destination("Congreso de Buenos Aires").await();
        } catch (ApiException e) {
            e.printStackTrace();
        }

        GeocodingResult[] results = new GeocodingResult[0];

        try {
            results = GeocodingApi.geocode(context,
                "Carabobo 3290 Villa Luzuriaga").await();
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        // System.out.println(gson.toJson(results[0]));
        // System.out.println(gson.toJson(contextPlaceApi.results));
        // System.out.println(gson.toJson(resultDetails.formattedAddress));
        // System.out.println(gson.toJson(resultDirection.routes));
        // System.out.println(gson.toJson(responseReuqest.results));
    }
}
