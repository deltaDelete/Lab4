package ru.deltadelete.lab7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.color.DynamicColors;

import java.util.Locale;

import ru.deltadelete.lab7.databinding.ActivityMainBinding;
import ru.deltadelete.lab7.ui.settings_fragment.SettingsFragment;
import ru.deltadelete.lab7.ui.settings_fragment.SettingsViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SettingsViewModel settingsVm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DynamicColors.applyToActivityIfAvailable(this);
        Fresco.initialize(this); // Подгрузчик картинок от Facebook
        settingsVm = new ViewModelProvider(this).get(SettingsViewModel.class);
        setLocale();
        applyThemeMode();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        var root = binding.getRoot();

        setContentView(root);
        setSupportActionBar(binding.toolbarMain);
    }

    private void applyThemeMode() {
        boolean darkModeEnabled = settingsVm.isDarkTheme();
        if (darkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    private void setLocale() {
        Locale locale = settingsVm.getLanguage();
        if (locale.getDisplayLanguage().equals("")) {
            return;
        }
        Resources res = getResources();
        var conf = res.getConfiguration();
        conf.setLocale(locale);
        Locale.setDefault(locale);
        createConfigurationContext(conf);
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