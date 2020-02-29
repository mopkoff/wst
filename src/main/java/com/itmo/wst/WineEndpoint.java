package com.itmo.wst;

import com.itmo.wst.model.GetWineRequest;
import com.itmo.wst.model.GetWineResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.math.BigInteger;


@Endpoint
public class WineEndpoint {
	private static final String NAMESPACE_URI = "itmo.com/wst";

	@Autowired
	private WineRepositoryImpl wineRepository;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWineRequest")
	@ResponsePayload
	public GetWineResponse getWine(@RequestPayload GetWineRequest request) {
		GetWineResponse response = new GetWineResponse();
		response.getWine().addAll(wineRepository.findByWine(request.getWine()));

		return response;
	}
}
