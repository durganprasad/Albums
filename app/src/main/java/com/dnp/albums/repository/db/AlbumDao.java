package com.dnp.albums.repository.db;


import com.dnp.albums.repository.model.Album;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface AlbumDao {
    String ALBUM_TABLE = "album_table";

    @Insert(onConflict = REPLACE)
    void insert(List<Album> albums);

    @Query("DELETE FROM " + ALBUM_TABLE)
    void deleteAllAlbums();

    @Query("SELECT * FROM " + ALBUM_TABLE + " ORDER BY title ASC")
    LiveData<List<Album>> getAllAlbums();
}
