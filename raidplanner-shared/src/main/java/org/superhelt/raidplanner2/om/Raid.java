package org.superhelt.raidplanner2.om;

import java.time.LocalDate;
import java.util.List;

public class Raid {

    private int id;
    private LocalDate date;
    private List<Encounter> encounters;
    private List<Player> signedUp;

    public Raid() {
    }

    public Raid(int id, LocalDate date, List<Encounter> encounters, List<Player> signedUp) {
        this.id = id;
        this.date = date;
        this.encounters = encounters;
        this.signedUp = signedUp;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Encounter> getEncounters() {
        return encounters;
    }

    public List<Player> getSignedUp() {
        return signedUp;
    }
}
