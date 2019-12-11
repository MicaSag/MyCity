package com.lastproject.mycity.network.retrofit.endpoints;

import com.lastproject.mycity.network.retrofit.models.Insee;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FrenchDataGeoService {


    @GET("communes?fields=code,codesPostaux&format=json&geometry=centre")
    Observable<List<Insee>> getInseeList(@Query("nom") String town);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://geo.api.gouv.fr/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
