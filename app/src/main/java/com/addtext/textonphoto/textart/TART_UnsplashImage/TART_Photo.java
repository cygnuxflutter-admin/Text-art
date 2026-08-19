package com.addtext.textonphoto.textart.TART_UnsplashImage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TART_Photo implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("width")
    @Expose
    private Integer width;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("urls")
    @Expose
    private TART_Urls urls;
    public TART_Urls getUrls() {
        return urls;
    }
    public void setUrls(TART_Urls urls) {
        this.urls = urls;
    }
}
