package estansaas.fonebayad.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import estansaas.fonebayad.R;
import estansaas.fonebayad.adapter.AdapterContactList;
import estansaas.fonebayad.model.ModelContact;
import estansaas.fonebayad.view.IndexableListView;

/**
 * Created by gerald.tayag on 11/12/2015.
 */
public class ActivitySharedContacts extends BaseActivity implements AdapterView.OnItemClickListener {

    @Bind(R.id.list_contact)
    public IndexableListView list_contact;

    private List<ModelContact> modelContactList;
    private ModelContact modelContact;
    private AdapterContactList adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_bills_contacts);
        ButterKnife.bind(this);

        modelContactList = new ArrayList<>();

        displayContacts();
        InitializeDataView();
    }

    private void displayContacts() {

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        modelContact = new ModelContact();
                        modelContact.setContact_name(name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase());
                        modelContact.setContact_no(pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                        modelContactList.add(modelContact);
                    }
                    pCur.close();
                }
            }
        }
    }

    private void InitializeDataView() {
        adapter = new AdapterContactList(this, modelContactList);
        list_contact.setAdapter(adapter);
        list_contact.setFastScrollEnabled(true);
        list_contact.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("CONTACT", modelContactList.get(position));
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.back)
    public void Back() {
        onBackPressed();
    }

    @OnClick(R.id.expanded_menu)
    public void toggleMenu() {
        showMenu();
    }
}
