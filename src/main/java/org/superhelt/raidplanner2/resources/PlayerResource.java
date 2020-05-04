package org.superhelt.raidplanner2.resources;

import org.superhelt.raidplanner2.dao.PlayerDaoMock;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/players")
public class PlayerResource {

    private final PlayerDaoMock playerDao;

    @Inject
    public PlayerResource(PlayerDaoMock playerDao) {
        this.playerDao = playerDao;
    }

    @GET
    public Response getPlayers() {
        return Response.ok(playerDao.getPlayers().size()).build();
    }
}
