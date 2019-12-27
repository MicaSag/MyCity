package com.lastproject.mycity.room.database;

import android.content.ContentValues;

import com.lastproject.mycity.utils.Converters;

import org.threeten.bp.LocalDateTime;

import java.util.Arrays;

public class TestData {

    static ContentValues event1(){

        //////////////////////////////     REAL ESTATE AGENT 1 : HOUSE 1    //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventID", "new event 1");
        contentValues.put("title", "Broquante");
        contentValues.put("description", "Jolie petite merde devenements pour se faire emmerder par des cons");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/brocante1_01.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/brocante1_02.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/brocante1_03.jpg\"]");
        contentValues.put("address", Arrays.asList("\"3 rue de la chesnaie\"", "\"\"", "\"Cagneux\"", "\"Bailleval\"", "\"France\"").toString());
        contentValues.put("startDate", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2019).withMonth(4)));
        contentValues.put("endDate", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2020).withMonth(3)));

        return contentValues;
    }

    static ContentValues event2(){
        //////////////////////////////     REAL ESTATE AGENT 1 : HOUSE 2    //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventID", "new event 2");
        contentValues.put("title", "Marché de Noël");
        contentValues.put("description", "Jolie petite merde devenements pour se faire emmerder par des cons");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/brocante1_01.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/brocante1_02.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/brocante1_03.jpg\"]");
        contentValues.put("address", Arrays.asList("\"3 rue de la chesnaie\"", "\"\"", "\"Cagneux\"", "\"Bailleval\"", "\"France\"").toString());
        contentValues.put("startDate", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2019).withMonth(4)));
        contentValues.put("endDate", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2020).withMonth(3)));

        return contentValues;
    }

    static ContentValues event3(){
        //////////////////////////////     REAL ESTATE AGENT 1 : penthouse 1   //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventID", "new event 3");
        contentValues.put("title", "Loto");
        contentValues.put("description", "Jolie petite merde devenements pour se faire emmerder par des cons");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/brocante1_01.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/brocante1_02.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/brocante1_03.jpg\"]");
        contentValues.put("address", Arrays.asList("\"3 rue de la chesnaie\"", "\"\"", "\"Cagneux\"", "\"Bailleval\"", "\"France\"").toString());
        contentValues.put("startDate", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2019).withMonth(4)));
        contentValues.put("endDate", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2020).withMonth(3)));

        return contentValues;
    }

    static ContentValues event4(){
        //////////////////////////////     REAL ESTATE AGENT 1 : penthouse 2   //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventID", "new event 4");
        contentValues.put("title", "Course de vélo");
        contentValues.put("description", "Jolie petite merde devenements pour se faire emmerder par des cons");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/brocante1_01.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/brocante1_02.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/brocante1_03.jpg\"]");
        contentValues.put("address", Arrays.asList("\"3 rue de la chesnaie\"", "\"\"", "\"Cagneux\"", "\"Bailleval\"", "\"France\"").toString());
        contentValues.put("startDate", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2019).withMonth(4)));
        contentValues.put("endDate", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2020).withMonth(3)));

        return contentValues;
    }

    static ContentValues event5(){
        //////////////////////////////     REAL ESTATE AGENT 1 : loft 1   //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventID", "new event 5");
        contentValues.put("title", "Mairie porte ouverte");
        contentValues.put("description", "Jolie petite merde devenements pour se faire emmerder par des cons");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/brocante1_01.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/brocante1_02.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/brocante1_03.jpg\"]");
        contentValues.put("address", Arrays.asList("\"3 rue de la chesnaie\"", "\"\"", "\"Cagneux\"", "\"Bailleval\"", "\"France\"").toString());
        contentValues.put("startDate", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2019).withMonth(4)));
        contentValues.put("endDate", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2020).withMonth(3)));

        return contentValues;
    }

    static ContentValues event6(){
        //////////////////////////////     REAL ESTATE AGENT 1 : loft 2   //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventID", "new event 6");
        contentValues.put("title", "Eglise en fête");
        contentValues.put("description", "Jolie petite merde devenements pour se faire emmerder par des cons");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/brocante1_01.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/brocante1_02.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/brocante1_03.jpg\"]");
        contentValues.put("address", Arrays.asList("\"3 rue de la chesnaie\"", "\"\"", "\"Cagneux\"", "\"Bailleval\"", "\"France\"").toString());
        contentValues.put("startDate", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2019).withMonth(4)));
        contentValues.put("endDate", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2020).withMonth(3)));

        return contentValues;
    }

    static ContentValues event7(){
        //////////////////////////////     REAL ESTATE AGENT 1 : Flat 1   //////////////////////////////
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventID", "new event 7");
        contentValues.put("title", "Bal trappe");
        contentValues.put("description", "Jolie petite merde devenements pour se faire emmerder par des cons");
        contentValues.put("photos", "[" +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/brocante1_01.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/brocante1_02.jpg\"," +
                "\"/sdcard/Android/data/com.lastproject.mycity/files/Pictures/brocante1/brocante1_03.jpg\"]");
        contentValues.put("address", Arrays.asList("\"3 rue de la chesnaie\"", "\"\"", "\"Cagneux\"", "\"Bailleval\"", "\"France\"").toString());
        contentValues.put("startDate", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2019).withMonth(4)));
        contentValues.put("endDate", Converters.dateToTimestamp(LocalDateTime.now().withDayOfMonth(2).withYear(2020).withMonth(3)));

        return contentValues;
    }
}
