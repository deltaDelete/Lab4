package ru.deltadelete.lab5;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Locale;

import ru.deltadelete.lab5.adapter.TownAdapter;
import ru.deltadelete.lab5.helpers.LocaleHelper;
import ru.deltadelete.lab5.models.Town;

public class MyViewModel extends AndroidViewModel {
    private TownAdapter adapter;
    private ArrayList<Town> towns;

    public MyViewModel(Application app) {
        super(app);
        initTowns();
    }

    public TownAdapter getAdapter() {
        return adapter;
    }
    private void initTowns() {
        towns = new ArrayList<Town>();
        var faker = new Faker(LocaleHelper.getCurrentLocale());
        for (int i = 0; i < 5; i++) {
            towns.add(Town.fromFaker(faker));
        }
    }

    public ArrayList<Town> getTowns() {
        return towns;
    }

    public void setAdapter(TownAdapter adapter) {
        this.adapter = adapter;
    }

//    public void setTowns(ArrayList<Town> towns) {
//        this.towns = towns;
//    }
}
