package info.androidhive.phonebook.usecases.contactDetails;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import info.androidhive.phonebook.models.PhoneBookUser;
import info.androidhive.phonebook.R;
import info.androidhive.phonebook.data.Injection;
import info.androidhive.phonebook.data.repositories.UserRepo;
import info.androidhive.phonebook.data.sources.UserDataSource;

public class ContactDetailsActivity extends AppCompatActivity
        implements ContactDetailsContract.View {

    private static final String TAG = "ContactDetailsActivity";
    public static final String EXTRA_SELECTED_CONTACT_ID = "INTENT_CONTACT_ID";

    private UserRepo mUserRepo;
    private ContactDetailsContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        Intent intent = getIntent();
        int userId = 0;

        try {
            if (intent != null) {
                userId = intent.getIntExtra(EXTRA_SELECTED_CONTACT_ID, 0);

                if (userId == 0) {
                    throw new RuntimeException();
                }
            }
        }
        catch (Exception e) {
            Log.e(TAG, "onCreate: User id not found to fetch data.", e);
        }


        mUserRepo = Injection.provideUserRepo(this);
        mPresenter = new ContactDetailsPresenter(mUserRepo, this, userId);

        mPresenter.start();
    }


    private void setUserData(PhoneBookUser user) {

        ImageView ivProfilePic = findViewById(R.id.iv_details_contact_picture);
        TextView tvPhoneNumber = findViewById(R.id.tv_contact_phone_number);
        TextView tvEmailId = findViewById(R.id.tv_contact_email_id);
        TextView tvFullName = findViewById(R.id.tv_contact_full_name);
        TextView tvGenderAge = findViewById(R.id.tv_contact_gender_age);
        TextView tvDob = findViewById(R.id.tv_contact_dob);
        TextView tvLocation = findViewById(R.id.tv_contact_location);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivProfilePic.setTransitionName("trans_" + user.getId());
        }

        Glide.with(ContactDetailsActivity.this)
                .load(user.getPhoto())
                .apply(RequestOptions.centerCropTransform())
                .into(ivProfilePic);

        tvPhoneNumber.setText(user.getPhone());
        tvEmailId.setText(user.getEmail());
        tvFullName.setText(TextUtils.concat(user.getName()," ", user.getSurname()));


        //  Capitalizing gender
        String gender = TextUtils.concat(user.getGender().substring(0,1).toUpperCase(), user.getGender().substring(1)).toString();
        String age = TextUtils.concat(String.valueOf(user.getAge())).toString();

        tvGenderAge.setText(TextUtils.concat(gender,", ", age));
        tvDob.setText(user.getBirthday().get("dmy"));

        tvLocation.setText(user.getRegion());
    }

    @Override
    public void setPresenter(ContactDetailsContract.Presenter presenter) {}

    @Override
    public void showContactDetails(PhoneBookUser contact) {
        setUserData(contact);
    }

    @Override
    public void toggleProgressBar(boolean isVisible) {

    }

    @Override
    public void showErrorMessage(int errorCode, String errorMessage) {

    }

    @Override
    public void showInformationalScreen(String message) {

    }

    @Override
    public void openContactListScreen() {
        onBackPressed();
    }
}
