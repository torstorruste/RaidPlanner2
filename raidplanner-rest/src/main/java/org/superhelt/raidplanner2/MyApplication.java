package org.superhelt.raidplanner2;

import org.glassfish.jersey.server.ResourceConfig;
import org.superhelt.raidplanner2.filter.CorsFilter;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/")
public class MyApplication extends ResourceConfig {
    public MyApplication() {
        register(new RaidPlannerBinder());
        register(GenericExceptionMapper.class);
        register(CorsFilter.class);
        packages("org.superhelt.raidplanner2");
    }
}
