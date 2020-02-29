package com.itmo.wst;

import com.itmo.wst.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.transaction.Transactional;
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

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createWineRequest")
	@ResponsePayload
	@Transactional
	public CreateWineResponse createWine(@RequestPayload CreateWineRequest request) {
		CreateWineResponse response = new CreateWineResponse();
		Wine wine = Helper.createNewWine(request);
		wineRepository.entityManager.persist(wine);
		response.setId(wine.getId());

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateWineRequest")
	@ResponsePayload
	@Transactional
	public UpdateWineResponse updateWine(@RequestPayload UpdateWineRequest request) {
		UpdateWineResponse response = new UpdateWineResponse();
		response.setOperationStatus(OperationStatus.FAILED);
		try {
			Wine wine = wineRepository.wineRepository.findById(request.getWine().getId()).orElseThrow();
			Helper.updateWine(wine, request);
			wineRepository.entityManager.persist(wine);
			response.setOperationStatus(OperationStatus.SUCCESS);
		}
		catch (Exception e){
			return response;
		}
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteWineRequest")
	@ResponsePayload
	@Transactional
	public DeleteWineResponse deleteWine(@RequestPayload DeleteWineRequest request) {
		DeleteWineResponse response = new DeleteWineResponse();
		response.setOperationStatus(OperationStatus.FAILED);
		try {
			Wine wine = wineRepository.wineRepository.findById(request.getId()).orElseThrow();
			wineRepository.entityManager.remove(wine);
			response.setOperationStatus(OperationStatus.SUCCESS);
		}
		catch (Exception e){
			return response;
		}
		return response;
	}
}
