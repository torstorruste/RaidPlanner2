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

    public static ImageIcon getIcon(String name) {
        URL url = IconUtil.class.getResource("/icons/"+name+".jpg");
        if(url == null) {
            url = IconUtil.class.getResource("/icons/"+name+".png");
        }
        return getIcon(url, 16);
    }

    public static ImageIcon getClassIcon(CharacterClass characterClass) {
            URL url = IconUtil.class.getResource("/classes/" + characterClass.toString().toLowerCase() + ".png");
            return getIcon(url, 16);
    }

    public static JLabel getClassLabel(Character character) {
        JLabel label = new JLabel(character.getName(), getClassIcon(character.getCharacterClass()), 0);
        if(character.getCharacterClass()!=CharacterClass.Priest) {
            label.setForeground(character.getCharacterClass().getColor());
        } else {
            label.setForeground(new Color(100,100,100));
        }
        return label;
    }

    public static ImageIcon getBuffIcon(Buff buff) {
        URL url = IconUtil.class.getResource("/buffs/" + buff.toString().toLowerCase() + ".jpg");
        return getIcon(url, 16);
    }

    public static ImageIcon getRoleIcon(Role role) {
        URL url = IconUtil.class.getResource("/roles/" + role + ".png");
        return getIcon(url, 16);
    }

    private static ImageIcon getIcon(URL path, int size) {
        try {
            ImageIcon imageIcon = new ImageIcon(path);
            Image image = imageIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
            return new ImageIcon(image, path.toString());
        } catch (Exception e) {
            log.error("Unable to fetch icon for {}", path);
            return null;
        }
    }
}
