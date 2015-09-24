package com.curso.worldwonders.sync;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Junior on 24/09/2015.
 */
public class SyncService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        
        SyncBase base = null;
        Bundle data = intent.getExtras();

        String command = data.getString("Command");

        switch (command){
            case "Wonders":
                base = new WondersSync(this);
                break;
        }

        base.Sync(intent);
        return super.onStartCommand(intent, flags, startId);
    }
}
