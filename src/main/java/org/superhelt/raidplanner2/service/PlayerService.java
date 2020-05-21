package org.superhelt.raidplanner2.service;

import org.jvnet.hk2.annotations.Service;
import org.superhelt.raidplanner2.dao.PlayerDao;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.Player;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerDao dao;

    @Inject
    public PlayerService(PlayerDao dao) {
        this.dao = dao;
    }

    public List<Player> getPlayers() {
        return dao.get();
    }

    public Optional<Player> getPlayer(int id) {
        return dao.get(id);
    }

    public Player addPlayer(Player player) {
        if(dao.get().stream().anyMatch(p->p.getName().equals(player.getName()))) {
            throw new ServiceException("A player with the name %s already exists", player.getName());
        }

        Player playerToSave = new Player(findId(), player.getName(), new ArrayList<>());
        dao.add(playerToSave);
        return playerToSave;
    }

    public Character addCharacter(Player player, Character character) {
        if (player.getCharacters().stream().anyMatch(c -> c.getName().equals(character.getName()))) {
            throw new ServiceException("Player %s already has a character with the name %s", player.getName(), character.getName());
        }

        Character characterToSave = new Character(findCharacterId(), character.getName(), character.getCharacterClass(), character.getRoles());
        player.getCharacters().add(characterToSave);
        dao.update(player);

        return characterToSave;
    }

    public void updateCharacter(Player player, Character character) {
        if (player.getCharacters().stream().noneMatch(c -> c.getId() == character.getId())) {
            throw new ServiceException("Player %s has no character with the id %d", player.getName(), character.getId());
        }

        player.getCharacters().removeIf(c -> c.getId() == character.getId());
        player.getCharacters().add(character);

        dao.update(player);
    }

    public void deleteCharacter(Player player, int characterId) {
        if (player.getCharacters().stream().noneMatch(c -> c.getId() == characterId)) {
            throw new ServiceException("Player %s has no character with the id %d", player.getName(), characterId);
        }

        player.getCharacters().removeIf(c->c.getId()==characterId);
        dao.update(player);
    }

    public void deletePlayer(int id) {
        if(dao.get().stream().noneMatch(p->p.getId()==id)) {
            throw new ServiceException("No player with id %d exists", id);
        }

        dao.delete(id);
    }

    private int findId() {
        return dao.get().stream().mapToInt(Player::getId).max().orElse(0) + 1;
    }

    private int findCharacterId() {
        return dao.get().stream().flatMap(p->p.getCharacters().stream()).mapToInt(Character::getId).max().orElse(0) + 1;
    }
}
