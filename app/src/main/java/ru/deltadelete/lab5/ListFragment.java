package ru.deltadelete.lab5;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Locale;

import ru.deltadelete.lab5.databinding.FragmentListBinding;

public class ListFragment extends Fragment {


    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private FragmentListBinding binding;

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
        // TODO: не обновлять при возвращении с другого фрагмента
        initList();
        initButtonAdd();
    }

    private void initButtonAdd() {
        binding.buttonAdd.setOnClickListener(v -> {
            Activity activity = getActivity();
            if (activity == null) return;
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_view_main, new NewTownFragment(), "fragment_new_town")
                    .addToBackStack("fragment_new_town")
                    .commit();
        });
    }

    private void initList() {
        var towns = new ArrayList<Town>();
        var faker = new Faker(getCurrentLocale());
        for (int i = 0; i < 50; i++) {
            towns.add(Town.fromFaker(faker));
        }
        var adapter = new TownAdapter(getContext(), towns);
        adapter.setOnItemClickListener((view1, item, position) -> {
            Toast.makeText(getContext(), item.getName(), Toast.LENGTH_SHORT).show();
        });
        adapter.setOnLongItemClickListener((view1, item, position) -> {
            view1.animate()
                    .translationXBy(view1.getWidth())
                    .setDuration(250)
                    .withEndAction(() -> {
                        adapter.remove(item);
                        view1.animate().translationXBy(view1.getWidth()).setDuration(0).start();
                    })
                    .start();
            return true;
        });

        binding.listViewFl.setAdapter(adapter);
    }
}