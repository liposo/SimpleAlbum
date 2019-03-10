package android.albumtest.database;

import android.albumtest.api.PhotosService;
import android.albumtest.database.dao.PhotoDao;
import android.albumtest.database.model.Photo;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoRepository {

    private PhotoDao photoDao;
    private LiveData<List<Photo>> photos;

    public PhotoRepository(Application application) {
        AlbumDatabase database = AlbumDatabase.getInstance(application);
        photoDao = database.photoDao();
        photos = photoDao.getAllPhotos();
    }

    public void insert(List<Photo> photo) {
        new InsertPhotoAsyncTask(photoDao).execute(photo);
    }

    public LiveData<List<Photo>> getPhotos() {
        return photos;
    }

    public LiveData<List<Photo>> getAlbumPhotos(String albumId) {
        return photoDao.getAlbumPhotos(albumId);
    }

    private static class InsertPhotoAsyncTask extends AsyncTask<List<Photo>, Void, Void> {
        private PhotoDao photoDao;

        private InsertPhotoAsyncTask(PhotoDao photoDao){
            this.photoDao = photoDao;
        }

        @Override
        protected Void doInBackground(List<Photo>[] photos) {
            for(Photo photo : photos[0]) {
                photoDao.insert(photo);
                Log.d("Photo: ", String.valueOf(photo.getId()));
            }
            return null;
        }
    }

    public void fetchPhotos() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PhotosService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PhotosService api = retrofit.create(PhotosService.class);
        Call<List<Photo>> call = api.getPhotos();

        call.enqueue(new Callback<List<Photo>>() {
            @Override
            public void onResponse(Call<List<Photo>> call, Response<List<Photo>> response) {
                if(response.code() == 200 && response.body() != null) {
                    insert(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Photo>> call, Throwable t) {

            }
        });
    }

    //TODO Update, Delete methods
}
