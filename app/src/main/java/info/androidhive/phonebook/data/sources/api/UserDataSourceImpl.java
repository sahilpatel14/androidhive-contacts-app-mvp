package info.androidhive.phonebook.data.sources.api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.phonebook.models.PhoneBookUser;
import info.androidhive.phonebook.Utils;
import info.androidhive.phonebook.data.sources.UserDataSource;

/**
 * Created by sahil-mac on 13/03/18.
 */

public class UserDataSourceImpl implements UserDataSource {

    private static final String TAG = "UserDataSourceImpl";

    private List<PhoneBookUser> userList = new ArrayList<>();
    private RequestQueue requestQueue;

    private static UserDataSourceImpl INSTANCE;
    public static UserDataSourceImpl getInstance(@NonNull Context context) {

        if (INSTANCE == null) {
            INSTANCE = new UserDataSourceImpl(context);
        }
        return INSTANCE;
    }

    private UserDataSourceImpl(Context context) {

        //  This is going to be application context.
        requestQueue = Volley.newRequestQueue(context);
    }

    @Override
    public void getUserList(final UserListCallback callback) {

        String url = "https://uinames.com/api/?ext&amount=25";

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray userJsonArray) {

                Log.d(TAG, "Received response for user api call and it has "+userJsonArray.length()+" items.");
                userList = new ArrayList<>(userJsonArray.length());

                for (int i = 0; i < userJsonArray.length(); i++) {
                    userList.add(Utils.toPhoneBookUser(userJsonArray.optJSONObject(i)));
                }

                callback.onUserListReceived(userList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error while making api call. ", error);
                callback.onError(1, "An error occurred while making the api call.");
            }
        });

        requestQueue.add(jsonArrayRequest);

    }

    @Override
    public void getUser(int userId, UserCallback callback) {

        if (userList.isEmpty()) {
            callback.onError(3, "User List is empty.");
            return;
        }

        PhoneBookUser user = getUserFromList(userId);

        if (user == null) {
            //  Invalid userId
            callback.onError(3, "User id does not exist.");
        } else {
            callback.onUserReceived(user);
        }
    }

    @Nullable
    private PhoneBookUser getUserFromList(int userId) {

        for (PhoneBookUser user : userList) {
            if (user.getId() == userId) {
                return user;
            }
        }

        return null;
    }

    @Override
    public void deleteUser(int userId, UserCallback callback) {

        PhoneBookUser userToDelete = getUserFromList(userId);

        if (userToDelete == null) {
            callback.onError(3, "User does not exist.");
        }
        else {
            callback.onUserDeleted();
        }

    }
}
