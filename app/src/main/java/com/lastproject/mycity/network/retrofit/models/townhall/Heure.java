package com.lastproject.mycity.network.retrofit.models.townhall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Heure {

    @SerializedName("de")
    @Expose
    private String de;
    @SerializedName("a")
    @Expose
    private String a;

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

}
