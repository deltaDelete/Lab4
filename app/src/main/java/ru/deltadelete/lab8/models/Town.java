package ru.deltadelete.lab8.models;

import androidx.annotation.DrawableRes;

import com.github.javafaker.Faker;

import java.io.Serializable;
import java.util.Locale;

import ru.deltadelete.lab8.helpers.LocaleHelper;

public class Town implements Serializable {
    private int id;
    private String name;
    private String country;

    private String description;
    @DrawableRes
    private int flag;
    private String flagUrl;

    public Town(String name, String country, String flagUrl, String description) {
        this.name = name;
        this.country = country;
        this.flagUrl = flagUrl;
        this.description = description;
    }

    public Town(String name, String country, String flagUrl) {
        this.name = name;
        this.country = country;
        this.flagUrl = flagUrl;
        this.description = "";
    }

    public static Town fromFaker(Faker faker) {
        var i = faker.random().nextInt(LocaleHelper.getLocales().size());
        Locale locale = LocaleHelper.getLocales().get(i);
        return new Town(
                faker.address().city(),
                locale.getDisplayCountry(),
                String.format("https://flagcdn.com/w320/%s.png", locale.getCountry().toLowerCase()),
                faker.lorem().paragraph(10)
        );
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }

    public String getDescription() {
        return this.description;
    }
}