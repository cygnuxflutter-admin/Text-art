package com.addtext.textonphoto.textart.TART_features.TART_picker.TART_entity;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.addtext.textonphoto.textart.TART_features.TART_picker.TART_utils.TART_FileUtils;

import java.util.ArrayList;
import java.util.List;

public class TART_PhotoDirectory implements Comparable<TART_PhotoDirectory> {
    private String coverPath;
    private long dateAdded;

    private String id;
    private String name;
    private List<TART_Photo> photos = new ArrayList();

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TART_PhotoDirectory)) {
            return false;
        }
        TART_PhotoDirectory photoDirectory = (TART_PhotoDirectory) obj;
        boolean z = !TextUtils.isEmpty(this.id);
        boolean isEmpty = true ^ TextUtils.isEmpty(photoDirectory.id);
        if (!z || !isEmpty || !TextUtils.equals(this.id, photoDirectory.id)) {
            return false;
        }
        return TextUtils.equals(this.name, photoDirectory.name);
    }

    public int hashCode() {
        if (!TextUtils.isEmpty(this.id)) {
            int hashCode = this.id.hashCode();
            if (TextUtils.isEmpty(this.name)) {
                return hashCode;
            }
            return (hashCode * 31) + this.name.hashCode();
        } else if (TextUtils.isEmpty(this.name)) {
            return 0;
        } else {
            return this.name.hashCode();
        }
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getCoverPath() {
        return this.coverPath;
    }

    public void setCoverPath(String str) {
        this.coverPath = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }


    public void setDateAdded(long j) {
        this.dateAdded = j;
    }

    public List<TART_Photo> getPhotos() {
        return this.photos;
    }

    public void setPhotos(List<TART_Photo> list) {
        if (list != null) {
            int size = list.size();
            int i = 0;
            for (int i2 = 0; i2 < size; i2++) {
                TART_Photo photo = list.get(i);
                if (photo == null || !TART_FileUtils.fileIsExists(photo.getPath())) {
                    list.remove(i);
                } else {
                    i++;
                }
            }
            this.photos = list;
        }
    }

    public List<String> getPhotoPaths() {
        ArrayList arrayList = new ArrayList(this.photos.size());
        for (TART_Photo path : this.photos) {
            arrayList.add(path.getPath());
        }
        return arrayList;
    }

    public void addPhoto(int i, String str) {
        if (TART_FileUtils.fileIsExists(str)) {
            this.photos.add(new TART_Photo(i, str));
        }
    }

    public int compareTo(@NonNull TART_PhotoDirectory photoDirectory) {
        try {
            return this.name.compareTo(photoDirectory.getName());
        } catch (Exception e) {
            return 1;
        }
    }
}
