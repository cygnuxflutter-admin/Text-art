package com.addtext.textonphoto.textart.TART_UnsplashImage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.addtext.textonphoto.textart.R;
import com.addtext.textonphoto.textart.TART_screens.TART_UnsplashPhotoActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class TART_PhotosAdapter extends RecyclerView.Adapter<TART_PhotosAdapter.ViewHolder> {

    private final List<TART_Photo> photoList;
    private Context mContext;


    public TART_PhotosAdapter(List<TART_Photo> photos, Context context) {
        photoList = photos;
        mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.knack_item_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        TART_Photo photo = photoList.get(position);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "onClick: "+photo.getUrls().getRegular());
                try {
                    Intent intent=new Intent(mContext, TART_UnsplashPhotoActivity.class);
                    intent.putExtra("image",photo.getUrls().getRegular());
                    mContext.startActivity(intent);
                }catch (Exception e){

                }

            }
        });

     /*   Picasso.get()
                .load(photo.getUrls().getRegular())
                .resize(300, 300)
                .centerCrop()
                .into(holder.imageView);*/

        Glide.with(mContext)
                .load(photo.getUrls().getRegular())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);
    }

    public void addPhotos(List<TART_Photo> photos){
        int lastCount = getItemCount();
        photoList.addAll(photos);
        notifyItemRangeInserted(lastCount, photos.size());
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageView;
        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imgview);
        }
    }


}
