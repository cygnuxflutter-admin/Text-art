package com.addtext.textonphoto.textart.TART_features.TART_picker.TART_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_entity.TART_PhotoDirectory;
import com.addtext.textonphoto.textart.R;

import java.util.List;

public class TART_PopupDirectoryListAdapter extends BaseAdapter {
    private List<TART_PhotoDirectory> directories;
    public RequestManager glide;

    public TART_PopupDirectoryListAdapter(RequestManager requestManager, List<TART_PhotoDirectory> list) {
        this.directories = list;
        this.glide = requestManager;
    }

    public int getCount() {
        return this.directories.size();
    }

    public TART_PhotoDirectory getItem(int i) {
        return this.directories.get(i);
    }

    public long getItemId(int i) {
        return this.directories.get(i).hashCode();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.knack___picker_item_directory, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.bindData(this.directories.get(i));
        return view;
    }

    private class ViewHolder {
        public ImageView ivCover;
        public TextView tvCount;
        public TextView tvName;

        public ViewHolder(View view) {
            this.ivCover = view.findViewById(R.id.iv_dir_cover);
            this.tvName = view.findViewById(R.id.tv_dir_name);
            this.tvCount = view.findViewById(R.id.tv_dir_count);
        }

        public void bindData(TART_PhotoDirectory photoDirectory) {
            RequestOptions requestOptions = new RequestOptions();
            ((requestOptions.dontAnimate()).dontTransform()).override(800, 800);
            TART_PopupDirectoryListAdapter.this.glide.setDefaultRequestOptions(requestOptions).load(photoDirectory.getCoverPath()).thumbnail(0.1f).into(this.ivCover);
            this.tvName.setText(photoDirectory.getName());
            TextView textView = this.tvCount;
            textView.setText(photoDirectory.getPhotos().size() + "");
        }
    }
}
