package com.itmo.wst.endpoint;

import com.itmo.wst.repository.WineRepositoryImpl;
import com.itmo.wst.fault.exception.IncorrectFormatException;
import com.itmo.wst.fault.exception.ServiceFaultException;
import com.itmo.wst.fault.exception.WineNotFoundException;
import com.itmo.wst.model.*;
import com.itmo.wst.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.transaction.Transactional;


@RestController
public class WineEndpoint {
	@Autowired
	private WineRepositoryImpl wineRepository;

	@RequestMapping(value = "rest/wines/get", method = RequestMethod.POST)
	public GetWineResponse getWine(@RequestBody GetWineRequest request) throws IncorrectFormatException, InterruptedException {
		GetWineResponse response = new GetWineResponse();
		Thread.sleep(1000);
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


	@Transactional
	@RequestMapping(value = "rest/wines", method = RequestMethod.POST)
	public CreateWineResponse createWine(@RequestBody CreateWineRequest request) throws IncorrectFormatException {
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

	@Transactional
	@RequestMapping(value = "rest/wines", method = RequestMethod.PUT)
	public UpdateWineResponse updateWine(@RequestBody UpdateWineRequest request) throws WineNotFoundException, IncorrectFormatException {
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

	@Transactional
	@RequestMapping(value = "rest/wines", method = RequestMethod.DELETE)
	public DeleteWineResponse deleteWine(@RequestBody DeleteWineRequest request) throws WineNotFoundException, IncorrectFormatException {
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
