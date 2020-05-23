package org.superhelt.raidplanner2.service;

import org.jvnet.hk2.annotations.Service;
import org.superhelt.raidplanner2.ServerException;
import org.superhelt.raidplanner2.dao.ApprovalDao;
import org.superhelt.raidplanner2.dao.InstanceDao;
import org.superhelt.raidplanner2.dao.PlayerDao;
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

    public List<Approval> getByCharacter(int playerId, int characterId) {
        return approvalDao.get().stream().filter(a->a.getPlayer().getId()==playerId && a.getCharacter().getId()==characterId).collect(Collectors.toList());
    }

    public List<Approval> getByBoss(int instanceId, int bossId) {
        return approvalDao.get().stream().filter(a->a.getInstance().getId()==instanceId && a.getBoss().getId()==bossId).collect(Collectors.toList());
    }

    public Approval addApproval(int playerId, int characterId, int instanceId, int bossId) {
        Player player = playerDao.get(playerId).orElseThrow(()->new ServerException("Player with id %d does not exist", playerId));
        Instance instance = instanceDao.get(instanceId).orElseThrow(()->new ServerException("Instance with id %d does not exist", instanceId));

        org.superhelt.raidplanner2.om.Character character = player.getCharacters().stream().filter(c->c.getId()==characterId).findFirst()
                .orElseThrow(()->new ServerException("Player %s does not have a character with id %d", player.getName(), characterId));

        Boss boss = instance.getBosses().stream().filter(b->b.getId()==bossId).findFirst().orElseThrow(
                ()->new ServerException("Instance %s does not contain a boss with id %d", instance.getName(), bossId));

        Approval approval = new Approval(player, character, instance, boss);
        approvalDao.addApproval(approval);

        return approval;
    }
}
