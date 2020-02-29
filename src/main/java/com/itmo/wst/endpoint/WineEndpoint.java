package com.itmo.wst.endpoint;

import com.itmo.wst.repository.WineRepositoryImpl;
import com.itmo.wst.fault.exception.IncorrectFormatException;
import com.itmo.wst.fault.exception.ServiceFaultException;
import com.itmo.wst.fault.exception.WineNotFoundException;
import com.itmo.wst.model.*;
import com.itmo.wst.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.transaction.Transactional;


@Endpoint
public class WineEndpoint {
	private static final String NAMESPACE_URI = "itmo.com/wst";

	@Autowired
	private WineRepositoryImpl wineRepository;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getWineRequest")
	@ResponsePayload
	public GetWineResponse getWine(@RequestPayload GetWineRequest request) throws IncorrectFormatException {
		GetWineResponse response = new GetWineResponse();
		try {
			response.getWine().addAll(wineRepository.findByWine(request.getWine()));
		} catch (NullPointerException npe){
			throw new IncorrectFormatException();
		}
		catch (Exception e){
			throw new ServiceFaultException("Unexpected error during processing.", Status.FAILED);
		}
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createWineRequest")
	@ResponsePayload
	@Transactional
	public CreateWineResponse createWine(@RequestPayload CreateWineRequest request) throws IncorrectFormatException {
		CreateWineResponse response = new CreateWineResponse();
		try {
			Wine wine = Helper.createNewWine(request);
			wineRepository.entityManager.persist(wine);
			response.setId(wine.getId());
		}
		catch (InvalidDataAccessApiUsageException e){
			throw new IncorrectFormatException();
		}
		catch (Exception e){
			throw new ServiceFaultException("Unexpected error during processing.", Status.FAILED);
		}
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateWineRequest")
	@ResponsePayload
	@Transactional
	public UpdateWineResponse updateWine(@RequestPayload UpdateWineRequest request) throws WineNotFoundException, IncorrectFormatException {
		UpdateWineResponse response = new UpdateWineResponse();
		response.setStatus(Status.FAILED);
		try {
			Wine wine = wineRepository.baseRepository.findById(request.getWine().getId()).orElseThrow(NullPointerException::new);
			Helper.updateWine(wine, request);
			wineRepository.entityManager.persist(wine);
			response.setStatus(Status.SUCCESS);
		}
		catch (NullPointerException npe){
			throw new WineNotFoundException();
		}
		catch (InvalidDataAccessApiUsageException e){
			throw new IncorrectFormatException();
		}
		catch (Exception e){
			throw new ServiceFaultException("Unexpected error during processing.", Status.FAILED);
		}
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteWineRequest")
	@ResponsePayload
	@Transactional
	public DeleteWineResponse deleteWine(@RequestPayload DeleteWineRequest request) throws WineNotFoundException, IncorrectFormatException {
		DeleteWineResponse response = new DeleteWineResponse();
		response.setStatus(Status.FAILED);
		try {
			Wine wine = wineRepository.baseRepository.findById(request.getId()).orElseThrow(NullPointerException::new);
			wineRepository.entityManager.remove(wine);
			response.setStatus(Status.SUCCESS);
		}
		catch (NullPointerException npe){
			throw new WineNotFoundException();
		}
		catch (InvalidDataAccessApiUsageException e){
			throw new IncorrectFormatException();
		}
		catch (Exception e){
			throw new ServiceFaultException("Unexpected error during processing.", Status.FAILED);
		}
		return response;
	}
}
