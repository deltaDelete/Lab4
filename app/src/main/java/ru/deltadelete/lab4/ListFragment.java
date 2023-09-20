package ru.deltadelete.lab4;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Locale;

import ru.deltadelete.lab4.databinding.FragmentListBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    private FragmentListBinding binding;

    public ListFragment() {
        // Required empty public constructor
    }

    @NonNull
    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private Locale getCurrentLocale() {
        return getResources().getConfiguration().getLocales().get(0);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        var towns = new ArrayList<Town>();
        var faker = new Faker(getCurrentLocale());
        for (int i = 0; i < 50; i++) {
            towns.add(Town.fromFaker(faker));
        }
        var adapter = new TownAdapter(getContext(), towns);

        binding.listViewFl.setAdapter(adapter);
    }
}