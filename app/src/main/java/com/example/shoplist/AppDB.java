package com.example.shoplist;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Item.class}, version = 4)
public abstract class AppDB extends RoomDatabase{
    public abstract ItemDao itemDao();

    private static AppDB INSTANCE;

    public static AppDB getDBInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(), AppDB.class, "localDB")
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }
}
