package com.lastproject.mycity.network.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Insee {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("codesPostaux")
    @Expose
    private List<String> codesPostaux = null;
    @SerializedName("nom")
    @Expose
    private String nom;
    @SerializedName("_score")
    @Expose
    private Double score;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getCodesPostaux() {
        return codesPostaux;
    }

    public void setCodesPostaux(List<String> codesPostaux) {
        this.codesPostaux = codesPostaux;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

}
