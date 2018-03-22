package info.androidhive.phonebook.usecases.contactDetails;

import info.androidhive.phonebook.common.architecture.BasePresenter;
import info.androidhive.phonebook.common.architecture.BaseView;
import info.androidhive.phonebook.models.PhoneBookUser;

/**
 * Created by sahil-mac on 19/03/18.
 */

public interface ContactDetailsContract {

    interface View extends BaseView<Presenter> {

        void showContactDetails(PhoneBookUser contact);

        void toggleProgressBar(boolean isVisible);

        void showErrorMessage(int errorCode, String errorMessage);

        void showInformationalScreen(String message);

        void openContactListScreen();
    }

    interface Presenter extends BasePresenter {

        void clickedBack();
    }

}
