package com.topmonks.cache;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.Hazelcast;

@WebListener
public class CacheClientServletContextListener implements javax.servlet.ServletContextListener {

    private static final String CACHE_SERVER_HOST = "localhost";
    private static final int CACHE_SERVER_PORT = 5701;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // Create Hazelcast client configuration
        ClientConfig config = new ClientConfig();
        config.setNetworkConfig(new ClientNetworkConfig().addAddress(CACHE_SERVER_HOST + ":" + CACHE_SERVER_PORT));

        // Connect to Hazelcast server
        HazelcastClient.newHazelcastClient(config);
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Hazelcast.shutdownAll();
    }

}
