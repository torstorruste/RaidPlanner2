package org.superhelt.raidplanner2.om;

import javax.swing.*;
import java.net.URL;

public enum Role {
    Tank, Healer, Melee, Ranged;

    public ImageIcon createImageIcon() {
        URL url = getClass().getResource("/" + this +".png");
        if(url != null) {
            return new ImageIcon(url, this.toString());
        } else {
            return null;
        }
    }
}
