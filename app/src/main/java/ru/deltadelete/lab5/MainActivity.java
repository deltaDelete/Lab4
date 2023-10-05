package ru.deltadelete.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.color.DynamicColors;

import ru.deltadelete.lab5.databinding.ActivityMainBinding;
import ru.deltadelete.lab5.ui.settings_fragment.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DynamicColors.applyToActivityIfAvailable(this);
        Fresco.initialize(this); // Подгрузчик картинок от Facebook

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        var root = binding.getRoot();

        setContentView(root);
        setSupportActionBar(binding.toolbarMain);

//        if (savedInstanceState == null) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .add(R.id.fragment_view_main, ListFragment.newInstance())
//                    // .addToBackStack("list_fragment_transaction") // Зачем возвращаться на пустое?
//                    .commit();
//        }
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