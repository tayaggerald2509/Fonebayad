package estansaas.fonebayad.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import estansaas.fonebayad.R;
import estansaas.fonebayad.auth.Responses.Response;
import estansaas.fonebayad.auth.RestClient;
import estansaas.fonebayad.model.ModelRegistration;
import estansaas.fonebayad.model.ModelSalutation;
import estansaas.fonebayad.utils.Constants;
import estansaas.fonebayad.utils.Network;
import estansaas.fonebayad.utils.Util;
import estansaas.fonebayad.view.FormSelector;
import estansaas.fonebayad.view.FormView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;

/**
 * Created by gerald.tayag on 10/5/2015.
 */
public class ActivityRegister extends AppCompatActivity implements View.OnFocusChangeListener {

    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.back)
    public ImageView back;

    @Bind(R.id.coordinatorLayout)
    public CoordinatorLayout coordinatorLayout;

    @Bind(R.id.lblTitle)
    public TextView lblTitle;
    @Bind(R.id.lblPerson)
    public TextView lblPerson;
    @Bind(R.id.salutation)
    public FormSelector<ModelSalutation> salutation;
    @Bind(R.id.txtFname)
    public FormView txtFname;
    @Bind(R.id.txtMname)
    public FormView txtMname;
    @Bind(R.id.txtLname)
    public FormView txtLname;

    @Bind(R.id.lblEmail)
    public TextView lblEmail;
    @Bind(R.id.txtFoneEmail)
    public FormView txtFoneEmail;
    @Bind(R.id.lblFoneEmail)
    public TextView lblFoneEmail;
    @Bind(R.id.VerifyFoneEmail)
    public ImageView VerifyFoneEmail;
    @Bind(R.id.txtPersonalEmail)
    public FormView txtPersonalEmail;
    @Bind(R.id.txtConfirmEmail)
    public FormView txtConfirmEmail;

    @Bind(R.id.lblAccount)
    public TextView lblAccount;
    @Bind(R.id.txtPin)
    public FormView txtPin;
    @Bind(R.id.txtConfirmPin)
    public FormView txtConfirmPin;

    @Bind(R.id.btnProceed)
    public Button btnProceed;

    private TextWatcher watcher;
    private EditText textview;
    private ModelRegistration registerAuthModel;
    private ModelSalutation modelSalutation;

    private List<ModelSalutation> modelSalutationList;

    public Boolean backpressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        InitializeView();
    }

    @OnClick(R.id.btnProceed)
    public void Proceed() {
        if (ValidateFields()) {
            ValidateEmails();
        }
    }

    private void InitializeView() {
        lblTitle.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        lblPerson.setTypeface(Util.setTypeface(this, Util.ROBOTO), Typeface.BOLD);
        salutation.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtFname.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtMname.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtLname.setTypeface(Util.setTypeface(this, Util.ROBOTO));

        lblEmail.setTypeface(Util.setTypeface(this, Util.ROBOTO), Typeface.BOLD);
        txtFoneEmail.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        lblFoneEmail.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtPersonalEmail.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtConfirmEmail.setTypeface(Util.setTypeface(this, Util.ROBOTO));

        lblAccount.setTypeface(Util.setTypeface(this, Util.ROBOTO), Typeface.BOLD);

        txtPin.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtConfirmPin.setTypeface(Util.setTypeface(this, Util.ROBOTO));

        btnProceed.setTypeface(Util.setTypeface(this, Util.ROBOTO), Typeface.BOLD);
        txtFoneEmail.setOnFocusChangeListener(this);

        modelSalutation = new ModelSalutation();
        modelSalutationList = new ArrayList<>();

        for (String status : getResources().getStringArray(R.array.salutation)) {
            modelSalutationList.add(new ModelSalutation(status));
        }

        salutation.setItems(modelSalutationList);
        salutation.setOnItemSelectedListener(new FormSelector.OnItemSelectedListener<ModelSalutation>() {
            @Override
            public void onItemSelectedListener(ModelSalutation item, int selectedIndex) {
                salutation.setError(null, Util.resizeDrawable(ActivityRegister.this, R.drawable.ic_keyboard_arrow_down_black_48dp));
            }
        });

        salutation.setOnFocusChangeListener(this);
    }

    private void RegistrationAuth(final DialogInterface dialogInterface) {
        registerAuthModel = new ModelRegistration();
        registerAuthModel.setUser_email(txtPersonalEmail.getText().toString());
        registerAuthModel.setUser_pin(txtConfirmPin.getText().toString());
        registerAuthModel.setUser_salutation(salutation.getText().toString());
        registerAuthModel.setUser_fname(txtFname.getText().toString());
        registerAuthModel.setUser_lname(txtLname.getText().toString());
        registerAuthModel.setUser_mname(txtMname.getText().toString());
        registerAuthModel.setUser_guid(Util.getGUID(this));
        registerAuthModel.setDevice_type(Util.getDeviceType());
        registerAuthModel.setDevice_name(Util.getDeviceName());
        registerAuthModel.setUser_username(txtFoneEmail.getText().toString());

        if (Network.isConnected(this)) {

            Call<Response> responseCall = RestClient.get().mobileRegistration(registerAuthModel);

            responseCall.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(retrofit.Response<Response> response, Retrofit retrofit) {
                    dialogInterface.dismiss();
                    if (response.isSuccess()) {
                        if (response.body().getStatus().contains(Constants.STATUS_ACCEPTED)) {
                            finish();
                            Util.startNextActivity(ActivityRegister.this, ActivityRegisterSuccess.class);
                        } else if (response.body().getStatus().contains(Constants.STATUS_LOOP)) {
                            Util.ShowMessage(coordinatorLayout, "You have an existing account on this phone.");
                        }
                    } else {
                        Util.ShowMessageWithAction(coordinatorLayout, "Failed to connect to server!", "retry", Constants.REGISTRATION);
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    dialogInterface.dismiss();
                    Util.ShowMessageWithAction(coordinatorLayout, "Failed to connect to server!", "retry", Constants.REGISTRATION);
                }
            });
        } else {
            Util.ShowMessage(coordinatorLayout, "Please connect to internet");
        }
    }

    private TextWatcher fieldWatcher(final EditText formView) {
        watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    formView.setError(null, Util.resizeDrawable(ActivityRegister.this, R.drawable.ic_action_ic_clear_white_48dp));
                } else {
                    formView.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        return watcher;
    }

    private Boolean ValidateFields() {
        for (int i = 0; i < Util.Registration.length; i++) {
            textview = (EditText) findViewById(Util.Registration[i]);
            textview.addTextChangedListener(fieldWatcher(textview));
            if (textview.getText().toString() == null || textview.getText().toString().compareTo("") == 0) {
                Util.ShowMessage(coordinatorLayout, "All fields are required Except Middle name!");
                textview.setError(null, Util.resizeDrawable(this, R.drawable.ic_action_ic_clear_white_48dp));
                textview.requestFocus();
                return false;
            } else {
                textview.setError(null);
            }
        }
        return true;
    }

    private void ValidateEmails() {
        if (Util.EmailValidator(txtPersonalEmail.getText().toString())) {
            if (txtPersonalEmail.getText().toString().compareTo(txtConfirmEmail.getText().toString()) == 0) {
                if (Network.isConnected(this)) {
                    CheckPersonalEmail();
                } else {
                    Util.ShowMessage(coordinatorLayout, "No Internet Connection!");
                }
            } else {
                txtPersonalEmail.requestFocus();
                Util.ShowMessage(coordinatorLayout, "Personal email does'nt match!");
            }
        } else {
            txtPersonalEmail.setError(null, Util.resizeDrawable(this, R.drawable.ic_action_ic_clear_white_48dp));
            Util.ShowMessage(coordinatorLayout, "Invalid personal email format!");
        }
    }

    private void checkFoneEmails() {
        retrofit.Call<Response> responseCall = RestClient.get().checkFonebayadEmail(txtFoneEmail.getText().toString());
        responseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(retrofit.Response<Response> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    VerifyFoneEmail.setVisibility(View.VISIBLE);
                    if (response.body().getStatus().contains(Constants.STATUS_OK)) {
                        //CheckPersonalEmail();
                        VerifyFoneEmail.setImageDrawable(Util.resizeDrawable(ActivityRegister.this, R.drawable.ic_action_ic_done_white_48dp));
                    } else if (response.body().getStatus().contains(Constants.STATUS_CONFLICT)) {
                        VerifyFoneEmail.setImageDrawable(Util.resizeDrawable(ActivityRegister.this, R.drawable.ic_action_ic_clear_white_48dp));
                        Util.ShowMessage(coordinatorLayout, "Sorry fonebayad email already exist!");
                    }
                } else {
                    Util.ShowMessage(coordinatorLayout, "Failed to connect to server!");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Util.ShowMessage(coordinatorLayout, "Failed to connect to server!");
            }
        });

    }

    private void CheckPersonalEmail() {
        retrofit.Call<Response> responseCall = RestClient.get().checkPersonalEmail(txtPersonalEmail.getText().toString());
        responseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(retrofit.Response<Response> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body().getStatus().contains(Constants.STATUS_ACCEPTED)) {
                        txtPersonalEmail.setError(null, Util.resizeDrawable(ActivityRegister.this, R.drawable.ic_action_ic_done_white_48dp));
                        ValidateAccount();
                    } else if (response.body().getStatus().contains(Constants.STATUS_CONFLICT)) {
                        txtPersonalEmail.setError(null, Util.resizeDrawable(ActivityRegister.this, R.drawable.ic_action_ic_clear_white_48dp));
                        Util.ShowMessage(coordinatorLayout, "Sorry your Personal email has been used!");
                    } else {
                        txtPersonalEmail.setError(null, Util.resizeDrawable(ActivityRegister.this, R.drawable.ic_action_ic_clear_white_48dp));
                    }
                } else {
                    Util.ShowMessageWithAction(coordinatorLayout, "Failed to connect to server!", "retry", Constants.REGISTRATION);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Util.ShowMessageWithAction(coordinatorLayout, "Failed to connect to server!", "retry", Constants.REGISTRATION);
            }
        });
    }

    private void ValidateAccount() {
        if (txtPin.getText().length() == 4) {
            if (txtPin.getText().toString().compareTo(txtConfirmPin.getText().toString()) != 0) {
                txtPin.requestFocus();
                txtPin.setError(null, Util.resizeDrawable(this, R.drawable.ic_action_ic_clear_white_48dp));
                txtConfirmPin.setError(null, Util.resizeDrawable(this, R.drawable.ic_action_ic_clear_white_48dp));
                Util.ShowMessage(coordinatorLayout, "Account PIN does'nt match!");
            } else {
                new MaterialDialog.Builder(this)
                        .title(R.string.action_signing)
                        .content(R.string.action_wait)
                        .contentGravity(GravityEnum.CENTER)
                        .theme(Theme.DARK)
                        .widgetColor(Color.WHITE)
                        .progressIndeterminateStyle(false)
                        .progress(true, 0)
                        .cancelable(false)
                        .showListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                RegistrationAuth(dialogInterface);
                            }
                        }).show();
            }
        } else {
            txtPin.requestFocus();
            txtPin.setError(null, Util.resizeDrawable(this, R.drawable.ic_action_ic_clear_white_48dp));
            Util.ShowMessage(coordinatorLayout, "Invalid PIN length");
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        switch (v.getId()) {
            case R.id.txtFoneEmail:
                if (!hasFocus) {
                    if (Network.isConnected(this)) {
                        checkFoneEmails();
                    } else {
                        Util.ShowMessage(coordinatorLayout, "Please connect to internet");
                    }

                }
                break;
            default:
                break;
        }


    }

    @OnClick(R.id.back)
    public void Back() {
        finish();
    }


    @Override
    public void onBackPressed() {

        if (backpressed) {
            super.onBackPressed();
            return;
        }

        backpressed = true;
        Toast.makeText(ActivityRegister.this, "Please once again to exit!", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backpressed = false;
            }
        }, 2000);
    }
}
