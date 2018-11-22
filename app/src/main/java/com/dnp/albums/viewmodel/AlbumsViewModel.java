package com.dnp.albums.viewmodel;

import android.app.Application;

import com.dnp.albums.repository.model.Album;
import com.dnp.albums.repository.AlbumsRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class AlbumsViewModel extends AndroidViewModel {

    private AlbumsRepository repository;

    public AlbumsViewModel(@NonNull Application application) {
        super(application);

        repository = new AlbumsRepository(application);
    }

    public LiveData<List<Album>> getAlbums() {
        return repository.getAlbumList();
    }
}
