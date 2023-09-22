package ru.deltadelete.lab5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.deltadelete.lab5.databinding.FragmentNewTownBinding;

public class NewTownFragment extends Fragment {
    private FragmentNewTownBinding binding;
    public NewTownFragment() { }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewTownBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}