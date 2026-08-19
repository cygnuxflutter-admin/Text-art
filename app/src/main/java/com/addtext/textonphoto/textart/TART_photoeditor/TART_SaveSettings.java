package com.addtext.textonphoto.textart.TART_photoeditor;

public class TART_SaveSettings {
    private boolean isClearViewsEnabled;
    private boolean isTransparencyEnabled;


    public boolean isTransparencyEnabled() {
        return this.isTransparencyEnabled;
    }


    public boolean isClearViewsEnabled() {
        return this.isClearViewsEnabled;
    }

    private TART_SaveSettings(Builder builder) {
        this.isClearViewsEnabled = builder.isClearViewsEnabled;
        this.isTransparencyEnabled = builder.isTransparencyEnabled;
    }

    public static class Builder {

        public boolean isClearViewsEnabled = true;

        public boolean isTransparencyEnabled = true;


        public TART_SaveSettings build() {
            return new TART_SaveSettings(this);
        }
    }
}
