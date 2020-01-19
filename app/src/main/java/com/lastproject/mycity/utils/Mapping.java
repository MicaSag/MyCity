package com.lastproject.mycity.utils;

import android.util.Log;

import com.lastproject.mycity.firebase.database.firestore.models.TownHallFireStore;
import com.lastproject.mycity.models.LatLng;
import com.lastproject.mycity.network.retrofit.models.townhall.Adress;
import com.lastproject.mycity.network.retrofit.models.townhall.Properties;
import com.lastproject.mycity.network.retrofit.models.townhall.TownH;

import java.util.ArrayList;
import java.util.List;

public class Mapping {

    private static final String TAG = Mapping.class.getSimpleName();

    public static TownHallFireStore mapTownHToTownHall(TownH townH){
        Log.d(TAG, "mapTownHToTownHall: ");

        TownHallFireStore townHallFireStore = new TownHallFireStore();
        Properties properties = townH.getFeatures().get(0).getProperties();
        String codeInsee = properties.getCodeInsee();
        String communeName = properties.getAdresses().get(0).getCommune();
        Adress adress = properties.getAdresses().get(0);
        List<Double> location = adress.getCoordonnees();

        townHallFireStore.setInseeID(codeInsee);
        townHallFireStore.setName(communeName);
        townHallFireStore.setLocation(new LatLng(location.get(0),location.get(1)));
        townHallFireStore.setAddress(new ArrayList<>(adress.getLignes()));
        townHallFireStore.getAddress().add(adress.getCodePostal() + " "+ adress.getCommune());
        townHallFireStore.setEmail(properties.getEmail());
        townHallFireStore.setPhoneNumber(properties.getTelephone());
        townHallFireStore.setUrl(properties.getUrl());
        townHallFireStore.setHours(new ArrayList<>(properties.getHoraires()));

        return townHallFireStore;
    }
}
