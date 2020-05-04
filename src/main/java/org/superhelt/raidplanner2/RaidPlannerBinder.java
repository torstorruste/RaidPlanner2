package org.superhelt.raidplanner2;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.superhelt.raidplanner2.dao.PlayerDaoMock;

public class RaidPlannerBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bindAsContract(PlayerDaoMock.class);
    }
}
