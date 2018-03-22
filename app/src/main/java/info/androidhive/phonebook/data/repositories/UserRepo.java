package info.androidhive.phonebook.data.repositories;

import info.androidhive.phonebook.data.sources.UserDataSource;

/**
 * Created by sahil-mac on 13/03/18.
 */

public class UserRepo implements UserDataSource {

    private final UserDataSource apiDataSource;

    public UserRepo(UserDataSource apiDataSource) {
        this.apiDataSource = apiDataSource;
    }

    @Override
    public void getUserList(UserListCallback callback) {
        apiDataSource.getUserList(callback);
    }

    @Override
    public void getUser(int userId, UserCallback callback) {
        apiDataSource.getUser(userId, callback);
    }

    @Override
    public void deleteUser(int userId, UserCallback callback) {
        apiDataSource.deleteUser(userId, callback);
    }
}
