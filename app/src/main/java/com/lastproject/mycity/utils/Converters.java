package com.lastproject.mycity.utils;

import android.util.Log;

import androidx.room.TypeConverter;

import com.google.firebase.firestore.GeoPoint;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lastproject.mycity.models.LatLng;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;


public class Converters {

    // For Debug
    private static final String TAG = Converters.class.getSimpleName();

    @TypeConverter
    public static LocalDateTime fromTimestampToLocalDateTime(Long value) {
        Log.d(TAG, "fromTimestamp() called with: value = [" + value + "]");
        return value == null ? null : Instant.ofEpochMilli(value).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    @TypeConverter
    public static Long fromLocalDateTimeToTimestamp(LocalDateTime date) {
        Log.d(TAG, "dateToTimestamp() called with: date = [" + date + "]");
        Long dateLong = (date == null ? null : date.atZone(ZoneId.systemDefault()).toEpochSecond()*1000);
        Log.d(TAG, "dateToTimestamp: dateLong = "+dateLong);
        return dateLong;
    }

    @TypeConverter
    public static ArrayList<String> fromString(String value) {
        Log.d(TAG, "fromString() called with: value = [" + value + "]");
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<String> list) {
        Log.d(TAG, "fromArrayList() called with: list = [" + list + "]");
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public static Map<String,String> fromStringToMapStringString(String value) {
        Log.d(TAG, "fromStringToMapStringString() called with: value = [" + value + "]");
        if (value == null){
            return(null);
        }
        Type mapType = new TypeToken<Map<String,String>>() {}.getType();
        return new Gson().fromJson(value, mapType);
    }

    @TypeConverter
    public static String fromMapStringStringToString(Map<String,String> map) {
        if (map == null){
            return(null);
        }
        Gson gson = new Gson();
        String json = gson.toJson(map);
        return json;
    }

    @TypeConverter
    public static LatLng fromStringToLatLng(String value) {
        if (value == null){
            return(null);
        }
        Type latLngType = new TypeToken<LatLng>() {}.getType();
        return new Gson().fromJson(value, latLngType);
    }

    @TypeConverter
    public static String fromLatLngToString(LatLng location) {
        if (location == null){
            return(null);
        }
        Gson gson = new Gson();
        String json = gson.toJson(location);
        return json;
    }
    @TypeConverter
    public static String fromGeoPointToString(GeoPoint geoPoint) {
        Log.d(TAG, "fromGeoPointToString() called with: geoPoint = [" + geoPoint + "]");
        Gson gson = new Gson();
        String json = gson.toJson(geoPoint);
        return json;
    }
    @TypeConverter
    public static GeoPoint fromStringToGeoPoint(String geoPoint) {
        Log.d(TAG, "fromStringToGeoPoint() called with: geoPoint = [" + geoPoint + "]");
        if (geoPoint == null){
            return(null);
        }
        Type geoPointType = new TypeToken<GeoPoint>() {}.getType();
        return new Gson().fromJson(geoPoint, geoPointType);
    }
}