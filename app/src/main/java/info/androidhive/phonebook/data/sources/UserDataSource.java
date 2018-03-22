package info.androidhive.phonebook.data.sources;

import java.util.List;

import info.androidhive.phonebook.models.PhoneBookUser;
import info.androidhive.phonebook.data.callbacks.BaseCallback;

/**
 * Created by sahil-mac on 13/03/18.
 */

public interface UserDataSource {



    void getUserList(UserListCallback callback);

    void getUser(int userId, UserCallback callback);

    void deleteUser(int userId, UserCallback callback);

    interface UserListCallback extends BaseCallback{
        void onUserListReceived(List<PhoneBookUser> userList);
    }

    interface UserCallback extends BaseCallback{
        void onUserReceived(PhoneBookUser user);
        void onUserDeleted();
    }
}
