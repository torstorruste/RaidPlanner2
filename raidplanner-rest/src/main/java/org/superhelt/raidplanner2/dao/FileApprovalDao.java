package org.superhelt.raidplanner2.dao;

import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.om.Approval;

import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Singleton
public class FileApprovalDao implements ApprovalDao {

    private static final Logger log = LoggerFactory.getLogger(FileInstanceDao.class);

    private static final List<Approval> approvals = new ArrayList<>();

    private static final Path jsonFile = Paths.get("approvals.json");

    static {
        try {
            List<Approval> storedApprovals = FileWriter.readFromFile(jsonFile, Approval[].class);
            approvals.addAll(storedApprovals);
        } catch (IOException e) {
            log.error("Unable to read instances from {}", jsonFile);
        }
    }

    public List<Approval> get() {
        return approvals;
    }

    public void addApproval(Approval approval) {
        approvals.add(approval);
        FileWriter.writeToFile(jsonFile, approvals);
    }

    public void deleteApproval(Approval approval) {
        approvals.removeIf(a->a.getCharacter().getId()==approval.getCharacter().getId() && a.getBoss().getId()==approval.getBoss().getId());
        FileWriter.writeToFile(jsonFile, approvals);
    }
}

