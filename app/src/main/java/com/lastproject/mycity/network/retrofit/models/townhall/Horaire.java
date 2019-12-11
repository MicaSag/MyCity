package com.lastproject.mycity.network.retrofit.models.townhall;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Horaire {

        @SerializedName("du")
        @Expose
        private String du;
        @SerializedName("au")
        @Expose
        private String au;
        @SerializedName("heures")
        @Expose
        private List<Heure> heures = null;

        public String getDu() {
            return du;
        }

        public void setDu(String du) {
            this.du = du;
        }

        public String getAu() {
            return au;
        }

        public void setAu(String au) {
            this.au = au;
        }

        public List<Heure> getHeures() {
            return heures;
        }

        public void setHeures(List<Heure> heures) {
            this.heures = heures;
        }
}
