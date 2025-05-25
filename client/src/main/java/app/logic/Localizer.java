package app.logic;

import app.logic.locales.Greek;
import app.logic.locales.Russian;
import app.logic.locales.Slovenian;
import app.logic.locales.Spanish;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class Localizer {
    private static final Map<Locale, Map<String, String>> resources = new HashMap<>();
    private Locale currentLocale;

    static {

        resources.put(new Locale("ru"), Russian.getMap());

        resources.put(new Locale("es", "DO"), Spanish.getMap());

        resources.put(new Locale("el"), Greek.getMap());

        resources.put(new Locale("sl"), Slovenian.getMap());
    }

    public Localizer(Locale defaultLocale) {
        this.currentLocale = defaultLocale;
    }

    public void setLocale(Locale locale) {
        this.currentLocale = locale;
    }

    public String getKeyString(String key) {
        return resources.get(currentLocale).getOrDefault(key, "[" + key + "]");
    }

    public String getDate(LocalDateTime date) {
        if (date == null) return "None";
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(currentLocale);
        return date.format(formatter);
    }
}