package com.lastproject.mycity.room.database;

import android.content.ContentValues;
import android.util.Log;

import com.google.firebase.firestore.GeoPoint;
import com.lastproject.mycity.utils.Converters;

import org.threeten.bp.LocalDateTime;

import java.util.Arrays;

public class TestData {

    private static final String TAG = TestData.class.getSimpleName();

    static ContentValues event1(){
        Log.d(TAG, "event1: ");

        //////////////////////////////     EVENT 1 : BROQUANTE    //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventID", LocalDateTime.now().toString());
        contentValues.put("inseeID", "38174");
        contentValues.put("title", "Broquante");
        contentValues.put("description", "Chineurs, chineuses, vous trouverez ce que vous cherchez depuis longtemps dans notre" +
                "borquante annuelle.");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/brocante1_01.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/brocante1_02.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/brocante1_03.jpg\"]");
        contentValues.put("location", "");
        contentValues.put("address", Arrays.asList("\"3 rue de la chesnaie\"", "\"\"", "\"Cagneux\"", "\"Bailleval\"", "\"France\"").toString());
        contentValues.put("startDate", Converters.fromLocalDateTimeToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2019).withMonth(4)));
        contentValues.put("endDate", Converters.fromLocalDateTimeToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2020).withMonth(3)));
        contentValues.put("canceled", false);

        return contentValues;
    }

    static ContentValues event2(){
        //////////////////////////////     EVENT 2 : MARCHE DE NOEL    //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventID", LocalDateTime.now().toString());
        contentValues.put("inseeID", "38174");
        contentValues.put("title", "Marché de Noël");
        contentValues.put("description", "Noël ! C’est l’occasion de se retrouver en famille, avec les amis et faire des balades ! " +
                "C’est une bonne occasion pour commencer ou finir ses achats de Noël ou bien tout simplement pour passer un bon moment en famille ou avec ses amis." +
                "Profitez-en pour découvrir notre marché de Noël 2019. C’est une bonne occasion pour commencer ou finir ses achats de Noël " +
                "ou bien tout simplement pour passer un bon moment en famille ou avec ses amis.");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/marchenoel1/marchenoel1_01.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/marchenoel1/marchenoel1_02.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/marchenoel1/marchenoel1_03.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/marchenoel1/marchenoel1_04.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/marchenoel1/marchenoel1_05.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/marchenoel1/marchenoel1_06.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/marchenoel1/marchenoel1_07.jpg\"]");
        contentValues.put("address", Arrays.asList("\"3 rue de la chesnaie\"", "\"\"", "\"Cagneux\"", "\"Bailleval\"", "\"France\"").toString());
        contentValues.put("startDate", Converters.fromLocalDateTimeToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2019).withMonth(4)));
        contentValues.put("endDate", Converters.fromLocalDateTimeToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2020).withMonth(3)));
        contentValues.put("canceled", false);

        return contentValues;
    }

    static ContentValues event3(){
        //////////////////////////////     EVENT 3 : LOTO   //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventID", LocalDateTime.now().toString());
        contentValues.put("inseeID", "38174");
        contentValues.put("title", "Loto");
        contentValues.put("description", "Toute une soirée pour remporter les meilleurs lots de cette nouvelle édition " +
                "du LOTO de la commune.");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/loto1/loto1_01.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/loto1/loto1_02.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/loto1/loto1_03.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/loto1/loto1_04.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/loto1/loto1_05.jpg\"]");
        contentValues.put("address", Arrays.asList("\"3 rue de la chesnaie\"", "\"\"", "\"Cagneux\"", "\"Bailleval\"", "\"France\"").toString());
        contentValues.put("startDate", Converters.fromLocalDateTimeToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2019).withMonth(4)));
        contentValues.put("endDate", Converters.fromLocalDateTimeToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2020).withMonth(3)));
        contentValues.put("canceled", false);

        return contentValues;
    }

    static ContentValues event4(){
        //////////////////////////////     EVENT 4 : Course de vélo   //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventID", LocalDateTime.now().toString());
        contentValues.put("inseeID", "38174");
        contentValues.put("title", "Course de vélo");
        contentValues.put("description", "18 course cycliste sur la commune. Venez participer ou tout simplement encourager" +
                "les meilleurs cyclistes qui seront au départ de cette nouvelle édition.");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/velo1/velo1_01.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/velo1/velo1_02.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/velo1/velo1_03.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/velo1/velo1_04.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/velo1/velo1_05.jpg\"]");
        contentValues.put("address", Arrays.asList("\"3 rue de la chesnaie\"", "\"\"", "\"Cagneux\"", "\"Bailleval\"", "\"France\"").toString());
        contentValues.put("startDate", Converters.fromLocalDateTimeToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2019).withMonth(4)));
        contentValues.put("endDate", Converters.fromLocalDateTimeToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2020).withMonth(3)));
        contentValues.put("canceled", false);

        return contentValues;
    }

    static ContentValues event5(){
        //////////////////////////////     Event 5 : Journée porte ouverte   //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventID", LocalDateTime.now().toString());
        contentValues.put("inseeID", "38174");
        contentValues.put("title", "Journée porte ouverte");
        contentValues.put("description", "C'est le bon moment pour venir visiter les lieux classés de la commune." +
                "Venir admirer l'ancienne église, la picine du 14ième sciècle, et bien d'autres choses encore.");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/porte1/porte1_01.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/porte1/porte1_02.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/porte1/porte1_03.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/porte1/porte1_04.jpg\"]");
        contentValues.put("address", Arrays.asList("\"3 rue de la chesnaie\"", "\"\"", "\"Cagneux\"", "\"Bailleval\"", "\"France\"").toString());
        contentValues.put("startDate", Converters.fromLocalDateTimeToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2019).withMonth(4)));
        contentValues.put("endDate", Converters.fromLocalDateTimeToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2020).withMonth(3)));
        contentValues.put("canceled", false);

        return contentValues;
    }

    static ContentValues event6(){
        //////////////////////////////     EVENT 6 : Concert   //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventID", LocalDateTime.now().toString());
        contentValues.put("inseeID", "38174");
        contentValues.put("title", "Concert");
        contentValues.put("description", "Les petits bottes de pailles et notre nouvel événement musical." +
                "Venez y écouter les meilleurs groupes de la région du moment.");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/musique1/musique1_01.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/musique1/musique1_02.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/musique1/musique1_03.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/musique1/musique1_04.jpg\"]");
        contentValues.put("address", Arrays.asList("\"3 rue de la chesnaie\"", "\"\"", "\"Cagneux\"", "\"Bailleval\"", "\"France\"").toString());
        contentValues.put("startDate", Converters.fromLocalDateTimeToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2019).withMonth(4)));
        contentValues.put("endDate", Converters.fromLocalDateTimeToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2020).withMonth(3)));
        contentValues.put("canceled", false);

        return contentValues;
    }

    static ContentValues event7(){
        //////////////////////////////     EVENT 7 : Ball Trap   //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventID", LocalDateTime.now().toString());
        contentValues.put("inseeID", "38174");
        contentValues.put("title", "Ball trap");
        contentValues.put("description", "Les meillerus tireurs de la region seront présents. Nous vous attendons nombreux.");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/balltrap1/balltrap1_01.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/balltrap1/balltrap1_02.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/balltrap1/balltrap1_03.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/balltrap1/balltrap1_04.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/balltrap1/balltrap1_05.jpg\"]");
        contentValues.put("address", Arrays.asList("\"3 rue de la chesnaie\"", "\"\"", "\"Cagneux\"", "\"Bailleval\"", "\"France\"").toString());
        contentValues.put("location", Converters.fromGeoPointToString(new GeoPoint(50.6105,1.88136)));
        contentValues.put("startDate", Converters.fromLocalDateTimeToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2019).withMonth(4)));
        contentValues.put("endDate", Converters.fromLocalDateTimeToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2020).withMonth(3)));
        contentValues.put("canceled", false);

        return contentValues;
    }
}
