package com.dnp.albums.repository.service;

import com.dnp.albums.repository.model.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AlbumsWebService {
    String ALBUMS_URL = "https://jsonplaceholder.typicode.com/";

    @GET("albums")
    Call<List<Album>> getAlbums();
}