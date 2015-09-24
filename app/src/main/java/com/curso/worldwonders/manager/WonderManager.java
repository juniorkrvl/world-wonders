package com.curso.worldwonders.manager;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.os.AsyncTask;

import com.curso.worldwonders.database.provider.WorldWondersContentProvider;
import com.curso.worldwonders.database.tables.WondersTable;
import com.curso.worldwonders.entity.Wonder;
import com.curso.worldwonders.infrastructure.OperationListener;
import com.curso.worldwonders.integrator.BackendIntegrator;
import com.curso.worldwonders.integrator.BackendOperationDescriptor;
import com.curso.worldwonders.integrator.OperationResult;
import com.curso.worldwonders.integrator.WorldWondersApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

    public void getWondersFromServer(final OperationListener<List<Wonder>> callback)
    {
        new AsyncTask<Void, Integer, OperationResult<List<Wonder>>>() {
            @Override
            protected OperationResult<List<Wonder>> doInBackground(Void... params) {
                ArrayList<Wonder> wonders = new ArrayList<Wonder>();
                BackendOperationDescriptor descriptor = new BackendOperationDescriptor();
                descriptor.endpoint = WorldWondersApi.Host + WorldWondersApi.GetWonderList;
                descriptor.requestMethod = "GET";
                descriptor.connectionTimeout = 30000;

                BackendIntegrator integrator = new BackendIntegrator();
                OperationResult<JSONObject> operationResult = integrator.executeOperation(descriptor);

                OperationResult<List<Wonder>> wondersResult = new OperationResult<List<Wonder>>();

                try {
                    JSONArray array = operationResult.result.optJSONArray("results");
                    for (int i=0;i<array.length();i++){
                      wonders.add(new Wonder(array.getJSONObject(i)));
                    }
                    wondersResult.result = wonders;

                    ContentResolver resolver = context.getContentResolver();
                    ContentValues[] bulkValues = new ContentValues[wonders.size()];
                    for (int i=0; i< wonders.size(); i++ ) {
                        ContentValues cv = new ContentValues();
                        Wonder w = wonders.get(i);
                        cv.put(WondersTable.ID,w.id);
                        cv.put(WondersTable.NAME,w.name);
                        cv.put(WondersTable.COUNTRY,w.country);
                        cv.put(WondersTable.DESCRIPTION,w.description);
                        cv.put(WondersTable.IMAGE_URL,w.image_url);
                        bulkValues[i] = cv;
                    }

                    resolver.bulkInsert(WorldWondersContentProvider.WONDER_CONTENT_URI,bulkValues);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return wondersResult;
            }

            @Override
            protected void onPostExecute(OperationResult<List<Wonder>> resultList) {
                if (callback == null){
                    return;
                }
                if (resultList.result != null) {
                    callback.onSuccess(resultList.result);
                } else {
                    callback.onError(resultList.code);
                }
            }
        }.execute();

    }

}
