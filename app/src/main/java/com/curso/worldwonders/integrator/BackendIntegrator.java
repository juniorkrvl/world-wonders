package com.curso.worldwonders.integrator;

import android.content.Context;

import com.curso.worldwonders.infrastructure.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Junior on 10/09/2015.
 */
public class BackendIntegrator {

    public OperationResult executeOperation(BackendOperationDescriptor descriptor)
    {
        HttpsURLConnection connection = null;
        OperationResult<JSONObject> result = new OperationResult<JSONObject>();
        try {
            connection = (HttpsURLConnection) new URL(descriptor.endpoint).openConnection();
            connection.setRequestMethod(descriptor.requestMethod);
            if (descriptor.connectionTimeout > 0){
                connection.setConnectTimeout(descriptor.connectionTimeout);
            }
            connection.setRequestProperty("X-Parse-Application-Id", "xf4nl2pBvNEkGADXxuN8yLzRchlvTqBoOWV2oCg0");
            connection.setRequestProperty("X-Parse-REST-API-Key", "ZdZ40VdqX1SJyUoGMvJOYWAnw53zyhjsFlXfF2Rh");
            connection.setRequestProperty("Content-Type", "application/json");

            //connection.setRequestProperty("X-Parse-Session-Token","r:pnktnjyb996sj4p156gjtp4im");



            connection.setDoInput(true);

            if (descriptor.requestMethod == "POST"){
                connection.setDoOutput(true);
                BufferedOutputStream outStream = new BufferedOutputStream(connection.getOutputStream());
                outStream.write(descriptor.bodyParameters.toString().getBytes());
                outStream.flush();
                outStream.close();
            }

            BufferedInputStream stream = new BufferedInputStream(connection.getInputStream());
            Scanner scanner = new Scanner(stream);
            scanner.useDelimiter("\\A");
            String responseData = "";
            if (scanner.hasNext()) {
                responseData = scanner.next();
            }

            result.code = connection.getResponseCode();
            result.result = new JSONObject(responseData);

        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            result.code = Constants.ErrorCodes.TIME_OUT;
            result.result = null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            result.code = Constants.ErrorCodes.GENERIC_ERROR;
            result.result = null;
        } catch (IOException e) {
            e.printStackTrace();
            result.code = Constants.ErrorCodes.GENERIC_ERROR;
            result.result = null;
        } catch (JSONException e) {
            e.printStackTrace();
            result.code = Constants.ErrorCodes.GENERIC_ERROR;
            result.result = null;
        }

        return result;
    }
}
