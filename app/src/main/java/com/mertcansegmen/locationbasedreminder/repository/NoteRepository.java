package com.mertcansegmen.locationbasedreminder.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.mertcansegmen.locationbasedreminder.persistence.AppDatabase;
import com.mertcansegmen.locationbasedreminder.persistence.NoteDao;
import com.mertcansegmen.locationbasedreminder.model.Note;
import com.mertcansegmen.locationbasedreminder.persistence.PlaceGroupDao;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;
    PlaceGroupDao placeGroupDao;

    public NoteRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        noteDao = database.noteDao();
        placeGroupDao = database.placeGroupDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Note note) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> noteDao.insert(note));
    }

    public void update(Note note) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> noteDao.update(note));
    }

    public void delete(Note note) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> noteDao.delete(note));
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
