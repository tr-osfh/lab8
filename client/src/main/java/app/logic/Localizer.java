package app.logic;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class Localizer {
    private ResourceBundle resourceBundle;

    public Localizer(ResourceBundle rb){
        this.resourceBundle = rb;
    }

    public void setResourceBundle(ResourceBundle rb){
        this.resourceBundle = rb;
    }

    public ResourceBundle getResourceBundle(){
        return resourceBundle;
    }

    public String getDate(LocalDate date) {
        if (date == null) return "null";
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(resourceBundle.getLocale());
        return date.format(formatter);
    }

    public String getDate(LocalDateTime date) {
        if (date == null) return "null";
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(resourceBundle.getLocale());
        return date.format(formatter);
    }

    public String getKeyString(String key) {
        return resourceBundle.getString(key);
    }


}
