package info.androidhive.phonebook.data;

import android.content.Context;
import android.support.annotation.NonNull;

import info.androidhive.phonebook.data.repositories.UserRepo;
import info.androidhive.phonebook.data.sources.UserDataSource;
import info.androidhive.phonebook.data.sources.api.UserDataSourceImpl;

/**
 * Created by sahil-mac on 13/03/18.
 */

public final class Injection {

    public static UserRepo provideUserRepo(@NonNull Context context) {
        return new UserRepo(UserDataSourceImpl.getInstance(context.getApplicationContext()));
    }
}
