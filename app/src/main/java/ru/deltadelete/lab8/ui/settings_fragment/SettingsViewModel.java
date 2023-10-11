package ru.deltadelete.lab8.ui.settings_fragment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.AndroidViewModel;

import org.apache.commons.lang3.LocaleUtils;

import java.util.Locale;

import ru.deltadelete.lab8.helpers.SharedPreferencesHelper;

public class SettingsViewModel extends AndroidViewModel {
    private Locale language;
    private String username;
    private Boolean loadFlags;
    private Boolean darkTheme;
    private Integer townAmount;

    public static final String LANGUAGE_KEY = "LANG";
    public static final String USERNAME_KEY = "USERNAME";
    public static final String TOWN_AMOUNT_KEY = "TOWN_AMOUNT";
    public static final String DARK_THEME_KEY = "DARK_THEME";
    public static final String LOAD_FLAGS_KEY = "LOAD_FLAGS";

    public SettingsViewModel(@NonNull Application application) {
        super(application);
    }

    public Locale getLanguage() {
        if (language == null) {
            String langCode = SharedPreferencesHelper.getString(
                    getApplication().getApplicationContext(),
                    LANGUAGE_KEY
            );
            language = LocaleUtils.toLocale(langCode);
        }
        return language;
    }

    public void setLanguage(Locale language) {
        this.language = language;
        SharedPreferencesHelper.putString(
                getApplication().getApplicationContext(),
                LANGUAGE_KEY,
                language.toString()
        );
    }

    public String getUsername() {
        if (username == null) {
            username = SharedPreferencesHelper.getString(
                    getApplication().getApplicationContext(),
                    USERNAME_KEY
            );
        }
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        SharedPreferencesHelper.putString(
                getApplication().getApplicationContext(),
                USERNAME_KEY,
                username
        );
    }

    public boolean isLoadFlags() {
        if (loadFlags == null) {
            loadFlags = SharedPreferencesHelper.getBool(
                    getApplication().getApplicationContext(),
                    LOAD_FLAGS_KEY
            );
        }
        return loadFlags;
    }

    public void setLoadFlags(boolean loadFlags) {
        this.loadFlags = loadFlags;
        SharedPreferencesHelper.putBool(
                getApplication().getApplicationContext(),
                LOAD_FLAGS_KEY,
                loadFlags
        );
    }

    public boolean isDarkTheme() {
        if (darkTheme == null) {
            darkTheme = SharedPreferencesHelper.getBool(
                    getApplication().getApplicationContext(),
                    DARK_THEME_KEY
            );
        }
        return darkTheme;
    }

    public void setDarkTheme(boolean darkTheme) {
        this.darkTheme = darkTheme;
        SharedPreferencesHelper.putBool(
                getApplication().getApplicationContext(),
                DARK_THEME_KEY,
                darkTheme
        );
        if (darkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public int getTownAmount() {
        if (townAmount == null) {
            townAmount = SharedPreferencesHelper.getInt(
                    getApplication().getApplicationContext(),
                    TOWN_AMOUNT_KEY
            );
        }
        return townAmount;
    }

    public void setTownAmount(int townAmount) {
        this.townAmount = townAmount;
        SharedPreferencesHelper.putInt(
                getApplication().getApplicationContext(),
                TOWN_AMOUNT_KEY,
                townAmount
        );
    }

}
