package ru.deltadelete.lab8;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.github.javafaker.Faker;

import java.util.ArrayList;

import ru.deltadelete.lab8.adapter.TownAdapter;
import ru.deltadelete.lab8.helpers.LocaleHelper;
import ru.deltadelete.lab8.helpers.SharedPreferencesHelper;
import ru.deltadelete.lab8.models.Town;

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
        var context = getApplication().getApplicationContext();
        var amount = SharedPreferencesHelper.getInt(context, "TOWN_AMOUNT");
        var faker = new Faker(LocaleHelper.getCurrentLocale());
        for (int i = 0; i < amount; i++) {
            towns.add(Town.fromFaker(faker));
        }
    }

    public ArrayList<Town> getTowns() {
        return towns;
    }

    public void setAdapter(TownAdapter adapter) {
        this.adapter = adapter;
    }
}
