package ru.deltadelete.lab5.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.android.material.card.MaterialCardView;

import ru.deltadelete.lab5.R;
import ru.deltadelete.lab5.models.Town;

import java.util.List;

public class TownAdapter extends ArrayAdapter<Town> {

    private final Context context;
    private LayoutInflater inflater;
    @LayoutRes
    private int layout;
    private List<Town> items;
    private OnItemClickListener onItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public OnLongClickListener getOnLongItemClickListener() {
        return onLongItemClickListener;
    }

    private OnLongClickListener onLongItemClickListener;

    public interface OnItemClickListener {
        void onClick(View view, Town item, int position);
    }

    public interface OnLongClickListener {
        boolean onLongClick(View view, Town item, int position);
    }

    public TownAdapter(Context context, List<Town> items) {
        super(context, R.layout.town_item, items);
        this.context = context;
        this.items = items;
        this.layout = R.layout.town_item;
        this.inflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnLongItemClickListener(OnLongClickListener listener) {
        this.onLongItemClickListener = listener;
    }

    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        if (convertView == null)
            view = inflater.inflate(this.layout, parent, false);
        else
            view = convertView;

        TextView townName = view.findViewById(R.id.townName);
        TextView townCountry = view.findViewById(R.id.townCountry);
        SimpleDraweeView flag = view.findViewById(R.id.countryFlag);
        MaterialCardView card = view.findViewById(R.id.card);

        var town = items.get(position);

        townName.setText(town.getName());
        townCountry.setText(town.getCountry());
        var req = ImageRequestBuilder.newBuilderWithSource(Uri.parse(town.getFlagUrl()))
                        .build();
        Log.d("IMAGE", String.valueOf(req.getPreferredWidth()));
        Log.d("IMAGE", String.valueOf(req.getPreferredHeight()));
        flag.setImageURI(town.getFlagUrl());
        card.setOnClickListener((v) -> {
            onItemClickListener.onClick(view, town, position);
        });
        card.setOnLongClickListener(v -> onLongItemClickListener.onLongClick(view, town, position));

        return view;
    }

    @Override
    public synchronized void remove(@Nullable Town object) {
        items.remove(object);
        super.remove(object);
    }

    @Override
    public synchronized void add(@Nullable Town object) {
//        super.add(object);
        items.add(object);
    }
}