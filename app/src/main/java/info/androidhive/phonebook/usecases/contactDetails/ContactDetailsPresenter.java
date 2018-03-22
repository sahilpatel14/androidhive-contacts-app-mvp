package info.androidhive.phonebook.usecases.contactDetails;

import info.androidhive.phonebook.data.repositories.UserRepo;
import info.androidhive.phonebook.data.sources.UserDataSource;
import info.androidhive.phonebook.models.PhoneBookUser;

/**
 * Created by sahil-mac on 19/03/18.
 */

public class ContactDetailsPresenter implements ContactDetailsContract.Presenter {

    private final ContactDetailsContract.View mView;
    private final UserRepo mRepository;
    private final int mUserId;


    public ContactDetailsPresenter(UserRepo repository, ContactDetailsContract.View view, int userId) {
        this.mView = view;
        this.mRepository = repository;
        this.mUserId = userId;
    }

    @Override
    public void start() {

        mView.toggleProgressBar(true);
        mRepository.getUser(mUserId, new UserDataSource.UserCallback() {
            @Override
            public void onUserReceived(PhoneBookUser user) {
                mView.toggleProgressBar(false);
                mView.showContactDetails(user);
            }

            @Override
            public void onUserDeleted() {
                mView.showInformationalScreen("User deleted Successfully.");
            }

            @Override
            public void onError(int errorCode, String reason) {
                mView.toggleProgressBar(false);
                mView.showErrorMessage(errorCode, reason);
            }
        });

    }

    @Override
    public void clickedBack() {
        mView.openContactListScreen();
    }
}
