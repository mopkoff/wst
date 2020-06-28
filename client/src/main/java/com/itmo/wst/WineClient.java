package com.itmo.wst;

import java.math.BigInteger;
import java.nio.charset.Charset;

import com.itmo.wst.model.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class WineClient {
    RestTemplate restTemplate;

    public WineClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
            String authHeader = "Basic " + new String(encodedAuth);
            set("Authorization", authHeader);
        }};
    }

    public GetWineResponse getWine(Wine wine) {
        GetWineRequest wineRequest = new GetWineRequest();
        wineRequest.setWine(wine);

        HttpEntity<GetWineRequest> request = new HttpEntity<GetWineRequest>(
                wineRequest, createHeaders("admin", "{noop}password")
        );
        ResponseEntity<GetWineResponse> response = restTemplate
                .exchange("http://localhost:8080/rest/wines/get", HttpMethod.POST, request, GetWineResponse.class);

        return response.getBody();
    }

    public CreateWineResponse createWine(Wine wine) {
        CreateWineRequest wineRequest = new CreateWineRequest();
        wineRequest.setName(wine.getName());
        wineRequest.setColor(wine.getColor());
        wineRequest.setRating(wine.getRating());
        wineRequest.setSugar(wine.getSugar());

        HttpEntity<CreateWineRequest> request = new HttpEntity<CreateWineRequest>(
                wineRequest, createHeaders("admin", "{noop}password")
        );
        ResponseEntity<CreateWineResponse> response = restTemplate
                .exchange("http://localhost:8080/rest/wines", HttpMethod.POST, request, CreateWineResponse.class);

        return response.getBody();
    }

    public UpdateWineResponse updateWine(Wine wine) {
        UpdateWineRequest wineRequest = new UpdateWineRequest();
        wineRequest.setWine(wine);

        HttpEntity<UpdateWineRequest> request = new HttpEntity<UpdateWineRequest>(
                wineRequest, createHeaders("admin", "{noop}password")
        );
        ResponseEntity<UpdateWineResponse> response = restTemplate
                .exchange("http://localhost:8080/rest/wines", HttpMethod.PUT, request, UpdateWineResponse.class);

        return response.getBody();
    }

    public DeleteWineResponse deleteWine(BigInteger id) {
        DeleteWineRequest wineRequest = new DeleteWineRequest();
        wineRequest.setId(id);

        HttpEntity<DeleteWineRequest> request = new HttpEntity<>(
                wineRequest, createHeaders("admin", "{noop}password")
        );
        ResponseEntity<DeleteWineResponse> response = restTemplate
                .exchange("http://localhost:8080/rest/wines", HttpMethod.DELETE, request, DeleteWineResponse.class);

        return response.getBody();
    }
}
