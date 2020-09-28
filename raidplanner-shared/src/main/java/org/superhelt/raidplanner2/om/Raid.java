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
    private List<Integer> signedUp;
    private boolean finalized;

    public Raid() {
    }

    public Raid(int id, LocalDate date, List<Encounter> encounters, List<Integer> signedUp, boolean finalized) {
        this.id = id;
        this.date = date;
        this.encounters = encounters;
        this.signedUp = signedUp;
        this.finalized = finalized;
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

    public List<Integer> getSignedUp() {
        return signedUp;
    }

    public boolean isFinalized() {
        return finalized;
    }

    public int findNextEncounterId() {
        return encounters.stream().mapToInt(Encounter::getId).max().orElse(0)+1;
    }
}
