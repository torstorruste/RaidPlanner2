package org.superhelt.raidplanner2.client.gui.raidAdmin;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

    private static final SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dt.parse(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if(value==null) return "";
        return dt.format(((Calendar)value).getTime());
    }
}
