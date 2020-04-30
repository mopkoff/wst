package com.itmo.wst;

import com.itmo.wst.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.math.BigInteger;

public class WineClient extends WebServiceGatewaySupport {

	private static final Logger log = LoggerFactory.getLogger(WineClient.class);

	public String serverAddress;

	private String getWineEndpoint() {
		return serverAddress + "/wines";
	}

	public GetWineResponse getWine(Wine wine) {

		GetWineRequest request = new GetWineRequest();
		request.setWine(wine);

		log.info("Requesting location for " + wine);

		return (GetWineResponse) getWebServiceTemplate()
				.marshalSendAndReceive(getWineEndpoint(), request);
	}

	public CreateWineResponse createWine(Wine wine) {

		CreateWineRequest request = new CreateWineRequest();
		request.setName(wine.getName());
		request.setColor(wine.getColor());
		request.setRating(wine.getRating());
		request.setSugar(wine.getSugar());

		return (CreateWineResponse) getWebServiceTemplate()
				.marshalSendAndReceive(getWineEndpoint(), request);
	}

	public UpdateWineResponse updateWine(Wine wine) {

		UpdateWineRequest request = new UpdateWineRequest();
		request.setWine(wine);

		return (UpdateWineResponse) getWebServiceTemplate()
				.marshalSendAndReceive(getWineEndpoint(), request);
	}

	public DeleteWineResponse deleteWine(BigInteger id) {

		DeleteWineRequest request = new DeleteWineRequest();
		request.setId(id);

		return (DeleteWineResponse) getWebServiceTemplate()
				.marshalSendAndReceive(getWineEndpoint(), request);
	}
}
