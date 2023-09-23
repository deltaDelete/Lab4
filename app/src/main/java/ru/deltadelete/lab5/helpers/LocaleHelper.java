package ru.deltadelete.lab5.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ru.deltadelete.lab5.models.Town;

public class LocaleHelper {
    private static List<Locale> locales;
    public static List<Locale> getLocales() {
        if (locales != null) return locales;
        var availableLocales = Locale.getAvailableLocales();
        var locales = new ArrayList<Locale>();
        for (var locale : availableLocales) {
            var country = locale.getDisplayCountry();
            if (country.isEmpty()) continue;
            var code = locale.getCountry();
            if (code.length() != 2) continue;
            locales.add(locale);
        }
        locales.sort(
                (o1, o2) -> o1.getDisplayCountry().compareTo(o2.getDisplayCountry().toLowerCase())
        );
        LocaleHelper.locales = locales;
        return LocaleHelper.locales;
    }
}
