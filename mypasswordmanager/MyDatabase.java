package com.example.mypasswordmanager;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Password.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public static final String NAME = "MyDatabase";

    public abstract PasswordDAO passwordDAO();
}
