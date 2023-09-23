package ru.deltadelete.lab5.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.ThemeUtils;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import ru.deltadelete.lab5.R;

public class AlertDialogFragment extends DialogFragment {
    @DrawableRes
    private int icon;
    @StringRes
    private int title;
    @StringRes
    private int positiveButtonText;
    @StringRes
    private int negativeButtonText;

    private DialogInterface.OnClickListener positiveClick;
    private DialogInterface.OnClickListener negativeClick;

    public static final String TITLE_ARG = "title";
    public static final String ICON_ARG = "icon";
    public static final String POSITIVE_BUTTON_ARG = "positive_button_text";
    public static final String NEGATIVE_BUTTON_ARG = "negative_button_text";

    public AlertDialogFragment(DialogInterface.OnClickListener positiveClick, DialogInterface.OnClickListener negativeClick) {
        this.positiveClick = positiveClick;
        this.negativeClick = negativeClick;
    }

    public static AlertDialogFragment newInstance(@StringRes int title,
                                                  @StringRes int positiveButtonText,
                                                  @StringRes int negativeButtonText,
                                                  @DrawableRes int icon,
                                                  DialogInterface.OnClickListener positiveClick,
                                                  DialogInterface.OnClickListener negativeClick
    ) {
        AlertDialogFragment frag = new AlertDialogFragment(
                positiveClick,
                negativeClick
        );
        Bundle args = new Bundle();
        args.putInt(TITLE_ARG, title);
        args.putInt(ICON_ARG, icon);
        args.putInt(POSITIVE_BUTTON_ARG, positiveButtonText);
        args.putInt(NEGATIVE_BUTTON_ARG, negativeButtonText);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.icon = getArguments().getInt(ICON_ARG);
            this.title = getArguments().getInt(TITLE_ARG);
            this.positiveButtonText = getArguments().getInt(POSITIVE_BUTTON_ARG);
            this.negativeButtonText = getArguments().getInt(NEGATIVE_BUTTON_ARG);
        }
//        setStyle(
//                DialogFragment.STYLE_NORMAL,
//                com.google.android.material.R.style.MaterialAlertDialog_Material3
//        );
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new MaterialAlertDialogBuilder(
                requireContext(),
                R.style.CustomMaterialAlertDialog
        )
                .setIcon(icon)
                .setTitle(title)
//                .setIconAttribute(com.google.android.material.R.attr.materialAlertDialogTitleIconStyle)
                .setPositiveButton(positiveButtonText, positiveClick)
                .setNegativeButton(negativeButtonText, negativeClick)
                .create();
    }
}
