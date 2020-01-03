package com.lastproject.mycity.firebase.database.firestore.models;

import com.lastproject.mycity.models.LatLng;
import com.lastproject.mycity.network.retrofit.models.townhall.Horaire;

import java.util.ArrayList;
import java.util.List;

public class TownHall {

    private String inseeID;
    private String name;
    private ArrayList<String> address;
    private String email;
    private String phoneNumber;
    private String url;
    private LatLng location;
    //private ArrayList<String> hours;
    private List<Horaire> hours;
    private String photo;

    // Blank constructor necessary for use with FireBase
    public TownHall() { }

    @Override
    public String toString() {
        return "TownHall{" +
                "inseeID='" + inseeID + '\'' +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", url='" + url + '\'' +
                ", location=" + location +
                ", hours=" + hours +
                ", photo='" + photo + '\'' +
                '}';
    }

// --- GETTERS ---

    public String getInseeID() {
        return inseeID;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUrl() {
        return url;
    }

    public LatLng getLocation() {
        return location;
    }

    public List<Horaire> getHours() {return hours;}

    public String getPhoto() {
        return photo;
    }


    // --- SETTERS ---

    public void setInseeID(String inseeID) {
        this.inseeID = inseeID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(ArrayList<String> address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setHours(List<Horaire> hours) {
        this.hours = hours;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
