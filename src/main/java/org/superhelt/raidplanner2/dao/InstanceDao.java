package org.superhelt.raidplanner2.dao;

import org.jvnet.hk2.annotations.Service;
import org.superhelt.raidplanner2.om.Boss;
import org.superhelt.raidplanner2.om.Instance;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Service
@Singleton
public class InstanceDao {

    public static List<Instance> instances = new ArrayList<>();

    static {
        List<Boss> bosses = new ArrayList<>();
        bosses.add(new Boss(1, "Wrathion"));
        bosses.add(new Boss(2, "Skitra"));
        bosses.add(new Boss(3, "Maut"));
        bosses.add(new Boss(4, "Hivemind"));
        bosses.add(new Boss(5, "Shad'har"));
        bosses.add(new Boss(6, "Inquisitor"));
        bosses.add(new Boss(7, "Drest'agath"));
        bosses.add(new Boss(8, "Vexiona"));
        bosses.add(new Boss(9, "Ra-Den"));
        bosses.add(new Boss(10, "Il'gynoth"));
        bosses.add(new Boss(11, "Carapace"));
        bosses.add(new Boss(12, "N'zoth"));

        instances.add(new Instance(1, "Ny'alotha", bosses));
    }

    public List<Instance> getInstances() {
        return instances;
    }
}
