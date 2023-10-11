package ru.deltadelete.lab7.ui.list_fragment;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

import ru.deltadelete.lab7.MyViewModel;
import ru.deltadelete.lab7.ui.dialogs.AlertDialogFragment;
import ru.deltadelete.lab7.ui.new_town_fragment.NewTownFragment;
import ru.deltadelete.lab7.R;
import ru.deltadelete.lab7.models.Town;
import ru.deltadelete.lab7.adapter.TownAdapter;
import ru.deltadelete.lab7.databinding.FragmentListBinding;
import ru.deltadelete.lab7.ui.town_details_fragment.TownDetailsFragment;

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

    public MyViewModel viewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);
        initList();
        initButtonAdd();
        initFakeLoading();
    }

    private void initFakeLoading() {
        FragmentActivity activity = requireActivity();
        binding.listViewFl.setVisibility(View.INVISIBLE);
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            activity.runOnUiThread(() -> {
                binding.progressIndicator.setVisibility(View.GONE);
                binding.listViewFl.setVisibility(View.VISIBLE);
            });
        });
        thread.start();
    }

    private void initButtonAdd() {
        binding.buttonAdd.setOnClickListener(v -> {
            FragmentActivity activity = requireActivity();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_view_main, new NewTownFragment(), "fragment_new_town")
                    .addToBackStack("fragment_new_town")
                    .commit();
        });
    }

//    TownAdapter adapter;

    private void initList() {
        TownAdapter adapter = viewModel.getAdapter();
        if (adapter != null) {
            binding.listViewFl.setAdapter(adapter);
        }
        Context context = requireContext();
        adapter = new TownAdapter(context, viewModel.getTowns());
        adapter.setOnItemClickListener((v, item, position) -> {
            Toast.makeText(context, item.getName(), Toast.LENGTH_SHORT).show();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .add(
                            R.id.fragment_view_main,
                            TownDetailsFragment.newInstance(item),
                            "fragment_town_details")
                    .addSharedElement(v, "townContainer")
                    .addSharedElement(v.findViewById(R.id.countryFlag), "countryFlag")
                    .addSharedElement(v.findViewById(R.id.townName), "townName")
                    .addSharedElement(v.findViewById(R.id.townCountry), "townCountry")
                    .addToBackStack("fragment_town_details")
                    .commit();
        });
        adapter.setOnLongItemClickListener(this::onLongItemClick);
        viewModel.setAdapter(adapter);
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
                requireActivity().getSupportFragmentManager(),
                "delete_item_dialog"
        );

        return true;
    }

    private void animatedRemove(Context context, View v, Town item) {
        MaterialCardView card = v.findViewById(R.id.card);
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
                    viewModel.getAdapter().remove(item);
                    v.animate().translationXBy(v.getWidth()).setDuration(0).start();
                    v.setBackgroundTintList(initialColor);
                })
                .start();
    }
}