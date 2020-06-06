package net.scandicraft.http.entity;

import com.google.gson.annotations.SerializedName;

public class VerifyTokenEntity {

    @SerializedName("username")
    private String name;

    private String uuid;

    private String token;

    public VerifyTokenEntity(String name, String uuid, String token) {
        this.name = name;
        this.uuid = uuid;
        this.token = token;
    }

}
