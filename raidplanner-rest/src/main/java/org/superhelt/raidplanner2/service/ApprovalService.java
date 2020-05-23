package org.superhelt.raidplanner2.service;

import org.jvnet.hk2.annotations.Service;
import org.superhelt.raidplanner2.ServerException;
import org.superhelt.raidplanner2.dao.ApprovalDao;
import org.superhelt.raidplanner2.dao.InstanceDao;
import org.superhelt.raidplanner2.dao.PlayerDao;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.*;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApprovalService {

    private final ApprovalDao approvalDao;
    private final InstanceDao instanceDao;
    private final PlayerDao playerDao;

    @Inject
    public ApprovalService(ApprovalDao approvalDao, InstanceDao instanceDao, PlayerDao playerDao) {
        this.approvalDao = approvalDao;
        this.instanceDao = instanceDao;
        this.playerDao = playerDao;
    }

    public List<Approval> getAll() {
        return approvalDao.get();
    }

    public List<Approval> getByCharacter(int characterId) {
        return approvalDao.get().stream().filter(a->a.getCharacter().getId()==characterId).collect(Collectors.toList());
    }

    public List<Approval> getByBoss(int bossId) {
        return approvalDao.get().stream().filter(a->a.getBoss().getId()==bossId).collect(Collectors.toList());
    }

    public Approval addApproval(int playerId, int characterId, int instanceId, int bossId) {
        Player player = getPlayer(playerId);
        Instance instance = getInstance(instanceId);
        Character character = getCharacter(player, characterId);
        Boss boss = getBoss(instance, bossId);

        if(approvalDao.get().stream().anyMatch(a->a.getBoss().getId()==bossId && a.getCharacter().getId() == characterId)) {
            throw new ServerException("Player %s is already approved for boss %s", player.getName(), boss.getName());
        }

        Approval approval = new Approval(character, boss);
        approvalDao.addApproval(approval);

        return approval;
    }

    public void deleteApproval(int playerId, int characterId, int instanceId, int bossId) {
        Player player = getPlayer(playerId);
        Instance instance = getInstance(instanceId);
        Character character = getCharacter(player, characterId);
        Boss boss = getBoss(instance, bossId);


        if(approvalDao.get().stream().noneMatch(a->a.getBoss().getId()==bossId && a.getCharacter().getId() == characterId)) {
            throw new ServerException("Player %s is not approved for boss %s, cannot delete", player.getName(), boss.getName());
        }

        Approval approval = new Approval(character, boss);
        approvalDao.deleteApproval(approval);
    }

    public Player getPlayer(int id) {
        return playerDao.get(id).orElseThrow(()->new ServerException("Player with id %d does not exist", id));
    }

    private Instance getInstance(int id) {
        return instanceDao.get(id).orElseThrow(()->new ServerException("Instance with id %d does not exist", id));
    }

    private Character getCharacter(Player player, int id) {
        return player.getCharacters().stream().filter(c->c.getId()==id).findFirst()
                .orElseThrow(()->new ServerException("Player %s does not have a character with id %d", player.getName(), id));
    }

    private Boss getBoss(Instance instance, int id) {
        return instance.getBosses().stream().filter(b->b.getId()==id).findFirst().orElseThrow(
                ()->new ServerException("Instance %s does not contain a boss with id %d", instance.getName(), id));
    }
}
