package com.lastproject.mycity.utils;

import android.util.Log;

import com.lastproject.mycity.firebase.database.firestore.models.TownHall;
import com.lastproject.mycity.models.LatLng;
import com.lastproject.mycity.network.retrofit.models.townhall.Adress;
import com.lastproject.mycity.network.retrofit.models.townhall.Properties;
import com.lastproject.mycity.network.retrofit.models.townhall.TownH;

import java.util.ArrayList;
import java.util.List;

public class Mapping {

    private static final String TAG = Mapping.class.getSimpleName();

    public static TownHall mapTownHToTownHall(TownH townH){
        Log.d(TAG, "mapTownHToTownHall: ");

        TownHall townHall = new TownHall();
        Properties properties = townH.getFeatures().get(0).getProperties();
        String codeInsee = properties.getCodeInsee();
        String communeName = properties.getAdresses().get(0).getCommune();
        Adress adress = properties.getAdresses().get(0);
        List<Double> location = adress.getCoordonnees();

        townHall.setInseeID(codeInsee);
        townHall.setName(communeName);
        townHall.setLocation(new LatLng(location.get(0),location.get(1)));
        townHall.setAddress(new ArrayList<>(adress.getLignes()));
        townHall.getAddress().add(adress.getCodePostal() + " "+ adress.getCommune());
        townHall.setEmail(properties.getEmail());
        townHall.setPhoneNumber(properties.getTelephone());
        townHall.setUrl(properties.getUrl());
        townHall.setHours(new ArrayList<>(properties.getHoraires()));

        return townHall;
    }
}
