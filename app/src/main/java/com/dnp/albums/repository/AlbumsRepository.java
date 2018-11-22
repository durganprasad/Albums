package com.dnp.albums.repository;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.dnp.albums.repository.model.Album;
import com.dnp.albums.repository.db.AlbumDao;
import com.dnp.albums.repository.db.AlbumsDatabase;
import com.dnp.albums.repository.service.AlbumsServiceClient;
import com.dnp.albums.repository.service.AlbumsWebService;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumsRepository {
    private static final String TAG = "AlbumsRepository";
    private static final String PREF_KEY_LAST_REFRESH_TIME = "last_refresh_time";
    private static final int REFRESH_TIMEOUT = 60 * 1000;

    private final AlbumsWebService albumsService;
    private final AlbumDao albumDao;
    private final Executor executor;
    private final SharedPreferences prefs;

    public AlbumsRepository(Application application) {
        albumsService = AlbumsServiceClient.getClient().create(AlbumsWebService.class);

        AlbumsDatabase database = AlbumsDatabase.getInstance(application);
        albumDao = database.albumDao();

        // Executor to run the service request in background thread
        executor = Executors.newSingleThreadExecutor();

        prefs = PreferenceManager.getDefaultSharedPreferences(application);
    }

    public LiveData<List<Album>> getAlbumList() {
        getAlbumsFromService();
        return albumDao.getAllAlbums();
    }

    private void getAlbumsFromService() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (needRefresh()) {
                    albumsService.getAlbums().enqueue(new Callback<List<Album>>() {
                        @Override
                        public void onResponse(Call<List<Album>> call, final Response<List<Album>> response) {
                            executor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    if (response.isSuccessful()) {
                                        Log.i(TAG, "Albums refreshed from service");
                                        albumDao.insert(response.body());
                                        updateLastRefreshTime();
                                    }
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<List<Album>> call, Throwable t) {
                            // Can implement retry policy here as an improvement
                            Log.e(TAG, "Error from the service");
                        }
                    });
                } else {
                    Log.i(TAG, "Refresh not required. Albums loaded from DB.");
                }
            }
        });
    }

    private boolean needRefresh() {
        long lastRefreshTime = prefs.getLong(PREF_KEY_LAST_REFRESH_TIME, 0L);
        return System.currentTimeMillis() - lastRefreshTime > REFRESH_TIMEOUT;
    }

    private void updateLastRefreshTime() {
        prefs.edit().putLong(PREF_KEY_LAST_REFRESH_TIME, System.currentTimeMillis()).apply();
    }
}
