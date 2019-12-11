package com.lastproject.mycity.network.retrofit.models.townhall;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Adress {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("lignes")
    @Expose
    private List<String> lignes = null;
    @SerializedName("codePostal")
    @Expose
    private String codePostal;
    @SerializedName("commune")
    @Expose
    private String commune;
    @SerializedName("coordonnees")
    @Expose
    private List<Double> coordonnees = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getLignes() {
        return lignes;
    }

    public void setLignes(List<String> lignes) {
        this.lignes = lignes;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public List<Double> getCoordonnees() {
        return coordonnees;
    }

    public void setCoordonnees(List<Double> coordonnees) {
        this.coordonnees = coordonnees;
    }
}