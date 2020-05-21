package org.superhelt.raidplanner2;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.superhelt.raidplanner2.dao.ApprovalDao;
import org.superhelt.raidplanner2.dao.InstanceDao;
import org.superhelt.raidplanner2.dao.PlayerDao;
import org.superhelt.raidplanner2.service.ApprovalService;
import org.superhelt.raidplanner2.service.InstanceService;
import org.superhelt.raidplanner2.service.PlayerService;

public class RaidPlannerBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bindAsContract(ApprovalDao.class);
        bindAsContract(ApprovalService.class);

        bindAsContract(InstanceDao.class);
        bindAsContract(InstanceService.class);

        bindAsContract(PlayerDao.class);
        bindAsContract(PlayerService.class);
    }
}
