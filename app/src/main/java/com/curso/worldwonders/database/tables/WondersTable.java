package com.curso.worldwonders.database.tables;

/**
 * Created by Junior on 06/08/2015.
 */
public interface WondersTable {

    String TABLE_NAME = "wonders";

    String ID = "_id";
    String OBJECT_ID = "object_id";
    String NAME = "name";
    String COUNTRY = "country";
    String DESCRIPTION = "description";
    String IMAGE_URL = "image_url";

    String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " ( "
            + ID + " INTEGER" + ","
            + OBJECT_ID + " TEXT PRIMARY KEY" + ","
            + NAME + " TEXT" + ","
            + COUNTRY + " TEXT" + ","
            + DESCRIPTION + " TEXT" + ","
            + IMAGE_URL + " TEXT"
            + ")";
}