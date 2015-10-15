package com.curso.worldwonders.sync;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import com.curso.worldwonders.R;
import com.curso.worldwonders.entity.Wonder;
import com.curso.worldwonders.infrastructure.Constants;
import com.curso.worldwonders.infrastructure.OperationListener;
import com.curso.worldwonders.manager.WonderManager;
import com.curso.worldwonders.ui.MainActivity;

import java.util.List;

/**
 * Created by Junior on 24/09/2015.
 */
public class WondersSync extends SyncBase {

    public WondersSync(Context context) {
        super(context);
    }

    @Override
    public void Sync(Intent intent) {
        super.Sync(intent);

        Bundle extras = intent.getExtras();

       final ResultReceiver receiver = extras.getParcelable("Receiver");

        final Bundle bundleResult = new Bundle();

        WonderManager manager = new WonderManager(_context);
        manager.getWondersFromServer(new OperationListener<List<Wonder>>() {
            @Override
            public void onSuccess(List<Wonder> result) {
                bundleResult.putString("Message","Sucesso!");
                receiver.send(Constants.Service.SUCCESS, bundleResult);
                //item.setActionView(null);


                // Create the Builder
                NotificationCompat.Builder mBuilder = new
                        NotificationCompat.Builder(_context)
                        .setAutoCancel(true)
                        .setTicker(_context.getResources().getString(R.string.not_wonders_sync_ticker, result.size()))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(_context.getResources().getString(R.string.not_wonders_sync_title, result.size()))
                        .setContentText(_context.getResources().getString(R.string.not_wonders_sync_text, result.size()));

                // Create the Intent to be dispatch by SO on Notification click
                Intent notificationIntent = new Intent(_context, MainActivity.class);

                // Create the task stack
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(_context);
                stackBuilder.addNextIntent(notificationIntent);

                // Create the PendingIntent
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(resultPendingIntent);

                // Create the Notification
                NotificationManager notificationManager = (NotificationManager)
                        _context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(1, mBuilder.build());

            }

            @Override
            public void onError(int error) {

                String description = "";
                switch (error) {
                    case Constants.ErrorCodes.GENERIC_ERROR:
                        description = "Generic error!";
                        break;
                    case Constants.ErrorCodes.TIME_OUT:
                        description = "Timeout error!";
                        break;
                }

                bundleResult.putString("Message",description);
                receiver.send(Constants.Service.ERROR, bundleResult);
                //Toast.makeText(_context, description, Toast.LENGTH_SHORT).show();
            }
        });
        //return false;

    }
}
