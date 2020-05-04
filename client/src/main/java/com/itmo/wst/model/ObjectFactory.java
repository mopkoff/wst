package com.itmo.wst.model;


public class ObjectFactory {
    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.itmo.wst.wsdl
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetWineRequest }
     * 
     */
    public GetWineRequest createGetWineRequest() {
        return new GetWineRequest();
    }

    /**
     * Create an instance of {@link Wine }
     * 
     */
    public Wine createWine() {
        return new Wine();
    }

    /**
     * Create an instance of {@link GetWineResponse }
     * 
     */
    public GetWineResponse createGetWineResponse() {
        return new GetWineResponse();
    }

    /**
     * Create an instance of {@link CreateWineRequest }
     * 
     */
    public CreateWineRequest createCreateWineRequest() {
        return new CreateWineRequest();
    }

    /**
     * Create an instance of {@link CreateWineResponse }
     * 
     */
    public CreateWineResponse createCreateWineResponse() {
        return new CreateWineResponse();
    }

    /**
     * Create an instance of {@link UpdateWineRequest }
     * 
     */
    public UpdateWineRequest createUpdateWineRequest() {
        return new UpdateWineRequest();
    }

    /**
     * Create an instance of {@link UpdateWineResponse }
     * 
     */
    public UpdateWineResponse createUpdateWineResponse() {
        return new UpdateWineResponse();
    }

    /**
     * Create an instance of {@link DeleteWineRequest }
     * 
     */
    public DeleteWineRequest createDeleteWineRequest() {
        return new DeleteWineRequest();
    }

    /**
     * Create an instance of {@link DeleteWineResponse }
     * 
     */
    public DeleteWineResponse createDeleteWineResponse() {
        return new DeleteWineResponse();
    }

}
