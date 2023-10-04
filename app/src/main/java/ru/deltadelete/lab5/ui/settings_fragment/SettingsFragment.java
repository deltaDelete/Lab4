package ru.deltadelete.lab5.ui.settings_fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ru.deltadelete.lab5.MyViewModel;
import ru.deltadelete.lab5.databinding.FragmentSettingsBinding;
import ru.deltadelete.lab5.databinding.FragmentTownDetailsBinding;
import ru.deltadelete.lab5.helpers.SharedPreferencesHelper;
import ru.deltadelete.lab5.models.Town;
import ru.deltadelete.lab5.ui.town_details_fragment.TownDetailsFragment;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        Context context = requireContext();
        binding.editTextUsername.setText(SharedPreferencesHelper.getString(context, "USERNAME"));
        binding.editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SharedPreferencesHelper.putString(context, "USERNAME", s.toString());
            }
        });

        binding.switchTheme.setChecked(SharedPreferencesHelper.getBool(context, "DARK_THEME"));

        binding.numberPickerAmountOfGeneratedTowns.setMinValue(0);
        binding.numberPickerAmountOfGeneratedTowns.setMaxValue(100);
        binding.numberPickerAmountOfGeneratedTowns.setValue(SharedPreferencesHelper.getInt(context, "TOWN_AMOUNT"));

        String[] locales = getResources().getAssets().getLocales();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                com.google.android.material.R.layout.m3_auto_complete_simple_item,
                locales);
        binding.spinnerLanguage.setAdapter(adapter);
        binding.spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                var selected = adapter.getItem(position);
                SharedPreferencesHelper.putString(context, "LANG", selected);
//                Stream.of(locales)
//                        .filter(it -> it.equals(selected))
//                        .findFirst().ifPresent(it ->
//                                SharedPreferencesHelper.putString(context, "LANG", it)
//                        );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        var settingsLang = SharedPreferencesHelper.getString(context, "LANG");
        binding.spinnerLanguage.setSelection(
                !settingsLang.isBlank() ? adapter.getPosition(settingsLang) : 0
        );
    }
}
