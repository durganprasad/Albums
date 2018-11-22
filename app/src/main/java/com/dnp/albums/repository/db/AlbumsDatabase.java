package com.dnp.albums.repository.db;

import android.content.Context;

import com.dnp.albums.repository.model.Album;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Album.class}, version = 1)
public abstract class AlbumsDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "albums.db";
    private static AlbumsDatabase instance;

    public abstract AlbumDao albumDao();

    public static synchronized AlbumsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AlbumsDatabase.class, DATABASE_NAME)
                    .addCallback(roomCallBack)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
