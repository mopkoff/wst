package com.itmo.wst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;


@Endpoint
public class WineEndpoint {
	private static final String NAMESPACE_URI = "itmo.com/wst";

	private WineRepository wineRepository;

	@Autowired
	public WineEndpoint(WineRepository wineRepository) {
		this.wineRepository = wineRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWineRequest")
	@ResponsePayload
	public GetWineResponse getWine(@RequestPayload GetWineRequest request) {
		GetWineResponse response = new GetWineResponse();
		response.setWine(wineRepository.findWine(request.getName()));

		return response;
	}
}
