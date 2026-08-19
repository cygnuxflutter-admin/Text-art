package com.addtext.textonphoto.textart.TART_features.TART_picker.TART_entity;

public class TART_Photo {

    private int id;
    private String path;

    public TART_Photo(int i, String str) {
        this.id = i;
        this.path = str;
    }

    public TART_Photo() {
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj instanceof TART_Photo) && this.id == ((TART_Photo) obj).id) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.id;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }
}
