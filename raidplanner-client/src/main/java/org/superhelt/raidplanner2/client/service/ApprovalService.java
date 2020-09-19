package org.superhelt.raidplanner2.client.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApprovalService {

    private static final Logger log = LoggerFactory.getLogger(ApprovalService.class);

    private final String baseUrl;

    public ApprovalService(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public List<Approval> getApprovals() {
        Client client = ClientBuilder.newClient();
        log.info("GET {}approvals", baseUrl);

        Approval[] approvals = client.target(baseUrl).path("approvals").request(MediaType.APPLICATION_JSON).get(Approval[].class);

        return new ArrayList<>(Arrays.asList(approvals));
    }

    public void addApproval(Instance instance, Boss boss, Player player, Character character) {
        Client client = ClientBuilder.newClient();
        log.info("POST {}{}", baseUrl, getApprovalUrl(instance, boss, player, character));

        client.target(baseUrl).path(getApprovalUrl(instance, boss, player, character))
                .request(MediaType.APPLICATION_JSON).post(Entity.json(null), Approval.class);
    }

    public void removeApproval(Instance instance, Boss boss, Player player, Character character) {
        Client client = ClientBuilder.newClient();
        log.info("DELETE {}{}", baseUrl, getApprovalUrl(instance, boss, player, character));

        client.target(baseUrl).path(getApprovalUrl(instance, boss, player, character))
                .request(MediaType.APPLICATION_JSON).delete();

    }

    private String getApprovalUrl(Instance instance, Boss boss, Player player, Character character) {
        return String.format("instances/%d/bosses/%d/approvals/%d/%d",
                instance.getId(), boss.getId(), player.getId(), character.getId());
    }
}
