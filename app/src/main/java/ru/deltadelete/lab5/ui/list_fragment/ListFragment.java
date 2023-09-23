package ru.deltadelete.lab5.ui.list_fragment;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Locale;

import ru.deltadelete.lab5.ui.dialogs.AlertDialogFragment;
import ru.deltadelete.lab5.ui.new_town_fragment.NewTownFragment;
import ru.deltadelete.lab5.R;
import ru.deltadelete.lab5.models.Town;
import ru.deltadelete.lab5.adapter.TownAdapter;
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
        initList();
        initButtonAdd();
    }

    private void initButtonAdd() {
        binding.buttonAdd.setOnClickListener(v -> {
            Activity activity = getActivity();
            if (activity == null) return;
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_view_main, new NewTownFragment(adapter), "fragment_new_town")
                    .addToBackStack("fragment_new_town")
                    .commit();
        });
    }

    TownAdapter adapter;
    private void initList() {
        if (adapter != null) {
            binding.listViewFl.setAdapter(adapter);
            return;
        }

        var towns = new ArrayList<Town>();
        var faker = new Faker(getCurrentLocale());
        for (int i = 0; i < 5; i++) {
            towns.add(Town.fromFaker(faker));
        }

        Context context = requireContext();

        adapter = new TownAdapter(context, towns);
        adapter.setOnItemClickListener((v, item, position) -> {
            Toast.makeText(context, item.getName(), Toast.LENGTH_SHORT).show();
        });
        adapter.setOnLongItemClickListener(this::onLongItemClick);

        binding.listViewFl.setAdapter(adapter);
    }

    private boolean onLongItemClick(View v, Town item, int position) {

        var dialog = AlertDialogFragment.newInstance(
                R.string.delete_dialog_title,
                R.string.delete_dialog_positive,
                R.string.delete_dialog_negative,
                R.drawable.baseline_delete_32,
                (d, i) -> animatedRemove(requireContext(), v, item),
                (d, i) -> d.cancel(),
                item);
        dialog.show(
                getActivity().getSupportFragmentManager(),
                "delete_item_dialog"
        );

        return true;
    }

    private void animatedRemove(Context context, View v, Town item) {
        CardView card = v.findViewById(R.id.card);
        final ColorStateList initialColor = card.getCardBackgroundColor();
        final ColorStateList finalColor = ColorStateList.valueOf(
                context.getColor(
                        com.google.android.material.R.color.design_default_color_error
                )
        );
        v.animate()
                .translationXBy(v.getWidth())
                .setDuration(250)
                .withStartAction(() -> {
                    card.setBackgroundTintList(finalColor);
                })
                .withEndAction(() -> {
                    adapter.remove(item);
                    v.animate().translationXBy(v.getWidth()).setDuration(0).start();
                    v.setBackgroundTintList(initialColor);
                })
                .start();
    }
}