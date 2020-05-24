package org.superhelt.raidplanner2.service;

import org.jvnet.hk2.annotations.Service;
import org.superhelt.raidplanner2.ServerException;
import org.superhelt.raidplanner2.dao.RaidDao;
import org.superhelt.raidplanner2.om.Raid;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class RaidService {

    private final RaidDao dao;

    @Inject
    public RaidService(RaidDao dao) {
        this.dao = dao;
    }

    public List<Raid> getAll() {
        return dao.getAll();
    }

    public Raid addRaid(Raid raid) {
        if(dao.getAll().stream().anyMatch(r->r.getDate().equals(raid.getDate()))) {
            throw new ServerException(400, "Raid at time %s already exists", raid.getDate());
        }
        Raid raidToSave = new Raid(findId(), raid.getDate(), new ArrayList<>(), new ArrayList<>());
        dao.add(raidToSave);
        return raidToSave;
    }

    private int findId() {
        return dao.getAll().stream().mapToInt(Raid::getId).max().orElse(0)+1;
    }
}
