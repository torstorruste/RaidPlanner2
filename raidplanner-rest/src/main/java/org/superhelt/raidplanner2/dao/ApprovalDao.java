package org.superhelt.raidplanner2.dao;

import org.jvnet.hk2.annotations.Contract;
import org.superhelt.raidplanner2.om.Approval;

import java.util.List;

@Contract
public interface ApprovalDao {

    void addApproval(Approval approval);

    List<Approval> get();

    void deleteApproval(Approval approval);
}
