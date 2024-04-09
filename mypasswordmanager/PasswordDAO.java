package com.example.mypasswordmanager;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PasswordDAO {

    @Delete
    void delete(Password password);

    @Insert
    void insertAll(Password... passwords);

    @Query("SELECT * FROM table_password ORDER BY id DESC")
    List<Password> getPassword();
}
