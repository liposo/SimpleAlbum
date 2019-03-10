package android.albumtest.database.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "photo_table")
public class Photo {

    @PrimaryKey
    @NonNull
    private String id;
    private String userId;
    private String albumId;
    private String title;
    private String url;
    private String thumbnailUrl;

    public Photo(@NonNull String id, String userId, String albumId, String title, String url, String thumbnailUrl) {
        this.id = id;
        this.userId = userId;
        this.albumId = albumId;
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getAlbumId() {
        return albumId;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
