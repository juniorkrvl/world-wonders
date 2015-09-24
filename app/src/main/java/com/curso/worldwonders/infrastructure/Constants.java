package com.curso.worldwonders.infrastructure;

/**
 * Created by Junior on 30/07/2015.
 */
public class Constants {

    public static interface IntentConsts {
        public static final String EXTRA_EMAIL = "email";
        public static final String EXTRA_PASSWORD = "password";
        public static final String EXTRA_NAME = "name";
        public static final String EXTRA_USER = "user";
        public static final String EXTRA_WONDER = "wonder";
        public static final int REQUEST_CODE_LOGIN = 1;
    }

    public static interface SharedPrefsConsts {
        public static final String PREFS_NAME = "general_prefs";
        public static final String KEY_EMAIL = "email";
        public static final String KEY_PASSWORD = "password";
        public static final String KEY_NAME = "name";
        public static final String KEY_LANGUAGE = "language";
        public static final String KEY_LOGGED = "logged";
        public static final String KEY_TOKEN = "token";
    }

    public static interface ErrorCodes {
        public static final int TIME_OUT = 1;
        public static final int SERVER_ERROR = 2;
        public static final int GENERIC_ERROR = 3;
    }

    public  static interface Service{
        public static final int ERROR = 0;
        public static final int SUCCESS = 1;
    }

}
