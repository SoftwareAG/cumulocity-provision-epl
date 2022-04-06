/*Copyright (c) 2018-2021 Software AG, Darmstadt, Germany and/or Software AG USA Inc., Reston, VA, USA, and/or its subsidiaries and/or its affiliates and/or their licensors.
* Use, reproduction, transfer, publication or disclosure is prohibited except as specifically provided for in your License Agreement with Software AG.
*/
package com.cumulocity.provision.utils;

import com.cumulocity.sdk.client.ParamSource;
import com.cumulocity.sdk.client.inventory.InventoryFilter;

public class CustomInventoryFilter extends InventoryFilter {
    @ParamSource
    private String query;

    public CustomInventoryFilter byQuery(String query) {
        this.query = query;
        return this;
    }

    @Override
    public String getType() {
        return query;
    }
}
