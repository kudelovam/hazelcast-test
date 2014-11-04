package com.topmonks.cache;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapConfig.EvictionPolicy;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;

@WebListener
public class CacheServerServletContextListener implements ServletContextListener {

    private static final int CACHE_SERVER_PORT = 5701;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // Create configuration
        NetworkConfig networkConfig = new NetworkConfig().
                setPort(CACHE_SERVER_PORT);
        MapConfig mapConfig = new MapConfig().
                setEvictionPolicy(EvictionPolicy.LRU).
                setEvictionPercentage(25);
        GroupConfig groupConfig = new GroupConfig("shared");
        Config config = new Config().
                setNetworkConfig(networkConfig).
                setGroupConfig(groupConfig);
        config.getMapConfigs().put("test", mapConfig);

        // Create Hazelcast instance
        Hazelcast.newHazelcastInstance(config);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Hazelcast.shutdownAll();
    }

}
