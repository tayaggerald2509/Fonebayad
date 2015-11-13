package estansaas.fonebayad.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Scroller;

import com.devsmart.android.ui.HorizontalListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import estansaas.fonebayad.R;
import estansaas.fonebayad.adapter.AdapterShareList;
import estansaas.fonebayad.model.ModelBillInformation;
import estansaas.fonebayad.model.ModelContact;
import estansaas.fonebayad.utils.Util;

/**
 * Created by gerald.tayag on 11/12/2015.
 */
public class ActivityShareBill extends AppCompatActivity implements TextWatcher, View.OnFocusChangeListener, View.OnTouchListener {

    public static final int REQUEST_CONTACT_LIST = 201;

    @Bind(R.id.shared_list)
    public HorizontalListView shared_list;

    @Bind(R.id.txtContactPerson)
    public EditText txtContactPerson;

    @Bind(R.id.txtContactNo)
    public EditText txtContactNo;

    @Bind(R.id.chk_view)
    public RadioButton chk_view;

    @Bind(R.id.chk_view_pay)
    public RadioButton chk_view_pay;

    @Bind(R.id.txtAddMessage)
    public EditText txtAddMessage;

    public static List<ModelBillInformation> billStatements;
    private ModelContact modelContact;
    private AdapterShareList adapterShareList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_bills);
        ButterKnife.bind(this);

        //billStatements = ModelBillInformation.getBillStatement();
        InitializeDataView();
    }

    @OnClick(R.id.ll_view_pay)
    public void View() {
        chk_view.setChecked(false);
        chk_view_pay.setChecked(true);
    }


    @OnClick(R.id.ll_view)
    public void ViewAndPay() {
        chk_view.setChecked(true);
        chk_view_pay.setChecked(false);
    }

    private void InitializeDataView() {
        txtAddMessage.setOnTouchListener(this);
        txtAddMessage.setScroller(new Scroller(this));
        txtAddMessage.setVerticalScrollBarEnabled(true);
        txtAddMessage.setMovementMethod(new ScrollingMovementMethod());
        txtAddMessage.addTextChangedListener(this);
        adapterShareList = new AdapterShareList(this, billStatements);
        shared_list.setAdapter(adapterShareList);
    }

    @OnClick(R.id.show_contacts)
    public void toggleContacts() {
        Intent intent = new Intent(this, ActivitySharedContacts.class);
        startActivityForResult(intent, REQUEST_CONTACT_LIST);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            // position the text type in the left top corner
            txtAddMessage.setGravity(Gravity.LEFT | Gravity.TOP);
        } else {
            // no text entered. Center the hint text.
            txtAddMessage.setGravity(Gravity.CENTER);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.txtAddMessage:
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                break;
        }
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.txtAddMessage:
                if (hasFocus) {
                    txtAddMessage.setGravity(Gravity.LEFT | Gravity.TOP);
                } else {
                    txtAddMessage.setGravity(Gravity.CENTER);
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_CONTACT_LIST:
                if (resultCode == Activity.RESULT_OK) {
                    modelContact = (ModelContact) data.getSerializableExtra("CONTACT");
                    txtContactPerson.setText(modelContact.getContact_name());
                    txtContactNo.setText(modelContact.getContact_no());
                }
                break;
        }
    }

    @OnClick(R.id.back)
    public void Back() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        finish();
        Util.startNextActivity(this, ActivityMyBills.class);
    }
}
