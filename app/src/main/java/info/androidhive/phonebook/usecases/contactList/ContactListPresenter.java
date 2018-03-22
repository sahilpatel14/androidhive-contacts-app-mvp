package info.androidhive.phonebook.usecases.contactList;

import java.util.List;

import info.androidhive.phonebook.models.PhoneBookUser;
import info.androidhive.phonebook.data.repositories.UserRepo;
import info.androidhive.phonebook.data.sources.UserDataSource;

/**
 * Created by sahil-mac on 19/03/18.
 */

public class ContactListPresenter implements ContactListContract.Presenter {

    private final UserRepo mRepository;

    private final ContactListContract.View mView;

    public ContactListPresenter(UserRepo repository, ContactListContract.View view) {
        this.mRepository = repository;
        this.mView = view;

        mView.setPresenter(this);
    }

    @Override
    public void start() {
        mView.toggleProgressBar(true);
        mRepository.getUserList(new UserDataSource.UserListCallback() {
            @Override
            public void onUserListReceived(List<PhoneBookUser> userList) {
                mView.toggleProgressBar(false);

                if (userList.isEmpty()) {
                    mView.showNoContactsFoundMessage();
                }
                else {
                    mView.showContactList(userList);
                }
            }

            @Override
            public void onError(int errorCode, String reason) {
                mView.toggleProgressBar(false);
                mView.showErrorMessage(errorCode, reason);
                mView.showNoContactsFoundMessage();
            }
        });
    }

    @Override
    public void selectedContact(PhoneBookUser contact) {
        mView.openContactDetailsScreen(contact);
    }

    @Override
    public void clickedBack() {
        mView.closeApp();
    }
}
