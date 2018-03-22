package info.androidhive.phonebook.usecases.contactList;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import info.androidhive.phonebook.models.PhoneBookUser;
import info.androidhive.phonebook.R;
import info.androidhive.phonebook.common.RoundedImageView;
import static info.androidhive.phonebook.usecases.contactList.ContactListActivity.LayoutType.GRID;
import static info.androidhive.phonebook.usecases.contactList.ContactListActivity.LayoutType.LIST;
import static info.androidhive.phonebook.usecases.contactList.ContactListActivity.LayoutType;

/**
 * Created by sahil-mac on 13/03/18.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactItemViewHolder> {

    private static final String TAG = "ContactListAdapter";

    private Context context;
    private List<PhoneBookUser> contactList;
    private ContactSelectedCallback callback;

    private LayoutType mLayoutType = LIST;

    ContactListAdapter(Context context) {

        this.context = context;

        try {
            callback = (ContactSelectedCallback) context;
        }
        catch (ClassCastException e) {
            Log.e(TAG, "ContactListAdapter: Interface not implemented.", e);
        }

    }

    ContactListAdapter(Context context, ContactSelectedCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    void setContactList(List<PhoneBookUser> contactList) {
        this.contactList = contactList;
    }

    void setLayoutType(LayoutType layoutType) {
        mLayoutType = layoutType;
    }


    @Override
    public ContactItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(context)
                .inflate(mLayoutType == LIST ?
                        R.layout.item_phonebook_contact_list :
                        R.layout.item_phonebook_contact_list_grid, parent, false);
        return new ContactItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ContactItemViewHolder holder, int position) {

        final PhoneBookUser phoneBookUser = contactList.get(position);
        holder.onBind(phoneBookUser);
    }

    @Override
    public int getItemCount() {
        return contactList == null ? 0 : contactList.size();
    }

    final class ContactItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivContactPicture;
        private TextView tvContactName;
        private TextView tvContactRegion;
        private PhoneBookUser phoneBookUser;

        ContactItemViewHolder(View itemView) {
            super(itemView);

            ivContactPicture = itemView.findViewById(R.id.iv_item_contact_pic);
            tvContactName = itemView.findViewById(R.id.tv_item_contact_name);
            tvContactRegion = itemView.findViewById(R.id.tv_item_contact_region);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            callback.onContactSelected(ivContactPicture, phoneBookUser);
        }

        public void onBind(PhoneBookUser phoneBookUser) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                ivContactPicture.setTransitionName("trans_"+phoneBookUser.getId());
            }

            this.phoneBookUser = phoneBookUser;
            tvContactName.setText(String.format("%s %s", phoneBookUser.getName(), phoneBookUser.getSurname()));
            tvContactRegion.setText(phoneBookUser.getRegion());
            RequestBuilder builder = Glide.with(context)
                    .load(phoneBookUser.getPhoto())
                    .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_account_circle_black_24dp)
                        .fitCenter()
                    );
            if (mLayoutType == LIST) {
                builder = builder.apply(RequestOptions.circleCropTransform());
            }
            builder.into(ivContactPicture);
        }
    }

    interface ContactSelectedCallback {
        void onContactSelected(View sharedElement, PhoneBookUser selectedContact);
    }
}
