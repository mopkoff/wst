package com.itmo.wst;

import java.math.BigInteger;

import com.itmo.wst.model.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class WineClient {
    RestTemplate restTemplate;

    public WineClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GetWineResponse getWine(Wine wine) {
        GetWineRequest request = new GetWineRequest();
        request.setWine(wine);

        return restTemplate.postForObject(
                "http://localhost:8080/rest/wines/get", request, GetWineResponse.class
        );
    }

    public CreateWineResponse createWine(Wine wine) {
        CreateWineRequest request = new CreateWineRequest();
        request.setName(wine.getName());
        request.setColor(wine.getColor());
        request.setRating(wine.getRating());
        request.setSugar(wine.getSugar());

        return restTemplate.postForObject(
                "http://localhost:8080/rest/wines", request, CreateWineResponse.class
        );
    }

    public UpdateWineResponse updateWine(Wine wine) {
        UpdateWineRequest wineRequest = new UpdateWineRequest();
        wineRequest.setWine(wine);

        HttpEntity<UpdateWineRequest> request = new HttpEntity<>(wineRequest);
        ResponseEntity<UpdateWineResponse> response = restTemplate
                .exchange("http://localhost:8080/rest/wines", HttpMethod.PUT, request, UpdateWineResponse.class);

        return response.getBody();
    }

    public DeleteWineResponse deleteWine(BigInteger id) {
        DeleteWineRequest wineRequest = new DeleteWineRequest();
        wineRequest.setId(id);

        HttpEntity<DeleteWineRequest> request = new HttpEntity<>(wineRequest);
        ResponseEntity<DeleteWineResponse> response = restTemplate
                .exchange("http://localhost:8080/rest/wines", HttpMethod.DELETE, request, DeleteWineResponse.class);

        return response.getBody();
    }
}
