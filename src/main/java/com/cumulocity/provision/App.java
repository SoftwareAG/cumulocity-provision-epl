package com.cumulocity.provision;

import com.cumulocity.microservice.autoconfigure.MicroserviceApplication;
import com.cumulocity.microservice.context.annotation.EnableContextSupport;
import com.cumulocity.provision.services.ProvisionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@MicroserviceApplication
@EnableScheduling
@EnableContextSupport
@EnableAsync
public class App {

    @Autowired
    ProvisionService provisionService;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
