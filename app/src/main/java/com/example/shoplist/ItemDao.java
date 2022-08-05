package com.example.shoplist;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM itemsTable")
    List<Item> index();

    @Query("SELECT * FROM itemsTable WHERE name = :itemName")
    Item getItem(String itemName);

    @Insert
    void insert(Item... items);

    @Update
    void update(Item... items);

    @Delete
    void delete(Item... items);
}
