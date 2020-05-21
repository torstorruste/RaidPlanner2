package org.superhelt.raidplanner2.dao;

import org.jvnet.hk2.annotations.Service;
import org.superhelt.raidplanner2.om.Approval;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Service
public class ApprovalDao {

    private static final List<Approval> approvals = new ArrayList<>();

    public void addApproval(Approval approval) {
        approvals.add(approval);
    }
}
