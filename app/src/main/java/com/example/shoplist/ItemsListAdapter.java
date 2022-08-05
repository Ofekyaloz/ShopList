package com.example.shoplist;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


import java.util.List;

public class ItemsListAdapter extends ArrayAdapter<Item> {

    private LayoutInflater inflater;
    private TextView tvName;
    private TextView tvAmount;



    public ItemsListAdapter(Context ctx, List<Item> userArrayList) {
        super(ctx, R.layout.item_list, userArrayList);
        this.inflater = LayoutInflater.from(ctx);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list, parent, false);
        }

        Item item = getItem(position);
        tvName = convertView.findViewById(R.id.tvItem_name);
        tvName.setText(item.getName());
        tvAmount = convertView.findViewById(R.id.tvItem_amount);
        tvAmount.setText(item.getAmount());
        CheckBox checkBox = convertView.findViewById(R.id.checkBox);
        View finalConvertView = convertView;
        checkBox.setOnClickListener(v -> {
            TextView name = finalConvertView.findViewById(R.id.tvItem_name);
            TextView num = finalConvertView.findViewById(R.id.tvItem_amount);

            // Creating a strikethrough text
            if (!name.getPaint().isStrikeThruText()) {
                name.setPaintFlags(name.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                num.setPaintFlags(num.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            // del
            else {
                name.setPaintFlags(name.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                num.setPaintFlags(num.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        });

        return convertView;
    }

}
