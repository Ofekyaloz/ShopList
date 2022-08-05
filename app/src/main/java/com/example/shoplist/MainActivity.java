package com.example.shoplist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.shoplist.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;
    private ItemsListAdapter adapter;
    private ListView listView;
    private List<Item> itemList;
    private Button selectAll;
    private AppDB db;
    private ItemDao itemDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "ItemsDB").allowMainThreadQueries().build();
        itemDao = db.itemDao();

        listView = findViewById(R.id.items);
        itemList = new ArrayList<>();
        adapter = new ItemsListAdapter(getApplicationContext(), itemList);
        listView.setAdapter(adapter);
        listView.setClickable(true);

        SwipeDetector swipeDetector = new SwipeDetector();
        listView.setOnTouchListener(swipeDetector);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            if (swipeDetector.swipeDetected()) {

                // right to left
                if (swipeDetector.getAction() == SwipeDetector.Action.RL) {
                    itemDao.delete(itemList.get(i));
                    onResume();
                    return;
                } else if (swipeDetector.getAction() == SwipeDetector.Action.LR){
                    return;
                }
            }

            Intent intent = new Intent(this, editItem.class);
            intent.putExtra("name", itemList.get(i).getName());
            intent.putExtra("amount",itemList.get(i).getAmount());
            intent.putExtra("index", i);
            startActivity(intent);
        });



        mainBinding.btnAddItem.setOnClickListener(view -> {
            Intent intent = new Intent(this, addItem.class);
            startActivity(intent);
        });

        selectAll = mainBinding.selectall;
        selectAll.setOnClickListener(v -> {
            boolean flag = false;
            if (selectAll.getText().toString().equals("SELECT ALL")) {
                selectAll.setText(R.string.unselect_all);
                flag = true;
            } else {
                selectAll.setText(R.string.select_all);
            }

            for(int i=0; i < listView.getChildCount(); i++){
                CheckBox cb = listView.getChildAt(i).findViewById(R.id.checkBox);
                cb.callOnClick();
                cb.setChecked(flag);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        itemList.clear();
        itemList.addAll(itemDao.index());
        adapter.notifyDataSetChanged();
    }
}