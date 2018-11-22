package com.dnp.albums.view.ui;

import android.os.Bundle;
import android.view.View;

import com.dnp.albums.R;
import com.dnp.albums.repository.model.Album;
import com.dnp.albums.view.adapter.AlbumsAdapter;
import com.dnp.albums.viewmodel.AlbumsViewModel;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AlbumsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_albums);

        final RecyclerView albumsView = findViewById(R.id.album_list);
        albumsView.setLayoutManager(new LinearLayoutManager(this));
        albumsView.setHasFixedSize(true);

        final AppCompatTextView loadingView = findViewById(R.id.loading_albums);
        final AppCompatTextView noAlbumsView = findViewById(R.id.no_albums);

        final AlbumsAdapter adapter = new AlbumsAdapter(this);
        albumsView.setAdapter(adapter);

        AlbumsViewModel albumsViewModel = ViewModelProviders.of(this).get(AlbumsViewModel.class);
        albumsViewModel.getAlbums().observe(this, new Observer<List<Album>>() {
            @Override
            public void onChanged(List<Album> albums) {
                loadingView.setVisibility(View.GONE);

                if (albums == null || albums.isEmpty()) {
                    noAlbumsView.setVisibility(View.VISIBLE);
                    albumsView.setVisibility(View.GONE);
                } else {
                    noAlbumsView.setVisibility(View.GONE);
                    albumsView.setVisibility(View.VISIBLE);
                    adapter.setAlbums(albums);
                }
            }
        });
    }
}
