package com.cumulocity.provision.services;

import c8y.IsDevice;

import com.cumulocity.model.ID;
import com.cumulocity.provision.utils.CustomInventoryFilter;
import com.cumulocity.rest.representation.event.EventRepresentation;
import com.cumulocity.rest.representation.identity.ExternalIDRepresentation;
import com.cumulocity.rest.representation.inventory.ManagedObjectRepresentation;
import com.cumulocity.sdk.client.SDKException;
import com.cumulocity.sdk.client.event.EventApi;
import com.cumulocity.sdk.client.identity.IdentityApi;
import com.cumulocity.sdk.client.inventory.InventoryApi;
import com.cumulocity.sdk.client.inventory.ManagedObjectCollection;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class C8YClient {

    @Autowired
    InventoryApi inventoryApi;

    @Autowired
    IdentityApi identityApi;

    @Autowired
    EventApi eventApi;

    private static final Logger logger = LoggerFactory.getLogger(C8YClient.class);

    private static final String SERIAL = "c8y_Serial";

    public ExternalIDRepresentation findExternalId(String externalId, String type) {
        ID id = new ID();
        if(type != null)
            id.setType(type);
        else
            id.setType(SERIAL);
        id.setValue(externalId);
        ExternalIDRepresentation extId = null;
        try {
            extId = identityApi.getExternalId(id);
        } catch (SDKException e) {
            logger.info("External ID {} not found", externalId);
        }
        return extId;
    }

    
    public ManagedObjectRepresentation getModel(String model, String sourceTenant) {
        ManagedObjectRepresentation result = null;
        CustomInventoryFilter inventoryFilter = new CustomInventoryFilter();
		inventoryFilter.byQuery("q=$filter=(name eq '" + model + "')");
        try  {
            ManagedObjectCollection moc = inventoryApi.getManagedObjectsByFilter(inventoryFilter);
            for (ManagedObjectRepresentation mor : moc.get().allPages()) {
                result = mor;
                break;
            }
        } catch (SDKException e) {
            logger.error("Model with name: {} does not exist in sourceTenant: {}!", model, sourceTenant);
        }
        return result;
    }

    public ManagedObjectRepresentation createModel(ManagedObjectRepresentation mor) {
        ManagedObjectRepresentation res = inventoryApi.create(mor);
        return res;
    }

    public EventRepresentation createDefaultEvent(ManagedObjectRepresentation mor) {
        logger.info("Creating Event...");
        EventRepresentation eventRepresentation = new EventRepresentation();
        eventRepresentation.setSource(mor);
        DateTime dt = DateTime.now(DateTimeZone.UTC);
        eventRepresentation.setDateTime(dt);
        eventRepresentation.setText("Simulated Event");
        eventRepresentation.setType("c8y_SimulatedEvent");
        EventRepresentation event =  eventApi.create(eventRepresentation);
        logger.debug("Event created: "+event.toJSON());
        return event;
    }

}
