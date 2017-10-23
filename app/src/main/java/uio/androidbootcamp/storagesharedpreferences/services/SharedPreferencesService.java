package uio.androidbootcamp.storagesharedpreferences.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import uio.androidbootcamp.storagesharedpreferences.R;
import uio.androidbootcamp.storagesharedpreferences.models.User;

public class SharedPreferencesService {

    private Context context;

    public SharedPreferencesService(Context context) {
        this.context = context;
    }

    public  void saveStringInSharedPreferences(String key, String value){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.packageName), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void saveLongInSharedPreferences(String key, Long value){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.packageName), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public String getStringFromSharedPreferences(String key){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.packageName), Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }

    public Long getLongFromSharedPreferences(String key){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(R.string.packageName), Context.MODE_PRIVATE);
        return sharedPref.getLong(key, 0);
    }

    public void saveUserInSharedPreferences(String key, User value){
        Gson gson = createGsonObject();
        String gsonValue = gson.toJson(value);
        saveStringInSharedPreferences(key, gsonValue);
    }

    public User obtainUserFromSharedPreferences(String key){
        String gsonValue = getStringFromSharedPreferences(key);
        Gson gson = createGsonObject();
        return gson.fromJson(gsonValue, User.class);
    }

    public void saveUsersInSharedPreferences(String key, List<User> value){
        Gson gson = createGsonObject();
        String jsonValue = gson.toJson(value);
        saveStringInSharedPreferences(key,jsonValue);
    }

    public List<User> getUsersFromSharedPreferences(String key){
        Gson gson = createGsonObject();
        Type listType = new TypeToken<ArrayList<User>>(){}.getType();
        String gsonValue = getStringFromSharedPreferences(key);
        return new Gson().fromJson(gsonValue, listType);
    }

    @NonNull
    private static Gson createGsonObject() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();
    }

}