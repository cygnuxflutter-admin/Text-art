package com.addtext.textonphoto.textart.TART_imagechanger;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;


public class TART_CustomEffect {
    private final String mEffectName;
    private final Map<String, Object> parametersMap;

    private TART_CustomEffect(Builder builder) {
        this.mEffectName = builder.mEffectName;
        this.parametersMap = builder.parametersMap;
    }

    public String getEffectName() {
        return this.mEffectName;
    }

    public Map<String, Object> getParameters() {
        return this.parametersMap;
    }


    public static class Builder {
        public String mEffectName;
        public Map<String, Object> parametersMap = new HashMap();

        public Builder(String str) throws RuntimeException {
            if (!TextUtils.isEmpty(str)) {
                this.mEffectName = str;
                return;
            }
            throw new RuntimeException("Effect name cannot be empty.Please provide effect name from EffectFactory");
        }

        public Builder setParameter(String str, Object obj) {
            this.parametersMap.put(str, obj);
            return this;
        }

        public TART_CustomEffect build() {
            return new TART_CustomEffect(this);
        }
    }
}
