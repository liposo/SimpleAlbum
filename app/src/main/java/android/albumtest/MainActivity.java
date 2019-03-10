package android.albumtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.albumtest.adapter.AlbumAdapter;
import android.albumtest.viewModel.AlbumsViewModel;
import android.albumtest.viewModel.PhotosViewModel;
import android.content.Intent;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    private AlbumsViewModel albumsViewModel;
    private PhotosViewModel photosViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        albumsViewModel = ViewModelProviders.of(this).get(AlbumsViewModel.class);
        photosViewModel = ViewModelProviders.of(this).get(PhotosViewModel.class);

        final AlbumAdapter albumAdapter = new AlbumAdapter();
        recyclerView.setAdapter(albumAdapter);

        albumsViewModel.getAlbums().observe(this, albumAdapter::submitList);

        /*
            The idea would be to fetch the photos from the API by albumId but I noticed that the routes are retuning all the content
            Example: https://jsonplaceholder.typicode.com/albums/1/photos should return only the photos in album id = 1
            but is returning all the photos.
            This issue was already reported and is currently open:
            https://github.com/typicode/jsonplaceholder/issues/91
            -----
            So I choose to fetch all albums and all photos, save in to the Room database and use a query to
            display the images of the selected album.
            Lots of improvement can be done here, for instance display a progress bar while everything is synced.
         */
        photosViewModel.getPhotos();

        albumAdapter.setOnItemClickListener(album -> {
            Intent myIntent = new Intent(getApplicationContext(), AlbumPhotosActivity.class);
            myIntent.putExtra("ALBUM_ID",album.getId());
            startActivity(myIntent);
        });
    }
}
