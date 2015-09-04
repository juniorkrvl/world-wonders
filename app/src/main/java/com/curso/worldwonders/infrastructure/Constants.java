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
    }

}
