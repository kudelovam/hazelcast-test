package com.topmonks.cache.rest;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.hazelcast.core.IList;
import com.topmonks.cache.Constants;

@Path("cache")
public class CacheResource {

    @Context
    private ServletContext servletContext;
    private IList<String> remoteCache;
    private IList<String> localCache;

    /**
     * Get all remotely cached values.
     */
    @GET
    @Path("remote")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getRemoteCache() {
        return remoteCache;
    }

    /**
     * Get all locally cached values.
     */
    @GET
    @Path("local")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> getLocalCache() {
        return localCache;
    }

    /**
     * Add given value in the remote cache.
     */
    @POST
    @Path("remote")
    @Consumes(MediaType.TEXT_PLAIN)
    public void cacheRemotely(String value) {
        if (value == null) {
            throw new BadRequestException();
        }
        remoteCache.add(value);
    }

    /**
     * Add given value in the local cache.
     */
    @POST
    @Path("local")
    @Consumes(MediaType.TEXT_PLAIN)
    public void cacheLocally(String value) {
        if (value == null) {
            throw new BadRequestException();
        }
        localCache.add(value);
    }

    @PostConstruct
    @SuppressWarnings("unchecked")
    private void initialize() {
        remoteCache = (IList<String>) servletContext.getAttribute(Constants.REMOTE_CACHE_CONTEXT_ATTR);
        localCache = (IList<String>) servletContext.getAttribute(Constants.LOCAL_CACHE_CONTEXT_ATTR);
    }

}
