package info.androidhive.phonebook.data.callbacks;

/**
 * Created by sahil-mac on 13/03/18.
 */

public class Error {


    private final int errorCode;
    private final String errorMessage;


    public Error(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
