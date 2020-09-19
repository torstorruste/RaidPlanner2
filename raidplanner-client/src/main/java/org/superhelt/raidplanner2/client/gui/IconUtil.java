package org.superhelt.raidplanner2.client.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.om.Character;
import org.superhelt.raidplanner2.om.CharacterClass;
import org.superhelt.raidplanner2.om.Role;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class IconUtil {

    private static final Logger log = LoggerFactory.getLogger(IconUtil.class);

    public static ImageIcon getDeleteIcon() {
        URL url = IconUtil.class.getResource("/delete.png");
        return new ImageIcon(url, "Delete");
    }

    public static ImageIcon getAddIcon() {
        URL url = IconUtil.class.getResource("/add.jpg");
        return new ImageIcon(url, "Add");
    }

    public static ImageIcon getClassIcon(CharacterClass characterClass) {
        try {
            URL url = IconUtil.class.getResource("/classes/" + characterClass.toString().toLowerCase() + ".png");
            ImageIcon imageIcon = new ImageIcon(url);

            Image image = imageIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);

            return new ImageIcon(image, characterClass.name());
        } catch(Exception e) {
            log.error("Unable to fetch icon for {}", characterClass);
            return null;
        }
    }

    public static JLabel getClassLabel(Character character) {
        JLabel label = new JLabel(character.getName(), getClassIcon(character.getCharacterClass()), 0);
        label.setForeground(character.getCharacterClass().getColor());
        return label;
    }

    public static ImageIcon getBuffIcon(Buff buff) {
        try {
            URL url = IconUtil.class.getResource("/buffs/" + buff.toString().toLowerCase() + ".jpg");
            ImageIcon imageIcon = new ImageIcon(url);

            Image image = imageIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);

            return new ImageIcon(image, buff.name());
        } catch(Exception e) {
            log.error("Unable to fetch icon for {}", buff);
            return null;
        }
    }

    public static ImageIcon getRoleIcon(Role role) {
        URL url = IconUtil.class.getResource("/roles/" + role +".png");
        if(url != null) {
            return new ImageIcon(url, role.toString());
        } else {
            return null;
        }
    }
}
