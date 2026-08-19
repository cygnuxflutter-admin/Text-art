package com.addtext.textonphoto.textart.TART_imagechanger;


public class TART_SaveSettings {
    private final boolean isClearViewsEnabled;
    private final boolean isTransparencyEnabled;

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

        public Builder setTransparencyEnabled(boolean z) {
            this.isTransparencyEnabled = z;
            return this;
        }

        public Builder setClearViewsEnabled(boolean z) {
            this.isClearViewsEnabled = z;
            return this;
        }

        public TART_SaveSettings build() {
            return new TART_SaveSettings(this);
        }
    }
}
