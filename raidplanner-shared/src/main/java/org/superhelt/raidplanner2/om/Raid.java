package org.superhelt.raidplanner2.om;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.superhelt.raidplanner2.serializers.LocalDateDeserializer;
import org.superhelt.raidplanner2.serializers.LocalDateSerializer;

import java.time.LocalDate;
import java.util.List;

public class Raid {

    private int id;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
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

    public int findNextEncounterId() {
        return encounters.stream().mapToInt(Encounter::getId).max().orElse(0)+1;
    }
}
