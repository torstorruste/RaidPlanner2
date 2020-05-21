package org.superhelt.raidplanner2;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.superhelt.raidplanner2.dao.PlayerDao;

public class RaidPlannerBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bindAsContract(PlayerDao.class);
    }
}
