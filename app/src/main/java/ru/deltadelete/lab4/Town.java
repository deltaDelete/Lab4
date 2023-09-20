package ru.deltadelete.lab4;

import androidx.annotation.DrawableRes;

import com.github.javafaker.Faker;

public class Town {
    private int id;
    private String name;
    private String country;
    @DrawableRes
    private int flag;
    private String flagUrl;

    public Town(String name, String country, String flagUrl) {
        this.name = name;
        this.country = country;
        this.flagUrl = flagUrl;
    }

    public static Town fromFaker(Faker faker) {
        return new Town(
                faker.address().city(),
                faker.address().country(),
                String.format("https://flagcdn.com/w320/%s.png", faker.country().countryCode2())
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
}