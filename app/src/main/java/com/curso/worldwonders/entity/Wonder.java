package com.curso.worldwonders.entity;

import android.database.Cursor;

import com.curso.worldwonders.database.tables.WondersTable;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Junior on 13/08/2015.
 */
public class Wonder implements Serializable {

    public int objectId;
    public int id;
    public String name;
    public String country;
    public String description;
    public String image_url;

    public Wonder(final Cursor cursor) {
        this.id = cursor.getInt(cursor.getColumnIndex(WondersTable.ID));
        this.objectId = cursor.getInt(cursor.getColumnIndex(WondersTable.OBJECT_ID));
        this.name = cursor.getString(cursor.getColumnIndex(WondersTable.NAME));
        this.country = cursor.getString(cursor.getColumnIndex(WondersTable.COUNTRY));
        this.image_url = cursor.getString(cursor.getColumnIndex(WondersTable.IMAGE_URL));
        this.description = cursor.getString(cursor.getColumnIndex(WondersTable.DESCRIPTION));
    }

    public Wonder(final JSONObject jsonObject) {
        this.objectId = jsonObject.optInt("objectId");
        this.name = jsonObject.optString("name");
        this.country = jsonObject.optString("Peru");
        this.image_url = jsonObject.optString("imageUrl");
        this.description = jsonObject.optString("description");
    }

}
