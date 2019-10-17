package com.mertcansegmen.locationbasedreminder.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mertcansegmen.locationbasedreminder.model.entity.Note;
import com.mertcansegmen.locationbasedreminder.model.repository.NoteRepository;

import java.util.List;

public class NotesFragmentViewModel extends AndroidViewModel {

    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;

    public NotesFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void delete(Note note) {
        repository.delete(note);
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}