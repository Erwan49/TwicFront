package fr.eseo.twicfront.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.eseo.twicfront.model.Ville;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

@Service
public class VilleService {

    private static final String API_URL = "http://localhost:8081/api/villes/";

    public List<Ville> getVilles() throws URISyntaxException, IOException, InterruptedException {

        URI url = new URI(API_URL);
        var request = HttpRequest.newBuilder().GET().uri(url).build();
        var client = HttpClient.newBuilder().build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();

        return Arrays.asList(objectMapper.readValue(response.body(), Ville[].class));
    }

    public Ville getVille(String codeCommune) throws URISyntaxException, IOException, InterruptedException {
        URI url = new URI(API_URL + codeCommune);
        var request = HttpRequest.newBuilder().GET().uri(url).build();
        var client = HttpClient.newBuilder().build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), Ville.class);
    }

    public Ville getVilleByName(String nomCommune) throws URISyntaxException, IOException, InterruptedException {
        nomCommune = nomCommune.replace(" ", "%20");
        URI url = new URI(API_URL + "nomCommune/" + nomCommune);
        var request = HttpRequest.newBuilder().GET().uri(url).build();
        var client = HttpClient.newBuilder().build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response.body(), Ville.class);
    }

    public double getDistanceVilles(String longitude1, String latitude1, String longitude2, String latitude2) {

        double degreesToRadians = Math.PI / 180.0;

        double phi1 = (90.0 - Double.parseDouble(latitude1)) * degreesToRadians;
        double phi2 = (90.0 - Double.parseDouble(latitude2)) * degreesToRadians;
        double theta1 = Double.parseDouble(longitude1) * degreesToRadians;
        double theta2 = Double.parseDouble(longitude2) * degreesToRadians;

        return Math.acos((Math.sin(phi1) * Math.sin(phi2) * Math.cos(theta1 - theta2) +
                Math.cos(phi1) * Math.cos(phi2))) * 6371;
    }

}
