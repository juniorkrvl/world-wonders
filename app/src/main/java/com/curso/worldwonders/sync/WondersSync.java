package com.curso.worldwonders.sync;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.widget.Toast;

import com.curso.worldwonders.entity.Wonder;
import com.curso.worldwonders.infrastructure.Constants;
import com.curso.worldwonders.infrastructure.OperationListener;
import com.curso.worldwonders.manager.WonderManager;

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
