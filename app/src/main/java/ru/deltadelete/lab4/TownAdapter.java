package ru.deltadelete.lab4;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;

import ru.deltadelete.lab4.R;
import ru.deltadelete.lab4.Town;

import java.util.List;

public class TownAdapter extends ArrayAdapter<Town> {

    private final Context context;
    private LayoutInflater inflater;
    @LayoutRes
    private int layout;
    private List<Town> items;

    public TownAdapter(Context context, List<Town> items) {
        super(context, R.layout.town_item, items);
        this.context = context;
        this.items = items;
        this.layout = R.layout.town_item;
        this.inflater = LayoutInflater.from(context);
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

        var town = items.get(position);

        townName.setText(town.getName());
        townCountry.setText(town.getCountry());
        flag.setImageURI(town.getFlagUrl());

        return view;
    }
}