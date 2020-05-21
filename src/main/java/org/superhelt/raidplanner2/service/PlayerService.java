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

    private PlayerDao playerDao;

    @Inject
    public PlayerService(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public List<Player> getPlayers() {
        return playerDao.getPlayers();
    }

    public Optional<Player> getPlayer(int id) {
        return playerDao.getPlayer(id);
    }

    public Player addPlayer(Player player) {
        Player playerToSave = new Player(findId(), player.getName(), new ArrayList<>());
        playerDao.addPlayer(playerToSave);
        return playerToSave;
    }

    public Character addCharacter(Player player, Character character) throws ServiceException {
        if (player.getCharacters().stream().anyMatch(c -> c.getName().equals(character.getName()))) {
            throw new ServiceException("Character with that name already exists");
        }

        Character characterToSave = new Character(findCharacterId(player), character.getName(), character.getCharacterClass(), character.getRoles());
        player.getCharacters().add(characterToSave);
        playerDao.updatePlayer(player);

        return characterToSave;
    }

    public void updateCharacter(Player player, Character character) throws ServiceException {
        if (player.getCharacters().stream().noneMatch(c -> c.getId() == character.getId())) {
            throw new ServiceException("No character with the given id");
        }

        player.getCharacters().removeIf(c -> c.getId() == character.getId());
        player.getCharacters().add(character);

        playerDao.updatePlayer(player);
    }

    public void deleteCharacter(Player player, int characterId) throws ServiceException {
        if (player.getCharacters().stream().noneMatch(c -> c.getId() == characterId)) {
            throw new ServiceException("No character with the given id");
        }

        player.getCharacters().removeIf(c->c.getId()==characterId);
        playerDao.updatePlayer(player);
    }

    private int findId() {
        return playerDao.getPlayers().stream().mapToInt(Player::getId).max().orElse(0) + 1;
    }

    private int findCharacterId(Player player) {
        return player.getCharacters().stream().mapToInt(Character::getId).max().orElse(0) + 1;
    }
}
