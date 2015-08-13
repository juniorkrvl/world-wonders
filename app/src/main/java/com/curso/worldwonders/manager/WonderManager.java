package com.curso.worldwonders.manager;

import android.content.Context;
import android.content.CursorLoader;

import com.curso.worldwonders.database.provider.WorldWondersContentProvider;
import com.curso.worldwonders.database.tables.WondersTable;

/**
 * Created by Junior on 13/08/2015.
 */
public class WonderManager {

    private Context context;

    public WonderManager(Context context){
        this.context = context;
    }

    public CursorLoader getWondersLoader()
    {
        CursorLoader cursorLoader = new CursorLoader(context, WorldWondersContentProvider.WONDER_CONTENT_URI,
                null, null, null, null);
        return cursorLoader;
    }

}
