package com.lastproject.mycity.network.retrofit.models.townhall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Properties {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("codeInsee")
        @Expose
        private String codeInsee;
        @SerializedName("pivotLocal")
        @Expose
        private String pivotLocal;
        @SerializedName("nom")
        @Expose
        private String nom;
        @SerializedName("adresses")
        @Expose
        private List<Adress> adresses = null;
        @SerializedName("horaires")
        @Expose
        private List<Horaire> horaires = null;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("telephone")
        @Expose
        private String telephone;
        @SerializedName("url")
        @Expose
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCodeInsee() {
            return codeInsee;
        }

        public void setCodeInsee(String codeInsee) {
            this.codeInsee = codeInsee;
        }

        public String getPivotLocal() {
            return pivotLocal;
        }

        public void setPivotLocal(String pivotLocal) {
            this.pivotLocal = pivotLocal;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public List<Adress> getAdresses() {
            return adresses;
        }

        public void setAdresses(List<Adress> adresses) {
            this.adresses = adresses;
        }

        public List<Horaire> getHoraires() {
            return horaires;
        }

        public void setHoraires(List<Horaire> horaires) {
            this.horaires = horaires;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
}
