package info.androidhive.phonebook.usecases.contactList;

import java.util.List;

import info.androidhive.phonebook.models.PhoneBookUser;
import info.androidhive.phonebook.common.architecture.BasePresenter;
import info.androidhive.phonebook.common.architecture.BaseView;

/**
 * Created by sahil-mac on 19/03/18.
 */

public interface ContactListContract {

    interface View extends BaseView<Presenter> {

        void showContactList(List<PhoneBookUser> contactList);

        void showNoContactsFoundMessage();

        void toggleProgressBar(boolean isVisible);

        void showErrorMessage(int errorCode, String errorMessage);

        void showInformationalMessage(String message);

        void openContactDetailsScreen(PhoneBookUser contactDetails);

        void closeApp();
    }

    interface Presenter extends BasePresenter {

        void selectedContact(PhoneBookUser contact);

        void clickedBack();
    }
}
