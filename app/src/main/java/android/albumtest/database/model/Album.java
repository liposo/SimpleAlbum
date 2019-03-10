package android.albumtest.database.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "album_table")
public class Album {

    @PrimaryKey
    @NonNull
    private String id;
    private String userId;
    private String title;

    public Album(@NonNull String id, String userId, String title) {
        this.id = id;
        this.userId = userId;
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
