package com.cumulocity.provision.services;


import java.util.ArrayList;
import java.util.List;

import com.cumulocity.microservice.subscription.model.MicroserviceSubscriptionAddedEvent;
import com.cumulocity.microservice.subscription.model.MicroserviceSubscriptionRemovedEvent;
import com.cumulocity.microservice.subscription.service.MicroserviceSubscriptionsService;
import com.cumulocity.provision.utils.ProvisionForm;
import com.cumulocity.provision.utils.ResultForm;
import com.cumulocity.rest.representation.inventory.ManagedObjectRepresentation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import c8y.IsDevice;


@Service
public class ProvisionService {

    @Autowired
    private MicroserviceSubscriptionsService subscriptionsService;

    @Autowired
    C8YClient c8yClient;

    private static final Logger logger = LoggerFactory.getLogger(ProvisionService.class);

    private ManagedObjectRepresentation createdModel;

    @EventListener
    public void onMicroserviceSubscribed(MicroserviceSubscriptionAddedEvent event) {
        try {
            String tenant = event.getCredentials().getTenant();
            logger.info("Subscribe event received for tenant {}", tenant);
            if(tenant != null) {
                subscriptionsService.runForTenant(tenant, () -> {
                    // empty
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @EventListener
    public void onMicroserviceUnsubscribed(MicroserviceSubscriptionRemovedEvent event) {
        try {
            String tenant = event.getTenant();
            logger.info("Unsubsribe Event received for Tenant {}. Simulator continue to run for this tenant.", tenant);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public List<ResultForm> provision(ProvisionForm pf) {
        
        List <ResultForm> result = new ArrayList<ResultForm>();
        String model = pf.getModel();
        String targetTenant = pf.getTargetTenant();
        String sourceTenant = subscriptionsService.getTenant();
        logger.info("Received new provision request: model {}, sourceTenant {}, targetTenant {}.", model, sourceTenant, targetTenant);
        ManagedObjectRepresentation mor = c8yClient.getModel(model, sourceTenant);
        logger.info("Found sourceModel: model {}", mor);
   
        subscriptionsService.runForEachTenant( () -> {
            String current = subscriptionsService.getTenant();
            logger.info("Testing for the right tenant: current tenant:{}, target tenant:{}.", current, targetTenant);
            // test if we are in the right tenant
            if (  current.equals(targetTenant) ){
                ManagedObjectRepresentation mo = new ManagedObjectRepresentation();
                mo.setName(mor.getName() + "_" + mor.getId());
                mo.setType(mor.getName()+ "_type");
                mo.set(new IsDevice());
                createdModel = c8yClient.createModel(mo);
                result.add(new ResultForm(targetTenant, model, createdModel.getId().getValue()));
                logger.info("Created new model:{}", createdModel);
            }
        });

        return result;

    }

}
