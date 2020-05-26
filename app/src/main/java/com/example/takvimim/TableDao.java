package com.example.takvimim;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface TableDao {
    @Insert
    public long Inserter(Table reminders);

    @Update
    public int Updater(Table reminders);

    @Delete
    public int Deleter(Table reminders);

    @Query("Select * from reminder")
    public List<Table> getAll();

    @Query("Delete from reminder")
    public void delAll();

    @Query("Select * from reminder where remindDate LIKE :remindDate")
    public List<Table> getAllDay(String remindDate);

    @Query("Select * from reminder where time" )
    public List<Table> getAllTime();

    @Query("Select * from reminder where remindDate LIKE :remindDate")
    public Table getTable(String remindDate);



}
