package android.albumtest.database.dao;

import android.albumtest.database.model.Album;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Album album);

    //TODO add insert delete and update methods

    @Query("SELECT * FROM album_table ORDER BY id ASC")
    LiveData<List<Album>> getAllAlbums();
}