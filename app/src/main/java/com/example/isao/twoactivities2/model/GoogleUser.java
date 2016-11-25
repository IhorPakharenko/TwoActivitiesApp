package com.example.isao.twoactivities2.model;

import com.google.gson.annotations.SerializedName;

public class GoogleUser {
    private String displayName;
    @SerializedName("image")
    private GoogleAvatar googleAvatar;

    public String getGoogleName() {
        return displayName;
    }
    public GoogleAvatar getGoogleAvatar() {
        return googleAvatar;
    }
}
