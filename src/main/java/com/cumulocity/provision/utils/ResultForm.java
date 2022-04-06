package com.cumulocity.provision.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public class ResultForm {
    private String tenant;
    private String model;
    private String id;
}
