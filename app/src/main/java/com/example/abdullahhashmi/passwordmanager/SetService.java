package com.example.abdullahhashmi.passwordmanager;

/**
 * Created by abdullahhashmi on 4/26/17.
 */

import android.content.ContentValues;
import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import android.widget.Toast;


public class SetService {

    private Context context;
    private DBHelper dbHandler;
    private Crypter crypter;
    private SQLiteDatabase database;

    public SetService(Context context, DBHelper dbHandler, Crypter crypter) {
        this.context = context;
        this.dbHandler = dbHandler;
        this.crypter = crypter;
    }

    public boolean addService(String[] toAdd) {
        long success = -1;
        if (toAdd[0] == null)
            toAdd[0] = "";
        try {
            ContentValues pushData = new ContentValues();
            long id = System.currentTimeMillis() / 1000;
            pushData.put("ID", id);
            pushData.put("TITLE", crypter.encrypt(toAdd[0]));
            pushData.put("USERNAME", crypter.encrypt(toAdd[1]));
            pushData.put("PASSWORD", crypter.encrypt(toAdd[2]));
            database = dbHandler.openDB(true);
            try {
                success = database.insertOrThrow("RECORDS", null, pushData);
            } catch (SQLiteException e) {
                Toast.makeText(context, "Error writing record", Toast.LENGTH_SHORT).show();
            }
            dbHandler.closeDB();
        } catch (Exception e) {
            Toast.makeText(context, "Encryption error", Toast.LENGTH_SHORT).show();
        }
        return success != -1;
    }

    public boolean updateService(int id, String[] toUpdate) {
        long success = -1;
        if (toUpdate[0] == null)
            toUpdate[0] = "";
        try {
            ContentValues pushData = new ContentValues();
            pushData.put("TITLE", crypter.encrypt(toUpdate[0]));
            pushData.put("USERNAME", crypter.encrypt(toUpdate[1]));
            pushData.put("PASSWORD", crypter.encrypt(toUpdate[2]));
            database = dbHandler.openDB(true);
            try {
                success = database.update("RECORDS", pushData, "ID=" + id, null);
            } catch (SQLiteException e) {

            }
            dbHandler.closeDB();
        } catch (Exception e) {
            Toast.makeText(context, "Encryption Error", Toast.LENGTH_SHORT).show();
        }
        return success == 1;
    }

    public boolean isEmpty(String text) {
        return text.length() == 0;
    }
}