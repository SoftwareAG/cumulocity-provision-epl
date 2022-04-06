package com.cumulocity.provision.utils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ProvisionForm {
    private String targetTenant;
    private String model;
}
