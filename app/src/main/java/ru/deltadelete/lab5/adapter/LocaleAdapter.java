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

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.android.material.card.MaterialCardView;

import java.util.List;
import java.util.Locale;

import ru.deltadelete.lab5.R;
import ru.deltadelete.lab5.models.Town;

public class LocaleAdapter extends ArrayAdapter<Locale> {

    private final Context context;
    private LayoutInflater inflater;
    @LayoutRes
    private int layout;
    private List<Locale> items;
    private TownAdapter.OnItemClickListener onItemClickListener;

    public TownAdapter.OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public TownAdapter.OnLongClickListener getOnLongItemClickListener() {
        return onLongItemClickListener;
    }

    private TownAdapter.OnLongClickListener onLongItemClickListener;

    public LocaleAdapter(Context context, List<Locale> items) {
        super(context, android.R.layout.simple_dropdown_item_1line, items);
        this.context = context;
        this.items = items;
        this.layout = android.R.layout.simple_dropdown_item_1line;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        if (convertView == null)
            view = inflater.inflate(this.layout, parent, false);
        else
            view = convertView;

        TextView text = view.findViewById(android.R.id.text1);

        var item = items.get(position);
        String textStr = item.getDisplayLanguage();
        textStr = textStr.substring(0, 1).toUpperCase() + textStr.substring(1);

        text.setText(textStr);

        return view;
    }

    @Override
    public synchronized void remove(@Nullable Locale object) {
        items.remove(object);
        super.remove(object);
    }

    @Override
    public synchronized void add(@Nullable Locale object) {
//        super.add(object);
        items.add(object);
        notifyDataSetChanged();
    }


}
