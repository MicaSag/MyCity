package com.lastproject.mycity.network.retrofit.endpoints;


import com.lastproject.mycity.network.retrofit.models.townhall.TownH;


import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

// Stream that back the information of a town hall
public interface FrenchDataPublicEstablishmentService {

    @GET("communes/{inseecode}/mairie")
    Observable<TownH> getTownHall(@Path("inseecode") String inseecode);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://etablissements-publics.api.gouv.fr/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}

