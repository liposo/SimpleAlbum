package android.albumtest;

import android.albumtest.adapter.PhotoAdapter;
import android.albumtest.viewModel.PhotosViewModel;
import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AlbumPhotosActivity extends AppCompatActivity {

    private PhotosViewModel photosViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        photosViewModel = ViewModelProviders.of(this).get(PhotosViewModel.class);

        final PhotoAdapter photoAdapter = new PhotoAdapter(this);
        recyclerView.setAdapter(photoAdapter);

        photosViewModel.getAlbumPhotos(getIntent().getStringExtra("ALBUM_ID")).observe(this, photoAdapter::submitList);

        photoAdapter.setOnItemClickListener(photo -> {
            Intent myIntent = new Intent(getApplicationContext(), FullImageActivity.class);
            myIntent.putExtra("IMG_URL",photo.getUrl());
            startActivity(myIntent);
        });
    }
}
