package info.androidhive.phonebook.usecases.contactList;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import info.androidhive.phonebook.common.GridSpacingItemDecoration;
import info.androidhive.phonebook.models.PhoneBookUser;
import info.androidhive.phonebook.R;
import info.androidhive.phonebook.data.Injection;
import info.androidhive.phonebook.data.repositories.UserRepo;
import info.androidhive.phonebook.usecases.contactDetails.ContactDetailsActivity;

public class ContactListActivity extends AppCompatActivity implements ContactListAdapter.ContactSelectedCallback, ContactListContract.View{


    private static final String TAG = "ContactListActivity";

    private RecyclerView mRecyclerView;
    private ContactListAdapter mAdapter;
    private ContactListContract.Presenter mPresenter;

    private LayoutType mLayoutType = LayoutType.LIST;

    private View sharedElement;
    private ProgressBar mProgressBar;
    private View mContactListContainer;
    private View mContactListEmptyContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        final UserRepo repo = Injection.provideUserRepo(this);
        mPresenter = new ContactListPresenter(repo, this);

        initViews();
        mPresenter.start();
    }

    private void initViews() {


        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("My Contacts");

        mProgressBar = findViewById(R.id.progress_bar);
        mContactListContainer = findViewById(R.id.container_contact_list);
        mContactListEmptyContainer = findViewById(R.id.container_no_contacts);
        mRecyclerView = findViewById(R.id.rv_contact_list);

        mAdapter = new ContactListAdapter(this);
        setupRecyclerView();
    }

    private void setupRecyclerView() {

        switch (mLayoutType) {
            case LIST:
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//                mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
                break;

            case GRID:
                mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(16), true));
        }
        mAdapter.setLayoutType(mLayoutType);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contact_list_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_show_grid :
                mLayoutType = mLayoutType == LayoutType.LIST ?
                        LayoutType.GRID :
                        LayoutType.LIST;
                        setupRecyclerView();
                        return true;

            default: return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public void setPresenter(ContactListContract.Presenter presenter) {}


    @Override
    public void showContactList(List<PhoneBookUser> contactList) {

        mContactListEmptyContainer.setVisibility(View.GONE);
        mContactListContainer.setVisibility(View.VISIBLE);

        mAdapter.setContactList(contactList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNoContactsFoundMessage() {
        mContactListEmptyContainer.setVisibility(View.VISIBLE);
        mContactListContainer.setVisibility(View.GONE);
    }

    @Override
    public void toggleProgressBar(boolean isVisible) {
        mProgressBar.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showErrorMessage(int errorCode, String errorMessage) {
        Log.e(TAG, errorCode+", "+errorMessage);
    }

    @Override
    public void showInformationalMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void closeApp() {
        finish();
    }

    @Override
    public void onBackPressed() {
        mPresenter.clickedBack();
    }

    @Override
    public void onContactSelected(View sharedElement, PhoneBookUser selectedContact) {
        this.sharedElement = sharedElement;
        mPresenter.selectedContact(selectedContact);
    }

    @Override
    public void openContactDetailsScreen(PhoneBookUser contactDetails) {

        Intent intent = new Intent(this, ContactDetailsActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, sharedElement, ViewCompat.getTransitionName(sharedElement));

        intent.putExtra(ContactDetailsActivity.EXTRA_SELECTED_CONTACT_ID, contactDetails.getId());
        startActivity(intent, options.toBundle());
    }


    public enum LayoutType { LIST, GRID }


    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}
