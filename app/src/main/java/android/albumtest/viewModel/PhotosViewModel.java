package android.albumtest.viewModel;

import android.albumtest.database.PhotoRepository;
import android.albumtest.database.model.Photo;
import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PhotosViewModel extends AndroidViewModel {

    private PhotoRepository photoRepository;
    private LiveData<List<Photo>> photos;

    public PhotosViewModel(@NonNull Application application) {
        super(application);
        photoRepository = new PhotoRepository(application);
        photos = photoRepository.getPhotos();
    }

    public void fetchPhotos() {
        photoRepository.fetchPhotos();
    }

    public LiveData<List<Photo>> getPhotos() {
        if(photos.getValue() != null)
            return photos;
        else
            fetchPhotos();
        return photos;
    }

    public LiveData<List<Photo>> getAlbumPhotos(String albumId) {
        return photoRepository.getAlbumPhotos(albumId);
    }
}