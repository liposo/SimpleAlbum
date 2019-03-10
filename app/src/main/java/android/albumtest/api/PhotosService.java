package android.albumtest.api;

import android.albumtest.database.model.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PhotosService {
    String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @GET("photos")
    Call<List<Photo>> getPhotos();
}
