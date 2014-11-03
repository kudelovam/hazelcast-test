package com.topmonks.cache;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.hazelcast.config.Config;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;

@WebListener
public class CacheServerServletContextListener implements ServletContextListener {

    private static final int CACHE_SERVER_PORT = 5701;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // Create configuration
        Config config = new Config().setNetworkConfig(new NetworkConfig().setPort(CACHE_SERVER_PORT));

        // Create Hazelcast instance
        Hazelcast.newHazelcastInstance(config);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Hazelcast.shutdownAll();
    }

}
