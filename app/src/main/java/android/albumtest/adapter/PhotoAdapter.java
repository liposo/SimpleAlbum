package android.albumtest.adapter;

import android.albumtest.R;
import android.albumtest.database.model.Album;
import android.albumtest.database.model.Photo;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class PhotoAdapter extends ListAdapter<Photo, PhotoAdapter.PhotoViewHolder> {

    private onItemClickListener onItemClickListener;
    private Context context;

    private static final DiffUtil.ItemCallback<Photo> DIFF_CALLBACK = new DiffUtil.ItemCallback<Photo>() {
        @Override
        public boolean areItemsTheSame(@NonNull Photo oldItem, @NonNull Photo newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Photo oldItem, @NonNull Photo newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getId().equals(newItem.getId()) &&
                    oldItem.getAlbumId().equals(newItem.getAlbumId()) &&
                    oldItem.getThumbnailUrl().equals(newItem.getThumbnailUrl()) &&
                    oldItem.getUrl().equals(newItem.getUrl());
        }
    };

    public PhotoAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_item, parent, false);
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo currentPhoto = getItem(position);
        holder.titleTextView.setText(currentPhoto.getTitle());
        Glide.with(context)
                .load(currentPhoto.getThumbnailUrl())
                .into(holder.imageView);
    }

    public Photo getPhotoAt(int position) {
        return getItem(position);
    }

    class PhotoViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private ImageView imageView;

        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (onItemClickListener != null && position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface onItemClickListener {
        void onItemClick(Photo photo);
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}