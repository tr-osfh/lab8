package app.logic;

import app.logic.locales.Russian;
import app.logic.locales.Spanish;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class Localizer {
    private static final Map<Locale, Map<String, String>> RESOURCES = new HashMap<>();
    private Locale currentLocale;

    static {;

        RESOURCES.put(new Locale("ru"), Russian.getMap());

        RESOURCES.put(new Locale("es", "DOM"), Spanish.getMap());
    }

    public Localizer(Locale defaultLocale) {
        this.currentLocale = defaultLocale;
    }

    public void setLocale(Locale locale) {
        this.currentLocale = locale;
    }

    public String getKeyString(String key) {
        return RESOURCES.get(currentLocale).getOrDefault(key, "[" + key + "]");
    }

    public String getDate(LocalDate date) {
        if (date == null) return "null";
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
                .withLocale(currentLocale);
        return date.format(formatter);
    }

    public String getDate(LocalDateTime date) {
        if (date == null) return "null";
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
                .withLocale(currentLocale);
        return date.format(formatter);
    }
}