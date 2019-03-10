package android.albumtest.database;

import android.albumtest.database.dao.AlbumDao;
import android.albumtest.database.dao.PhotoDao;
import android.albumtest.database.model.Album;
import android.albumtest.database.model.Photo;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Album.class, Photo.class}, version = 1,  exportSchema = false)
public abstract class AlbumDatabase extends RoomDatabase {

    private static AlbumDatabase instance;

    public abstract AlbumDao albumDao();
    public abstract PhotoDao photoDao();

    public static synchronized AlbumDatabase getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AlbumDatabase.class, "album_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
