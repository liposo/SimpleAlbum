package android.albumtest.database.dao;

import android.albumtest.database.model.Photo;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Photo photo);

    //TODO add insert delete and update methods

    @Query("SELECT * FROM photo_table ORDER BY id ASC")
    LiveData<List<Photo>> getAllPhotos();

    @Query("SELECT * FROM photo_table WHERE albumId = :albumId ORDER BY id ASC")
    LiveData<List<Photo>> getAlbumPhotos(String albumId);
}