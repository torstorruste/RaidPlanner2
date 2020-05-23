package org.superhelt.raidplanner2;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.superhelt.raidplanner2.dao.*;
import org.superhelt.raidplanner2.service.ApprovalService;
import org.superhelt.raidplanner2.service.InstanceService;
import org.superhelt.raidplanner2.service.PlayerService;

public class RaidPlannerBinder extends AbstractBinder {
    @Override
    protected void configure() {
        bind(FileApprovalDao.class).to(ApprovalDao.class);
        bindAsContract(ApprovalService.class);

        bind(FileInstanceDao.class).to(InstanceDao.class);
        bindAsContract(InstanceService.class);

        bind(FilePlayerDao.class).to(PlayerDao.class);
        bindAsContract(PlayerService.class);
    }
}
