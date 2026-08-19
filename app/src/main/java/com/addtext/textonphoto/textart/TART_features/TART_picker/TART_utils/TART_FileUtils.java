package com.addtext.textonphoto.textart.TART_features.TART_picker.TART_utils;

import java.io.File;

public class TART_FileUtils {
    public static boolean fileIsExists(String str) {
        if (str == null || str.trim().length() <= 0) {
            return false;
        }
        try {
            if (!new File(str).exists()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
