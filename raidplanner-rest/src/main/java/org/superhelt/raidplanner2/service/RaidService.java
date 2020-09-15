package org.superhelt.raidplanner2.service;

import org.jvnet.hk2.annotations.Service;
import org.superhelt.raidplanner2.ServerException;
import org.superhelt.raidplanner2.dao.InstanceDao;
import org.superhelt.raidplanner2.dao.PlayerDao;
import org.superhelt.raidplanner2.dao.RaidDao;
import org.superhelt.raidplanner2.om.*;
import org.superhelt.raidplanner2.om.Character;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RaidService {

    private final RaidDao raidDao;
    private final PlayerDao playerDao;
    private final InstanceDao instanceDao;

    @Inject
    public RaidService(RaidDao raidDao, PlayerDao playerDao, InstanceDao instanceDao) {
        this.raidDao = raidDao;
        this.playerDao = playerDao;
        this.instanceDao = instanceDao;
    }

    public List<Raid> getRaids() {
        return raidDao.getAll();
    }

    public Raid getRaid(int id) {
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

        if(raid.getSignedUp().stream().anyMatch(p->p==player.getId())) {
            throw new ServerException("Player %s is already signed up to raid %s", player.getName(), raid.getDate());
        }

        raid.getSignedUp().add(player.getId());
        raidDao.update(raid);
        return savedPlayer;
    }

    public void unsign(Raid raid, Player player) {
        raid.getSignedUp().removeIf(p->p==player.getId());
        raidDao.update(raid);
    }

    private int findId() {
        return raidDao.getAll().stream().mapToInt(Raid::getId).max().orElse(0)+1;
    }

    public void addEncounter(Raid raid, Boss boss) {
        Optional<Boss> savedBoss = instanceDao.get().stream()
                .flatMap(i -> i.getBosses().stream())
                .filter(i -> i.getId() == boss.getId() && i.getName().equals(boss.getName()))
                .findFirst();

        if(!savedBoss.isPresent()) {
            throw new ServerException("Boss %s (id=%d) does not exist", boss.getName(), boss.getId());
        }

        if(raid.getEncounters().stream()
                .map(Encounter::getBossId)
                .anyMatch(b->b==boss.getId())) {
            throw new ServerException("Boss %s (id=%d) is already an encounter for the raid", boss.getName(), boss.getId());
        }

        int encounterId = raid.findNextEncounterId();

        raid.getEncounters().add(new Encounter(encounterId, boss.getId(), new ArrayList<>()));

        raidDao.update(raid);
    }

    public void deleteEncounter(Raid raid, int encounterId) {
        raid.getEncounters().removeIf(e->e.getId()==encounterId);

        raidDao.update(raid);
    }

    public void addCharacter(Raid raid, int encounterId, EncounterCharacter encounterCharacter) {
        if(encounterCharacter.getRole()==null) {
            throw new ServerException("Role must be specified");
        }

        Optional<Encounter> encounter = raid.getEncounters().stream().filter(e -> e.getId() == encounterId).findFirst();

        if(!encounter.isPresent()) {
            throw new ServerException("Encounter %d does not exist in raid %d", encounterId, raid.getId());
        }

        Optional<Player> player = playerDao.get(encounterCharacter.getPlayerId());

        if(!player.isPresent()) {
            throw new ServerException("Player %d does not exist", encounterCharacter.getPlayerId());
        }

        Optional<Character> character = player.get().getCharacters().stream().filter(c -> c.getId() == encounterCharacter.getCharacterId()).findFirst();

        if(!character.isPresent()) {
            throw new ServerException("Player %d does not have a character with the id %d", encounterCharacter.getPlayerId(), encounterCharacter.getPlayerId());
        }

        if(encounter.get().getCharacters().stream()
                .anyMatch(c->c.getPlayerId()==encounterCharacter.getPlayerId())) {
            throw new ServerException("Player %d is already added to the encounter", encounterCharacter.getPlayerId());
        }

        encounter.get().getCharacters().add(encounterCharacter);

        raidDao.update(raid);
    }

    public void deleteCharacter(Raid raid, int encounterId, int characterId) {
        Optional<Encounter> encounter = raid.getEncounters().stream().filter(e -> e.getId() == encounterId).findFirst();

        if(!encounter.isPresent()) {
            throw new ServerException("Encounter %d does not exist in raid %d", encounterId, raid.getId());
        }

        if(!encounter.get().getCharacters().stream().anyMatch(c->c.getCharacterId()==characterId)) {
            throw new ServerException("Character %d is not added to encounter %d", characterId, encounterId);
        }

        encounter.get().getCharacters().removeIf(c->c.getCharacterId()==characterId);

        raidDao.update(raid);

    }
}
