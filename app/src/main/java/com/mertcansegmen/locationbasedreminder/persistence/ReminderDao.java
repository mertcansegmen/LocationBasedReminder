package com.mertcansegmen.locationbasedreminder.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.mertcansegmen.locationbasedreminder.model.Reminder;
import com.mertcansegmen.locationbasedreminder.model.ReminderWithNotePlacePlaceGroup;

import java.util.List;

@Dao
public interface ReminderDao {

    @Insert
    void insert(Reminder reminder);

    @Update
    void update(Reminder reminder);

    @Delete
    void delete(Reminder reminder);

    @Query("DELETE FROM Reminder")
    void deleteAllReminders();

    @Transaction
    @Query("SELECT * FROM Reminder ORDER BY reminderId DESC")
    LiveData<List<ReminderWithNotePlacePlaceGroup>> getAllRemindersWithNotePlacePlaceGroup();

}
