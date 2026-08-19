package com.addtext.textonphoto.textart;


import java.util.ArrayList;

public class Constants {
    public static int BORDER_WIDTH_DP = 5;
    public static ArrayList<String> FORMAT_IMAGE = new ImageTypeList();
    public static String PKG_APP = BuildConfig.APPLICATION_ID;
    public static boolean SHOW_ADS = true;

    static class ImageTypeList extends ArrayList<String> {
        ImageTypeList() {
            add(".PNG");
            add(".JPEG");
            add(".jpg");
            add(".png");
            add(".jpeg");
            add(".JPG");
        }
    }
}
