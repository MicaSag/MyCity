package com.lastproject.mycity.network.retrofit.streams;

import com.lastproject.mycity.network.retrofit.endpoints.FrenchDataGeoService;
import com.lastproject.mycity.network.retrofit.models.Insee;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

// Stream that returns a list of INSEE code according to a string
public class InseeListStreams {

    public static Observable<List<Insee>> streamFetchInseeList(String town){
        FrenchDataGeoService frenchDataGeoService
                = FrenchDataGeoService.retrofit.create(FrenchDataGeoService.class);
        return frenchDataGeoService.getInseeList(town)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
