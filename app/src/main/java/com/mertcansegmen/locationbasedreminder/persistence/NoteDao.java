package com.mertcansegmen.locationbasedreminder.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.mertcansegmen.locationbasedreminder.model.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    long insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM Note WHERE noteId NOT IN (SELECT noteId FROM Reminder)")
    void deleteAllNotes();

    @Query("SELECT * FROM Note WHERE noteId NOT IN (SELECT noteId FROM Reminder) ORDER BY noteId DESC")
    LiveData<List<Note>> getAllNotes();
}