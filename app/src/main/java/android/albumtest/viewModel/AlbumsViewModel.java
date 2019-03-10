package android.albumtest.viewModel;

import android.albumtest.database.AlbumRepository;
import android.albumtest.database.model.Album;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class AlbumsViewModel extends AndroidViewModel {

    private AlbumRepository albumRepository;
    private LiveData<List<Album>> albums;

    public AlbumsViewModel(@NonNull Application application) {
        super(application);
        albumRepository = new AlbumRepository(application);
        albums = albumRepository.getAlbums();
    }

    public void fetchAlbums() {
        albumRepository.fetchAlbums();
    }

    public LiveData<List<Album>> getAlbums() {
        if(albums.getValue() != null)
            return albums;
        else
            fetchAlbums();
        return albums;
    }
}