package ru.deltadelete.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.color.DynamicColors;

import ru.deltadelete.lab4.databinding.ActivityMainBinding;

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
}