package ru.deltadelete.lab5.ui.settings_fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.apache.commons.lang3.LocaleUtils;

import java.util.List;
import java.util.Locale;

import ru.deltadelete.lab5.adapter.LocaleAdapter;
import ru.deltadelete.lab5.databinding.FragmentSettingsBinding;
import ru.deltadelete.lab5.helpers.SharedPreferencesHelper;
import timber.log.Timber;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;
    private LocaleAdapter adapter;

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

        List<Locale> locales = List.of(
                LocaleUtils.toLocale("ru_RU"),
                LocaleUtils.toLocale("en_US")
        );
        adapter = new LocaleAdapter(context,
                locales);
        binding.spinnerLanguage.setAdapter(adapter);
        binding.spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                langCode = adapter.getItem(position).getLanguage();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                langCode = Locale.getDefault().getLanguage();
            }
        });
        String settingsLang = SharedPreferencesHelper.getString(context, "LANG");
        var locale = new Locale(settingsLang);
        binding.spinnerLanguage.setText(locale.getDisplayLanguage(), false);
    }

    private String langCode = "";
    private void onSave(View v) {
        Context context = requireActivity().getApplicationContext();
        String username = binding.editTextUsername.getText().toString();
        boolean darkTheme = binding.switchTheme.isChecked();
        int townAmount = binding.numberPickerAmountOfGeneratedTowns.getValue();
        String lang = binding.spinnerLanguage.getText().toString();
        boolean loadFlags = binding.switchLoadFlags.isChecked();

        Timber.tag("INFO").i("Prefs: %s, %b, %d, %s, %b", username, darkTheme, townAmount, lang, loadFlags);

        SharedPreferencesHelper.putString(context, "USERNAME", username);
        SharedPreferencesHelper.putBool(context, "DARK_THEME", darkTheme);
        SharedPreferencesHelper.putInt(context, "TOWN_AMOUNT", townAmount);
        SharedPreferencesHelper.putString(context, "LANG", lang);
        SharedPreferencesHelper.putBool(context, "LOAD_FLAGS", loadFlags);

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
