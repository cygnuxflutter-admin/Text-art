package com.addtext.textonphoto.textart.TART_features.TART_picker.TART_adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_entity.TART_Photo;
import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_entity.TART_PhotoDirectory;
import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_event.TART_Selectable;

import java.util.ArrayList;
import java.util.List;

public abstract class TART_SelectableAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements TART_Selectable {
    private static final String TAG = "SelectableAdapter";
    public int currentDirectoryIndex = 0;
    protected List<TART_PhotoDirectory> photoDirectories = new ArrayList();
    protected List<String> selectedPhotos = new ArrayList();

    public boolean isSelected(TART_Photo photo) {
        return getSelectedPhotos().contains(photo.getPath());
    }

    public void toggleSelection(TART_Photo photo) {
        if (this.selectedPhotos.contains(photo.getPath())) {
            this.selectedPhotos.remove(photo.getPath());
        } else {
            this.selectedPhotos.add(photo.getPath());
        }
    }

    public void clearSelection() {
        this.selectedPhotos.clear();
    }

    public int getSelectedItemCount() {
        return this.selectedPhotos.size();
    }

    public void setCurrentDirectoryIndex(int i) {
        this.currentDirectoryIndex = i;
    }

    public List<TART_Photo> getCurrentPhotos() {
        if (this.photoDirectories.size() <= this.currentDirectoryIndex) {
            this.currentDirectoryIndex = this.photoDirectories.size() - 1;
        }
        return this.photoDirectories.get(this.currentDirectoryIndex).getPhotos();
    }

    public List<String> getCurrentPhotoPaths() {
        ArrayList arrayList = new ArrayList(getCurrentPhotos().size());
        for (TART_Photo path : getCurrentPhotos()) {
            arrayList.add(path.getPath());
        }
        return arrayList;
    }

    public List<String> getSelectedPhotos() {
        return this.selectedPhotos;
    }
}
