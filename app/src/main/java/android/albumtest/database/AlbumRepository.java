package android.albumtest.database;

import android.albumtest.api.AlbumsService;
import android.albumtest.database.dao.AlbumDao;
import android.albumtest.database.model.Album;
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

public class AlbumRepository {

    private AlbumDao albumDao;
    private LiveData<List<Album>> albums;

    public AlbumRepository(Application application) {
        AlbumDatabase database = AlbumDatabase.getInstance(application);
        albumDao = database.albumDao();
        albums = albumDao.getAllAlbums();
    }

    public void insert(List<Album> albums) {
        new InsertAlbumAsyncTask(albumDao).execute(albums);
    }

    public LiveData<List<Album>> getAlbums() {
        return albums;
    }

    private static class InsertAlbumAsyncTask extends AsyncTask<List<Album>, Void, Void> {
        private AlbumDao albumDao;

        private InsertAlbumAsyncTask(AlbumDao albumDao){
            this.albumDao = albumDao;
        }

        @Override
        protected Void doInBackground(List<Album>[] albums) {
            for(Album album : albums[0]) {
                albumDao.insert(album);
                Log.d("ALBUM: ", String.valueOf(album.getId()));
            }
            return null;
        }
    }

    public void fetchAlbums() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AlbumsService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AlbumsService api = retrofit.create(AlbumsService.class);
        Call<List<Album>> call = api.getAlbums();

        call.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if(response.code() == 200 && response.body() != null) {
                    insert(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }

    //TODO Update, Delete methods
}
