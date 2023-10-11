package ru.deltadelete.lab8.ui.town_details_fragment;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.deltadelete.lab8.MyViewModel;
import ru.deltadelete.lab8.databinding.FragmentTownDetailsBinding;
import ru.deltadelete.lab8.models.Town;

public class TownDetailsFragment extends Fragment {

    public TownDetailsFragment() {
        // Required empty public constructor
    }

    public static final String TOWN_ARG = "TOWN_OBJECT";
    private Town town;
    private FragmentTownDetailsBinding binding;

    public static TownDetailsFragment newInstance(Town town) {
        TownDetailsFragment fragment = new TownDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(TOWN_ARG, town);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.town = (Town) getArguments().getSerializable(TOWN_ARG);
        }
        var animation = TransitionInflater.from(requireContext()).inflateTransition(
                android.R.transition.move
        );
        setSharedElementEnterTransition(animation);
        setSharedElementReturnTransition(animation);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTownDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public MyViewModel viewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDetails();
    }

    private void initDetails() {
        binding.countryFlag.setImageURI(town.getFlagUrl());
        binding.townName.setText(town.getName());
        binding.townCountry.setText(town.getCountry());
        binding.townDescription.setText(town.getDescription());
    }
}
