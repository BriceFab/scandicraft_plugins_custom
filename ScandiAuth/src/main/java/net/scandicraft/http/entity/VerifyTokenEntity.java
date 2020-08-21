package net.scandicraft.http.entity;

import com.google.gson.annotations.SerializedName;

public class VerifyTokenEntity {

    @SerializedName("username")
    private final String name;

    private final String uuid;

    private final String token;

    public VerifyTokenEntity(String name, String uuid, String token) {
        this.name = name;
        this.uuid = uuid;
        this.token = token;
    }

}
