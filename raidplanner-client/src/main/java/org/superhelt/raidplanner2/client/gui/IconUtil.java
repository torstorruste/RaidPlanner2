package org.superhelt.raidplanner2.client.gui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.om.CharacterClass;

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
}
