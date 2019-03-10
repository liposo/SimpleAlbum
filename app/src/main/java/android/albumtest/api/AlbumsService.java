package android.albumtest.api;

import android.albumtest.database.model.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AlbumsService {
    String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @GET("albums")
    Call<List<Album>> getAlbums();
}
