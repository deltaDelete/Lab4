package ru.deltadelete.lab5.ui.settings_fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import ru.deltadelete.lab5.databinding.FragmentSettingsBinding;
import ru.deltadelete.lab5.helpers.SharedPreferencesHelper;
import timber.log.Timber;

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
        logPrefs();
        initViews();
        initSaveButton();
    }

    private void initSaveButton() {
        binding.buttonSave.setOnClickListener(this::onSave);
    }

    private void initViews() {
        Context context = requireContext();
        binding.editTextUsername.setText(SharedPreferencesHelper.getString(context, "USERNAME"));

        binding.switchTheme.setChecked(SharedPreferencesHelper.getBool(context, "DARK_THEME"));

        binding.numberPickerAmountOfGeneratedTowns.setMinValue(0);
        binding.numberPickerAmountOfGeneratedTowns.setMaxValue(100);
        binding.numberPickerAmountOfGeneratedTowns.setValue(SharedPreferencesHelper.getInt(context, "TOWN_AMOUNT"));

        List<String> locales = List.of(getResources().getAssets().getLocales());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_dropdown_item_1line,
                locales);
        binding.spinnerLanguage.setAdapter(adapter);
        String settingsLang = SharedPreferencesHelper.getString(context, "LANG");
        binding.spinnerLanguage.setText(settingsLang, false);
    }

    private void onSave(View v) {
        Context context = requireActivity().getApplicationContext();
        String username = binding.editTextUsername.getText().toString();
        boolean darkTheme = binding.switchTheme.isChecked();
        int townAmount = binding.numberPickerAmountOfGeneratedTowns.getValue();
        String lang = binding.spinnerLanguage.getText().toString();

        Timber.tag("INFO").i("Prefs: %s, %b, %d, %s", username, darkTheme, townAmount, lang);

        SharedPreferencesHelper.putString(context, "USERNAME", username);
        SharedPreferencesHelper.putBool(context, "DARK_THEME", darkTheme);
        SharedPreferencesHelper.putInt(context, "TOWN_AMOUNT", townAmount);
        SharedPreferencesHelper.putString(context, "LANG", lang);

        logPrefs();
    }

    private void logPrefs() {
        Context context = requireActivity().getApplicationContext();

        String username = SharedPreferencesHelper.getString(context, "USERNAME");
        boolean darkTheme = SharedPreferencesHelper.getBool(context, "DARK_THEME");
        int townAmount = SharedPreferencesHelper.getInt(context, "TOWN_AMOUNT");
        String lang = SharedPreferencesHelper.getString(context, "LANG");

        Timber.tag("INFO").i("Loaded Prefs: %s, %b, %d, %s", username, darkTheme, townAmount, lang);
    }
}
