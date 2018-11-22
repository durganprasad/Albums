package com.dnp.albums.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dnp.albums.R;
import com.dnp.albums.repository.model.Album;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.AlbumHolder> {
    private Context context;
    private List<Album> albumList = new ArrayList<>();

    public AlbumsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.album_list_item, parent, false);
        return new AlbumHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {
        Album album = albumList.get(position);
        holder.tvAlbumName.setText(album.getTitle());
        holder.tvUserId.setText(context.getResources().getString(R.string.album_user_id, album.getUserId()));
    }

    public void setAlbums(List<Album> albums) {
        albumList = albums;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return albumList == null ? 0 : albumList.size();
    }

    class AlbumHolder extends RecyclerView.ViewHolder {

        AppCompatTextView tvAlbumName;
        AppCompatTextView tvUserId;

        private AlbumHolder(View itemView) {
            super(itemView);

            tvAlbumName = itemView.findViewById(R.id.album_name);
            tvUserId = itemView.findViewById(R.id.album_user_id);
        }
    }
}
