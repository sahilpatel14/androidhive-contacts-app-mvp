package info.androidhive.phonebook;

import com.google.gson.Gson;

import org.json.JSONObject;

import info.androidhive.phonebook.models.PhoneBookUser;

/**
 * Created by sahil-mac on 13/03/18.
 */

public final class Utils {


    public static PhoneBookUser toPhoneBookUser(JSONObject jsonUser) {
        PhoneBookUser user = new Gson().fromJson(jsonUser.toString(),PhoneBookUser.class);
        return user;
    }
}
