package com.example.shoplist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class editItem extends AppCompatActivity {
    private String itemName;
    private String itemAmount;
    private EditText etName;
    private EditText etAmount;
    private Button save;
    private Button exit;
    private int index;
    private AppDB db;
    private ItemDao itemDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "ItemsDB").allowMainThreadQueries().build();
        itemDao = db.itemDao();

        index = getIntent().getExtras().getInt("index");

        itemAmount = getIntent().getExtras().getString("amount");
        etAmount = findViewById(R.id.editNewAmount);
        etAmount.setText(itemAmount);

        itemName = getIntent().getExtras().getString("name");
        etName = findViewById(R.id.editNewName);
        etName.setText(itemName);


        findViewById(R.id.btnEditPlus).setOnClickListener(v -> {
            String num = etAmount.getText().toString();
            int amount = 0;
            if (!num.equals(""))
                amount = Integer.parseInt(num);
            etAmount.setText(String.valueOf(++amount));

        });

        findViewById(R.id.btnEditMinus).setOnClickListener(v -> {
            String num = etAmount.getText().toString();
            int amount = 2;
            if (!num.equals(""))
                amount = Integer.parseInt(num);

            if (amount <= 1)
                return;
            etAmount.setText(String.valueOf(--amount));
        });


        save = findViewById(R.id.btnSaveItem);
        save.setOnClickListener(v -> {
            Item item = itemDao.getItem(itemName);
            if (item == null) {
                finish();
                return;
            }
            String newAmount = etAmount.getText().toString();
            if (newAmount.equals(""))
                item.setAmount(itemAmount);
            else
                item.setAmount(newAmount);

            String newName = etName.getText().toString();
            if (newName.equals(""))
                item.setName(itemName);
            else
                item.setName(newName);

            itemDao.update(item);
            finish();
        });

        findViewById(R.id.btnExit).setOnClickListener(v -> finish());

    }
}