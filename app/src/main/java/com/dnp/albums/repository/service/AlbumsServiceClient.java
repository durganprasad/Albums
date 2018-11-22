package com.dnp.albums.repository.service;

import androidx.annotation.NonNull;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.dnp.albums.repository.service.AlbumsWebService.ALBUMS_URL;

public class AlbumsServiceClient {
    @NonNull
    public static Retrofit getClient() {
        return new Retrofit.Builder()
                .baseUrl(ALBUMS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
