package com.lastproject.mycity.network.retrofit.streams;

import com.lastproject.mycity.network.retrofit.endpoints.FrenchDataPublicEstablishmentService;
import com.lastproject.mycity.network.retrofit.models.townhall.TownHall;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TownHallStreams {

    public static Observable<TownHall> streamFetchTownHall(String insee){
        FrenchDataPublicEstablishmentService frenchDataPublicEstablishmentService
                = FrenchDataPublicEstablishmentService.retrofit.create(FrenchDataPublicEstablishmentService.class);
        return frenchDataPublicEstablishmentService.getTownHall(insee)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
