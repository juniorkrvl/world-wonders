package com.curso.worldwonders.integrator;

import org.json.JSONObject;

/**
 * Created by Junior on 10/09/2015.
 */
public class BackendOperationDescriptor {
    public String requestMethod;
    public String endpoint;
    public JSONObject bodyParameters;
    public int connectionTimeout;
}
