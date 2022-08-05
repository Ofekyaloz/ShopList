package com.example.shoplist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.shoplist.databinding.ActivityAddItemBinding;

public class addItem extends AppCompatActivity {
    private AppDB db;
    private ItemDao itemDao;
    private ActivityAddItemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "ItemsDB").allowMainThreadQueries().build();
        itemDao = db.itemDao();

        EditText etName = binding.etName;
        EditText etAmount = binding.etAmount;

        Button add = binding.btnAddItem;
        add.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String amount = etAmount.getText().toString();
            if (name.length() == 0 || amount.length() == 0 || amount.equals("0")) {
                return;
            }
            while (amount.startsWith("0"))
                amount = amount.replaceFirst("0","");

            Item item = new Item(name, amount);
            itemDao.insert(item);
            finish();
        });

        binding.btnPlus.setOnClickListener(v -> {
            String num = etAmount.getText().toString();
            int amount = 0;
            if (!num.equals(""))
                amount = Integer.parseInt(num);
            etAmount.setText(String.valueOf(++amount));

        });

        binding.btnMinus.setOnClickListener(v -> {
            String num = etAmount.getText().toString();
            int amount = 2;
            if (!num.equals(""))
                amount = Integer.parseInt(num);

            if (amount <= 1)
                return;
            etAmount.setText(String.valueOf(--amount));
        });
    }
}