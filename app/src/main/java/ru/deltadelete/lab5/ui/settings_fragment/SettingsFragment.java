package ru.deltadelete.lab5.ui.settings_fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import org.apache.commons.lang3.LocaleUtils;

import java.util.List;
import java.util.Locale;

import ru.deltadelete.lab5.R;
import ru.deltadelete.lab5.adapter.LocaleAdapter;
import ru.deltadelete.lab5.databinding.FragmentSettingsBinding;
import ru.deltadelete.lab5.helpers.SharedPreferencesHelper;
import timber.log.Timber;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;
    private LocaleAdapter adapter;
    private SettingsViewModel viewModel;
    private static final List<Locale> locales = List.of(
            LocaleUtils.toLocale("ru_RU"),
            LocaleUtils.toLocale("en_US")
    );

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
        viewModel = new ViewModelProvider(requireActivity()).get(SettingsViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logPrefs();
        initViews();
        initSaveButton();
        initOnChangeActions();
    }

    private void initSaveButton() {
        binding.buttonSave.setOnClickListener(this::onSave);
    }

    private void initViews() {
        Context context = requireContext();
        binding.editTextUsername.setText(viewModel.getUsername());

        binding.switchTheme.setChecked(viewModel.isDarkTheme());
        binding.switchLoadFlags.setChecked(viewModel.isLoadFlags());

        binding.numberPickerAmountOfGeneratedTowns.setMinValue(0);
        binding.numberPickerAmountOfGeneratedTowns.setMaxValue(100);
        binding.numberPickerAmountOfGeneratedTowns.setValue(viewModel.getTownAmount());

        adapter = new LocaleAdapter(context,
                locales);
        binding.spinnerLanguage.setAdapter(adapter);
        Locale lang = viewModel.getLanguage();
        String text = lang.getDisplayLanguage();
        String displayText = text.substring(0, 1).toUpperCase() + text.substring(1);
        binding.spinnerLanguage.setText(displayText, false);
    }

    private void onSave(View v) {
        Toast.makeText(
                        requireContext(),
                        R.string.settings_changes_toast,
                        Toast.LENGTH_LONG
                )
                .show();
    }

    private void initOnChangeActions() {
        binding.spinnerLanguage.setOnItemClickListener((parent, view, position, id) -> {
            Locale item = adapter.getItem(position);
            viewModel.setLanguage(item);
            String text = item.getDisplayLanguage();
            String displayText = text.substring(0, 1).toUpperCase() + text.substring(1);
            binding.spinnerLanguage.setText(displayText, false);
        });

        binding.switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.setDarkTheme(isChecked);
        });
        binding.switchLoadFlags.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.setLoadFlags(isChecked);
        });

        binding.numberPickerAmountOfGeneratedTowns.setOnValueChangedListener((picker, oldVal, newVal) -> {
            viewModel.setTownAmount(newVal);
        });

        binding.editTextUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.setUsername(s.toString());
            }
        });
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
