package ru.deltadelete.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.UiModeManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.color.DynamicColors;

import org.apache.commons.lang3.LocaleUtils;

import java.util.Locale;

import ru.deltadelete.lab5.databinding.ActivityMainBinding;
import ru.deltadelete.lab5.helpers.SharedPreferencesHelper;
import ru.deltadelete.lab5.ui.settings_fragment.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DynamicColors.applyToActivityIfAvailable(this);
        Fresco.initialize(this); // Подгрузчик картинок от Facebook

        setLocale();
        setThemeMode();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        var root = binding.getRoot();

        setContentView(root);
        setSupportActionBar(binding.toolbarMain);
    }

    private void setThemeMode() {
        boolean darkModeEnabled = SharedPreferencesHelper.getBool(this, "DARK_THEME");
        UiModeManager ui = (UiModeManager) this.getSystemService(UI_MODE_SERVICE);
        if (darkModeEnabled) {
            ui.setNightMode(UiModeManager.MODE_NIGHT_YES);
        }
        else {
            ui.setNightMode(UiModeManager.MODE_NIGHT_NO);
        }
    }

    private void setLocale() {
        String lang = SharedPreferencesHelper.getString(getApplicationContext(), "LANG");

        Locale locale = new Locale(lang);
        Resources res = getResources();
        var conf = res.getConfiguration();
        conf.setLocale(locale);
        Locale.setDefault(locale);
        createConfigurationContext(conf);
        res.updateConfiguration(conf, res.getDisplayMetrics());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.settings_menu_item) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_view_main, SettingsFragment.newInstance())
                    .addToBackStack("settings_fragment")
                    .commit();
            return true;
        }
        return false;
    }
}