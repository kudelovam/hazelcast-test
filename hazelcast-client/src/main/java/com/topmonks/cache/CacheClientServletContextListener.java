package com.topmonks.cache;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@WebListener
public class CacheClientServletContextListener implements javax.servlet.ServletContextListener {

    private static final String CACHE_SERVER_HOST = "localhost";
    private static final int CACHE_SERVER_PORT = 5701;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // Create Hazelcast client configuration
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.
            setNetworkConfig(new ClientNetworkConfig().addAddress(CACHE_SERVER_HOST + ":" + CACHE_SERVER_PORT)).
            setGroupConfig(new GroupConfig("shared"));

        // Connect to remote Hazelcast instance
        HazelcastInstance remoteInstance = HazelcastClient.newHazelcastClient(clientConfig);

        // Create configuration for local Hazelcast instance
        Config config = new Config().setGroupConfig(new GroupConfig("local"));

        // Create new local Hazelcast instance
        HazelcastInstance localInstance = Hazelcast.newHazelcastInstance(config);

        // Add instances to servlet context
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute(Constants.REMOTE_CACHE_CONTEXT_ATTR, remoteInstance.getList("test"));
        servletContext.setAttribute(Constants.LOCAL_CACHE_CONTEXT_ATTR, localInstance.getList("test"));
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Hazelcast.shutdownAll();
    }

}
