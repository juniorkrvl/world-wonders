package com.curso.worldwonders.infrastructure;

/**
 * Created by Junior on 10/09/2015.
 */
public interface OperationListener<T> {
    public void onSuccess(T result);
    public void onError(int error);
}

