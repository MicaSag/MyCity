package com.lastproject.mycity.room.database;

import android.content.ContentValues;
import android.util.Log;

import com.google.firebase.firestore.GeoPoint;
import com.lastproject.mycity.utils.Converters;

import org.threeten.bp.LocalDateTime;

import java.util.Arrays;

public class TestData {

    private static final String TAG = TestData.class.getSimpleName();

    private static final String userID = "Rxcob7pKMaUJ0YH7t1wl2Vuybtm2";

    static ContentValues event1(){
        Log.d(TAG, "event1: ");

        //////////////////////////////     EVENT 1 : BROCANTE    //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventID", LocalDateTime.now().toString() + "A");
        contentValues.put("inseeID", "60042");
        contentValues.put("title", "Brocante");
        contentValues.put("description", "Chineurs, chineuses, vous trouverez ce que vous cherchez depuis longtemps dans notre" +
                "borquante annuelle.");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/room_brocante1_01.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/room_brocante1_02.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/room_brocante1_03.jpg\"]");
        contentValues.put("location", "");
        contentValues.put("address", Arrays.asList("\"10 rue Jacques Isoré\"", "\"\"", "\"60140\"", "\"Bailleval\"", "\"France\"").toString());
        contentValues.put("startDate", "01/10/2020");
        contentValues.put("endDate", "04/10/2020");
        contentValues.put("userID", userID);
        contentValues.put("canceled", false);
        contentValues.put("published", false);
        contentValues.put("atCreate", true);
        contentValues.put("atUpdate", false);

        return contentValues;
    }

    static ContentValues event2(){
        //////////////////////////////     EVENT 2 : MARCHE DE NOEL    //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventID", LocalDateTime.now().toString() + "B");
        contentValues.put("inseeID", "60042");
        contentValues.put("title", "Marché de Noël");
        contentValues.put("description", "Noël ! C’est l’occasion de se retrouver en famille, avec les amis et faire des balades ! " +
                "C’est une bonne occasion pour commencer ou finir ses achats de Noël ou bien tout simplement pour passer un bon moment en famille ou avec ses amis." +
                "Profitez-en pour découvrir notre marché de Noël 2019. C’est une bonne occasion pour commencer ou finir ses achats de Noël " +
                "ou bien tout simplement pour passer un bon moment en famille ou avec ses amis.");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/marchenoel1/room_marchenoel1_01.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/marchenoel1/room_marchenoel1_02.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/marchenoel1/room_marchenoel1_03.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/marchenoel1/room_marchenoel1_04.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/marchenoel1/room_marchenoel1_05.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/marchenoel1/room_marchenoel1_06.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/marchenoel1/room_marchenoel1_07.jpg\"]");
        contentValues.put("location", "");
        contentValues.put("address", Arrays.asList("\"15 rue de la fontaine Tartarin\"", "\"\"", "\"60140\"", "\"Bailleval\"", "\"France\"").toString());
        contentValues.put("startDate", "02/10/2020");
        contentValues.put("endDate", "06/10/2020");
        contentValues.put("canceled", false);
        contentValues.put("published", false);
        contentValues.put("atCreate", true);
        contentValues.put("atUpdate", false);
        contentValues.put("userID", userID);

        return contentValues;
    }

    static ContentValues event3(){
        //////////////////////////////     EVENT 3 : LOTO   //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventID", LocalDateTime.now().toString() + "C");
        contentValues.put("inseeID", "60042");
        contentValues.put("title", "Loto");
        contentValues.put("description", "Toute une soirée pour remporter les meilleurs lots de cette nouvelle édition " +
                "du LOTO de la commune.");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/loto1/room_loto1_01.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/loto1/room_loto1_02.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/loto1/room_loto1_03.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/loto1/room_loto1_04.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/loto1/room_loto1_05.jpg\"]");
        contentValues.put("address", Arrays.asList("\"17 rue du Grand Aulnois\"", "\"\"", "\"60140\"", "\"Bailleval\"", "\"France\"").toString());
        contentValues.put("startDate", "12/10/2020");
        contentValues.put("endDate", "16/10/2020");
        contentValues.put("canceled", false);
        contentValues.put("published", false);
        contentValues.put("atCreate", true);
        contentValues.put("atUpdate", false);
        contentValues.put("userID", userID);

        return contentValues;
    }

    static ContentValues event4(){
        //////////////////////////////     EVENT 4 : Course de vélo   //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventID", LocalDateTime.now().toString() + "D");
        contentValues.put("inseeID", "60042");
        contentValues.put("title", "Course de vélo");
        contentValues.put("description", "18 course cycliste sur la commune. Venez participer ou tout simplement encourager" +
                "les meilleurs cyclistes qui seront au départ de cette nouvelle édition.");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/velo1/room_velo1_01.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/velo1/room_velo1_02.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/velo1/room_velo1_03.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/velo1/room_velo1_04.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/velo1/room_velo1_05.jpg\"]");
        contentValues.put("address", Arrays.asList("\"28 rue des Boues\"", "\"\"", "\"60140\"", "\"Bailleval\"", "\"France\"").toString());
        contentValues.put("startDate", "02/10/2021");
        contentValues.put("endDate", "06/11/2021");
        contentValues.put("canceled", false);
        contentValues.put("published", false);
        contentValues.put("atCreate", true);
        contentValues.put("atUpdate", false);
        contentValues.put("userID", userID);

        return contentValues;
    }

    static ContentValues event5(){
        //////////////////////////////     Event 5 : Journée porte ouverte   //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventID", LocalDateTime.now().toString() + "E");
        contentValues.put("inseeID", "60042");
        contentValues.put("title", "Journée porte ouverte");
        contentValues.put("description", "C'est le bon moment pour venir visiter les lieux classés de la commune." +
                "Venir admirer l'ancienne église, la picine du 14ième sciècle, et bien d'autres choses encore.");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/porte1/room_porte1_01.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/porte1/room_porte1_02.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/porte1/room_porte1_03.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/porte1/room_porte1_04.jpg\"]");
        contentValues.put("address", Arrays.asList("\"9 rue du Pont Mathieu\"", "\"\"", "\"60140\"", "\"Bailleval\"", "\"France\"").toString());
        contentValues.put("startDate", "02/01/2019");
        contentValues.put("endDate", "16/01/2019");
        contentValues.put("canceled", false);
        contentValues.put("published", false);
        contentValues.put("atCreate", true);
        contentValues.put("atUpdate", false);
        contentValues.put("userID", userID);

        return contentValues;
    }

    static ContentValues event6(){
        //////////////////////////////     EVENT 6 : Concert   //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventID", LocalDateTime.now().toString() + "F");
        contentValues.put("inseeID", "60042");
        contentValues.put("title", "Concert");
        contentValues.put("description", "Les petits bottes de pailles et notre nouvel événement musical." +
                "Venez y écouter les meilleurs groupes de la région du moment.");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/musique1/room_musique1_01.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/musique1/room_musique1_02.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/musique1/room_musique1_03.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/musique1/room_musique1_04.jpg\"]");
        contentValues.put("address", Arrays.asList("\"17 rue aux dames\"", "\"\"", "\"60140\"", "\"Bailleval\"", "\"France\"").toString());
        contentValues.put("startDate", "02/11/2019");
        contentValues.put("endDate", "06/11/2019");
        contentValues.put("canceled", false);
        contentValues.put("published", false);
        contentValues.put("atCreate", true);
        contentValues.put("atUpdate", false);
        contentValues.put("userID", userID);

        return contentValues;
    }

    static ContentValues event7(){
        //////////////////////////////     EVENT 7 : Ball Trap   //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventID", LocalDateTime.now().toString() + "G");
        contentValues.put("inseeID", "60042");
        contentValues.put("title", "Ball trap");
        contentValues.put("description", "Les meillerus tireurs de la region seront présents. Nous vous attendons nombreux.");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/balltrap1/room_balltrap1_01.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/balltrap1/room_balltrap1_02.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/balltrap1/room_balltrap1_03.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/balltrap1/room_balltrap1_04.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/balltrap1/room_balltrap1_05.jpg\"]");
        contentValues.put("address", Arrays.asList("\"3 rue de la chesnaie\"", "\"Cagneux\"", "\"60140\"", "\"Bailleval\"", "\"France\"").toString());
        contentValues.put("location", Converters.fromGeoPointToString(new GeoPoint(50.6105,1.88136)));
        contentValues.put("startDate", "02/10/2018");
        contentValues.put("endDate", "06/10/2018");
        contentValues.put("canceled", false);
        contentValues.put("published", false);
        contentValues.put("atCreate", true);
        contentValues.put("atUpdate", false);
        contentValues.put("userID", userID);

        return contentValues;
    }
}
