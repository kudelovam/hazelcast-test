package com.topmonks.cache.rest;

import javax.ws.rs.ApplicationPath;

import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;

@ApplicationPath("/")
public class CacheResourceConfig extends DefaultResourceConfig {

    public CacheResourceConfig() {
        super(CacheResource.class);
        getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, true);
    }

}
