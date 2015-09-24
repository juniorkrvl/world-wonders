package com.curso.worldwonders.sync;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Junior on 24/09/2015.
 */
public abstract class SyncBase {

    protected Context _context;

    public SyncBase(Context context) {
        this._context = context;
    }

    public void Sync(Intent intent)
    {}

}
