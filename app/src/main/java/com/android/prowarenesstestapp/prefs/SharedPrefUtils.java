package com.android.prowarenesstestapp.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPrefUtils {

    public static final String TAG = SharedPrefUtils.class.getName();
    public static final String SHARED_PREF_NAME = "Prowareness";
    public static final int PRIVATE_MODE = 0;

    private static SharedPreferences _pref;
    private static SharedPrefUtils _instance;
    private static String strDefaultValue="";
    private static int intDefaultValue=0;
    private static boolean booleanDefaultValue=false;

    public SharedPrefUtils() {
    }


    public static SharedPrefUtils getInstance(Context context) {
        if (_pref == null) {
            _pref = context.getSharedPreferences(SHARED_PREF_NAME, PRIVATE_MODE);
        }
        if (_instance == null) {
            _instance = new SharedPrefUtils();
        }
        return _instance;
    }


    /**
     * This Method Clear shared preference.
     */
    public void clear() {
        SharedPreferences.Editor editor = _pref.edit();
        editor.clear();
        editor.commit();
    }

    public void setString(String key, String value) {
        if (key != null && value != null) {
            try {
                if (_pref != null) {
                    SharedPreferences.Editor editor = _pref.edit();
                    editor.putString(key, value);
                    editor.apply();
                }
            } catch (Exception e) {
                Log.d(TAG, "Exception:", e);
            }
        }
    }

    public void setLong(String key, long value) {
        if (key != null) {
            try {
                if (_pref != null) {
                    SharedPreferences.Editor editor = _pref.edit();
                    editor.putLong(key, value);
                    editor.commit();
                }
            } catch (Exception e) {
                Log.d(TAG, "Exception:", e);
            }
        }
    }


    public void setInt(String key, int value) {
        if (key != null) {
            try {
                if (_pref != null) {
                    SharedPreferences.Editor editor = _pref.edit();
                    editor.putInt(key, value);
                    editor.commit();
                }
            } catch (Exception e) {
                Log.d(TAG, "Exception:", e);
            }
        }
    }


    public void setDouble(String key, double value) {
        if (key != null) {
            try {
                if (_pref != null) {
                    SharedPreferences.Editor editor = _pref.edit();
                    editor.putFloat(key, (float) value);
                    editor.commit();
                }
            } catch (Exception e) {
                Log.d(TAG, "Exception:", e);
            }
        }
    }


    public void setBoolean(String key, boolean value) {
        if (key != null) {
            try {
                if (_pref != null) {
                    SharedPreferences.Editor editor = _pref.edit();
                    editor.putBoolean(key, value);
                    editor.commit();
                }
            } catch (Exception e) {
                Log.d(TAG, "Exception:", e);
            }
        }
    }

    public int getInt(String key, int defaultValue) {
        if (_pref != null && key != null && _pref.contains(key)) {
            return _pref.getInt(key, defaultValue);
        }
        return defaultValue;
    }

    public long getLong(String key, long defaultValue) {
        if (_pref != null && key != null && _pref.contains(key)) {
            return _pref.getLong(key, defaultValue);
        }
        return defaultValue;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        if (_pref != null && key != null && _pref.contains(key)) {
            return _pref.getBoolean(key, defaultValue);
        }
        return defaultValue;
    }


    public boolean isNull(String key) {
        return !(_pref != null && key != null && _pref.contains(key));

    }

    public String getString(String key, String defaultValue) {
        if (_pref != null && key != null && _pref.contains(key)) {
            return _pref.getString(key, defaultValue);
        }
        return defaultValue;
    }

    public double getDouble(String key, double defaultValue) {
        if (_pref != null && key != null && _pref.contains(key)) {
            return _pref.getFloat(key, (float) defaultValue);
        }
        return defaultValue;
    }


    public void removeString(String key) {
        if (key != null) {
            try {
                if (_pref != null && _pref.contains(key)) {
                    SharedPreferences.Editor editor = _pref.edit();
                    editor.remove(key);
                    editor.apply();
                }
            } catch (Exception e) {
                Log.d(TAG, "Exception:", e);
            }
        }
    }

    public final class Key {
        public static final String ITEM_POSITION = "itemPosition";
        public static final String LOGIN_STATE = "loginState";
        public static final String USER_ID = "userId";
        public static final String USERNAME = "userName";
        public static final String TICKET_TYPE = "ticketType";

    }

}

