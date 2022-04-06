package com.cumulocity.provision.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cumulocity.provision.services.ProvisionService;
import com.cumulocity.provision.utils.ProvisionForm;
import com.cumulocity.provision.utils.ResultForm;

@RestController
public class ProvisionAPI {
    private static final Logger logger = LoggerFactory.getLogger(ProvisionAPI.class);

    @Autowired
    ProvisionService provisionService;

    @RequestMapping(value = "/provision", method = RequestMethod.POST)
    public ResponseEntity<List<ResultForm>> provisionModel(@RequestBody ProvisionForm provisionForm) {
        logger.info("Provision model...");
        List<ResultForm> result = provisionService.provision(provisionForm);
        return ResponseEntity.ok(result);
    }

}
