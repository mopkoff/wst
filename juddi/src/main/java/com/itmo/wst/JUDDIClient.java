package com.itmo.wst;

import org.apache.juddi.api_v3.AccessPointType;
import org.apache.juddi.v3.client.UDDIConstants;
import org.apache.juddi.v3.client.config.UDDIClerk;
import org.apache.juddi.v3.client.config.UDDIClient;
import org.apache.juddi.v3.client.transport.Transport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.uddi.api_v3.*;
import org.uddi.v3_service.UDDIInquiryPortType;

import java.util.Arrays;
import java.util.List;

@Component
@PropertySource("classpath:custom.properties")
public class JUDDIClient {

    @Value("${wine.server.address}")
    public String serverAddress;

    @Value("${wine.client.address}")
    public String clientAddress;

    final Jaxb2Marshaller marshaller;

    @Value("${wine.business.name}")
    public String businessName;
    @Value("${wine.service.name}")
    public String serviceName;

    private static UDDIClerk clerk = null;
    private static UDDIClient client = null;
    private static UDDIInquiryPortType inquiry = null;


    private String getWineEndpoint() {
        return serverAddress + "/";
    }

    public JUDDIClient(Jaxb2Marshaller marshaller) {
        try {
            // create a client and read the config in the archive;
            // you can use your config file name
            client = new UDDIClient("META-INF/uddi.xml");
            // get the clerk
            clerk = client.getClerk("default");
            Transport transport = client.getTransport(clerk.getUDDINode().getName());
            inquiry = transport.getUDDIInquiryService();
            if (clerk == null) {
                throw new Exception("the clerk wasn't found, check the config file!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.marshaller = marshaller;
    }

    public void registerService() {
        try {
            // Creating the parent business entity that will contain our service.
            String businessKey = getBusiness();
            if (businessKey == null) {
                System.out.println("Creating business");
                businessKey = createBusiness();
            } else {
                System.out.println("Business already exists");
            }

            // Creating a service to save.  Only adding the minimum data: the parent business key retrieved from saving the business
            // above and a single name.
            BusinessService service = getService(businessKey);
            if (service == null) {
                System.out.println("Creating service");
                createService(businessKey);
            } else {
                System.out.println("Service already exists");
            }

            clerk.discardAuthToken();
            // Now you have a business and service via
            // the jUDDI API!
            System.out.println("Success!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public WineClient getWineClient() {
        try {
            String businessKey = getBusiness();
            System.out.println("Found business, key " + businessKey);
            BusinessService service = getService(businessKey);
            assert service != null;
            String serviceLocation = service.getBindingTemplates().getBindingTemplate().stream()
                    .findFirst()
                    .orElseThrow(() -> new Exception("No binding templates"))
                    .getAccessPoint()
                    .getValue();
            System.out.println("Found service, located at: " + serviceLocation);

            WineClient wineClient = new WineClient();
            wineClient.serverAddress = serviceLocation;
            wineClient.setDefaultUri(clientAddress);
            wineClient.setMarshaller(marshaller);
            wineClient.setUnmarshaller(marshaller);
            return wineClient;
        } catch (Exception e) {
            System.err.println("Exception while fetching wine client: " + Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    private String createBusiness() {
        BusinessEntity businessEntity = new BusinessEntity();
        Name businessNameEntity = new Name();
        businessNameEntity.setValue(businessName);
        businessEntity.getName().add(businessNameEntity);
        // Adding the business entity to the "save" structure, using our publisher's authentication info and saving away.
        BusinessEntity registeredBusiness = clerk.register(businessEntity);
        if (registeredBusiness == null) {
            System.out.println("Failed to create business");
            System.exit(1);
        }
        String businessKey = registeredBusiness.getBusinessKey();
        System.out.println("Business key: " + businessKey);
        return businessKey;
    }

    private String getBusiness() throws Exception {
        FindBusiness findBusiness = new FindBusiness();

        findBusiness.setAuthInfo(clerk.getAuthToken());

        org.uddi.api_v3.FindQualifiers qualifiers = new org.uddi.api_v3.FindQualifiers();
        qualifiers.getFindQualifier().add(UDDIConstants.EXACT_MATCH);
        findBusiness.setFindQualifiers(qualifiers);

        Name searchName = new Name();
        searchName.setValue(businessName);
        findBusiness.getName().add(searchName);
        BusinessList businessList = inquiry.findBusiness(findBusiness);
        List<BusinessInfo> businessDetails = businessList.getBusinessInfos().getBusinessInfo();
        if (businessDetails.isEmpty()) {
            return null;
        }
        return businessDetails.get(0).getBusinessKey();
    }

    private BusinessService getService(String businessKey) throws Exception {
        BusinessEntity businessDetail = clerk.getBusinessDetail(businessKey);
        if (businessDetail.getBusinessServices() == null) {
            return null;
        }
        return businessDetail.getBusinessServices().getBusinessService().stream()
                .filter(service -> serviceName.equals(service.getName().get(0).getValue()))
                .findFirst()
                .orElse(null);
    }

    private BusinessService createService(String businessKey) {
        BusinessService service = new BusinessService();
        service.setBusinessKey(businessKey);
        Name serviceNameEntity = new Name();
        serviceNameEntity.setValue(serviceName);
        service.getName().add(serviceNameEntity);

        // Add binding templates, etc...
        BindingTemplate bindingTemplate = new BindingTemplate();
        AccessPoint accessPoint = new AccessPoint();
        accessPoint.setUseType(AccessPointType.WSDL_DEPLOYMENT.toString());
        accessPoint.setValue(getWineEndpoint());
        bindingTemplate.setAccessPoint(accessPoint);
        BindingTemplates myBindingTemplates = new BindingTemplates();
        //optional but recommended step, this annotations our binding with all the standard SOAP tModel instance infos
        UDDIClient.addSOAPtModels(bindingTemplate);
        myBindingTemplates.getBindingTemplate().add(bindingTemplate);
        service.setBindingTemplates(myBindingTemplates);
        // Adding the service to the "save" structure, using our publisher's authentication info and saving away.
        BusinessService registeredService = clerk.register(service);
        if (registeredService == null){
            System.out.println("Failed to register service!");
            System.exit(1);
        }

        String serviceKey = registeredService.getServiceKey();
        System.out.println("Service key: " + serviceKey);

        return registeredService;
    }

}
