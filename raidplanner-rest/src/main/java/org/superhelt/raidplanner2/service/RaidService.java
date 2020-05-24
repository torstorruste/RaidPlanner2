package org.superhelt.raidplanner2.service;

import org.jvnet.hk2.annotations.Service;
import org.superhelt.raidplanner2.ServerException;
import org.superhelt.raidplanner2.dao.PlayerDao;
import org.superhelt.raidplanner2.dao.RaidDao;
import org.superhelt.raidplanner2.om.Player;
import org.superhelt.raidplanner2.om.Raid;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class RaidService {

    private final RaidDao raidDao;
    private final PlayerDao playerDao;

    @Inject
    public RaidService(RaidDao raidDao, PlayerDao playerDao) {
        this.raidDao = raidDao;
        this.playerDao = playerDao;
    }

    public List<Raid> getAll() {
        return raidDao.getAll();
    }

    public Raid get(int id) {
        return raidDao.get(id).orElseThrow(()->new ServerException(404, String.format("Raid with id %d does not exist", id)));
    }

    public Raid addRaid(Raid raid) {
        if(raidDao.getAll().stream().anyMatch(r->r.getDate().equals(raid.getDate()))) {
            throw new ServerException(400, "Raid at time %s already exists", raid.getDate());
        }
        Raid raidToSave = new Raid(findId(), raid.getDate(), new ArrayList<>(), new ArrayList<>());
        raidDao.add(raidToSave);
        return raidToSave;
    }

    public Player signup(Raid raid, Player player) {
        Player savedPlayer = playerDao.get(player.getId()).orElseThrow(()->new ServerException(400, "Player with id "+player.getId()+" does not exist"));

        if(raid.getSignedUp().stream().anyMatch(p->p.getId()==player.getId())) {
            throw new ServerException("Player %s is already signed up to raid %s", player.getName(), raid.getDate());
        }

        raid.getSignedUp().add(player);
        raidDao.update(raid);
        return savedPlayer;
    }

    private int findId() {
        return raidDao.getAll().stream().mapToInt(Raid::getId).max().orElse(0)+1;
    }
}
