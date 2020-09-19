package org.superhelt.raidplanner2.client.gui;

import javax.swing.*;
import java.net.URL;

public class IconUtil {

    public static ImageIcon getDeleteIcon() {
        URL url = IconUtil.class.getResource("/delete.png");
        return new ImageIcon(url, "Delete");
    }

    public static ImageIcon getAddIcon() {
        URL url = IconUtil.class.getResource("/add.jpg");
        return new ImageIcon(url, "Add");

    }
}
