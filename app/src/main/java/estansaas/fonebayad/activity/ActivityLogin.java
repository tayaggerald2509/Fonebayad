package estansaas.fonebayad.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.pass.Spass;
import com.samsung.android.sdk.pass.SpassFingerprint;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import estansaas.fonebayad.R;
import estansaas.fonebayad.auth.Responses.ResponseLogin;
import estansaas.fonebayad.auth.RestClient;
import estansaas.fonebayad.model.ModelLogin;
import estansaas.fonebayad.utils.Constants;
import estansaas.fonebayad.utils.Network;
import estansaas.fonebayad.utils.Util;
import estansaas.fonebayad.view.PinView;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by gerald.tayag on 10/5/2015.
 */
public class ActivityLogin extends AppCompatActivity implements MaterialDialog.SingleButtonCallback {

    private static boolean AUTHENTICATED;
    private static boolean FINGERPRINT_PROVIDED = false;

    @Bind(R.id.coordinatorLayout)
    public CoordinatorLayout coordinatorLayout;
    @Bind(R.id.lblAppName)
    public TextView lblAppName;
    @Bind(R.id.txtPin)
    public PinView txtPin;
    @Bind(R.id.lblEnterPin)
    public TextView lblEnterPin;
    @Bind(R.id.lblRegister)
    public TextView lblRegister;
    @Bind(R.id.lblProblem)
    public TextView lblProblem;
    @Bind(R.id.btnLogin)
    public Button btnLogin;
    @Bind(R.id.ll_pin)
    public LinearLayout ll_pin;
    @Bind(R.id.txtQuickTour)
    public TextView txtQuickTour;

    private SpassFingerprint mSpassFingerprint;
    private Spass mSpass;
    private TextWatcher watcher = null;
    private InputMethodManager inputMethodManager;
    private Boolean backpressed = false;
    private boolean onReadyIdentify = false;
    private boolean onReadyEnroll = false;
    boolean isFeatureEnabled = false;

    @Override
    protected void onStart() {
        super.onStart();
        AUTHENTICATED = false;
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    private SpassFingerprint.IdentifyListener listener = new SpassFingerprint.IdentifyListener() {
        @Override
        public void onFinished(int eventStatus) {
            Log.i("identify finished", "reason=" + getEventStatusName(eventStatus));
            onReadyIdentify = false;
            int FingerprintIndex = 0;
            try {
                FingerprintIndex = mSpassFingerprint.getIdentifiedFingerprintIndex();
            } catch (IllegalStateException ise) {
                Log.i("Message", ise.getMessage());
            }
            if (eventStatus == SpassFingerprint.STATUS_AUTHENTIFICATION_SUCCESS) {
                AUTHENTICATED = true;
            } else if (eventStatus == SpassFingerprint.STATUS_AUTHENTIFICATION_PASSWORD_SUCCESS) {
                Log.i("onFinished ", "onFinished() : Password authentification Success");
            } else {
                Util.ShowMessage(coordinatorLayout, "Authentification Fail for identify");
            }
        }

        @Override
        public void onReady() {
            Log.i("Message", "identify state is ready");
        }

        @Override
        public void onStarted() {
            Log.i("Message", "User touched fingerprint sensor!");
        }
    };

    private SpassFingerprint.RegisterListener mRegisterListener = new SpassFingerprint.RegisterListener() {

        @Override
        public void onFinished() {
            onReadyEnroll = false;
            Log.i("Message", "RegisterListener.onFinished()");
        }
    };

    private String getEventStatusName(int eventStatus) {
        switch (eventStatus) {
            case SpassFingerprint.STATUS_AUTHENTIFICATION_SUCCESS:
                AuthenticateLogin();
                return "STATUS_AUTHENTIFICATION_SUCCESS";
            case SpassFingerprint.STATUS_AUTHENTIFICATION_PASSWORD_SUCCESS:
                return "STATUS_AUTHENTIFICATION_PASSWORD_SUCCESS";
            case SpassFingerprint.STATUS_TIMEOUT_FAILED:
                return "STATUS_TIMEOUT";
            case SpassFingerprint.STATUS_SENSOR_FAILED:
                return "STATUS_SENSOR_ERROR";
            case SpassFingerprint.STATUS_USER_CANCELLED:
                return "STATUS_USER_CANCELLED";
            case SpassFingerprint.STATUS_QUALITY_FAILED:
                return "STATUS_QUALITY_FAILED";
            case SpassFingerprint.STATUS_USER_CANCELLED_BY_TOUCH_OUTSIDE:
                return "STATUS_USER_CANCELLED_BY_TOUCH_OUTSIDE";
            case SpassFingerprint.STATUS_AUTHENTIFICATION_FAILED:
            default:
                return "STATUS_AUTHENTIFICATION_FAILED";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        InitializeViews();
        mSpass = new Spass();

        try {
            mSpass.initialize(this);

            isFeatureEnabled = mSpass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT);

            if (isFeatureEnabled) {
                FINGERPRINT_PROVIDED = true;
                mSpassFingerprint = new SpassFingerprint(this);
                Log.i("Message", "Fingerprint Service is supported in the device.");
                Log.i("Message", "SDK version : " + mSpass.getVersionName());
            } else {
                FINGERPRINT_PROVIDED = false;
                Log.i("Message", "Fingerprint Service is not supported in the device.");
            }
        } catch (SsdkUnsupportedException e) {
            FINGERPRINT_PROVIDED = false;
            Log.i("Message", "Exception: " + e);
        } catch (UnsupportedOperationException e) {
            FINGERPRINT_PROVIDED = false;
            Log.i("Message", "Fingerprint Service is not supported in the device");
        }
    }

    private void InitializeViews() {
        lblEnterPin.setTypeface(Util.setTypeface(this, Util.MYRIAD));
        lblRegister.setTypeface(Util.setTypeface(this, Util.ROBOTO), Typeface.BOLD);
        lblAppName.setTypeface(Util.setTypeface(this, Util.MYRIAD_BOLD_ITALIC));

        lblProblem.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        btnLogin.setTypeface(Util.setTypeface(this, Util.MYRIAD), Typeface.BOLD);
        txtQuickTour.setTypeface(Util.setTypeface(this, Util.ROBOTO), Typeface.BOLD);
    }

    @OnClick(R.id.ll_pin)
    public void Pin() {
        Toast.makeText(ActivityLogin.this, "PIN", Toast.LENGTH_SHORT).show();
        Login();
    }

    @OnClick(R.id.lblRegister)
    public void Register() {
        Util.startNextActivity(this, ActivityRegister.class);
    }

    @OnClick(R.id.btnLogin)
    public void Login() {

        AuthenticateLogin();

        /*if (!AUTHENTICATED && FINGERPRINT_PROVIDED) {
            try {
                if (!mSpassFingerprint.hasRegisteredFinger()) {
                    AuthenticateLogin();
                } else {
                    if (onReadyIdentify == false) {
                        onReadyIdentify = true;
                        try {
                            mSpassFingerprint.startIdentifyWithDialog(this, listener, true);
                            Log.i("Message", "Please identify finger to verify you");
                        } catch (IllegalStateException e) {
                            onReadyIdentify = false;
                            Log.i("Message", "Exception: " + e);
                        }
                    } else {
                        Log.i("Message", "Please cancel Identify first");
                    }
                }
            } catch (UnsupportedOperationException e) {
                Log.i("Message", "Fingerprint Service is not supported in the device");
            }
        } else {
            AuthenticateLogin();
        }
        */
    }

    @OnClick(R.id.lblProblem)
    public void ProblemLogin() {
        Util.startNextActivity(this, ActivityProblemLogin.class);
    }

    private void AuthenticateLogin() {
        if (Network.isConnected(this)) {
            clearPins();
            txtPin.requestFocus();
            ShowHideSoftKeyboard();
            TextChangedListener();
            txtPin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == 6) {
                        clearPins();
                    }
                    return false;
                }
            });
        } else {
            Util.ShowMessage(coordinatorLayout, "No Internet connection.");
        }
    }

    private void fonebayadLoginSession(final DialogInterface dialogInterface) {

        final retrofit.Call<ResponseLogin> loginResponseCall = RestClient.get().fonebayadLogin(txtPin.getText().toString(), Util.getGUID(this));
        loginResponseCall.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Response<ResponseLogin> response, Retrofit retrofit) {

                if (response.isSuccess()) {
                    ResponseLogin loginResponse = response.body();
                    clearPins();
                    Log.i("Status Code", String.valueOf(response.code()));
                    if (loginResponse.getStatus().contains(Constants.STATUS_NOTFOUND)) {
                        Util.ShowNeutralDialog(ActivityLogin.this, "fonebayad", "You have entered Invalid PIN", "OK", ActivityLogin.this);
                    } else if (loginResponse.getStatus().contains(Constants.STATUS_NOTACCEPTABLE)) {
                        Util.ShowNeutralDialog(ActivityLogin.this, "fonebayad", "Entered PIN is not yet valid." + '\n' + "You need to verify your email first.", "OK", ActivityLogin.this);
                    } else if (loginResponse.getStatus().contains(Constants.STATUS_NODEVICE)) {
                        Util.ShowDialog(ActivityLogin.this, "Register", "Entered PIN doesn't have and account. Would you like to: ", "REGISTER", "CANCEL", new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                switch (dialogAction) {
                                    case POSITIVE:
                                        Register();
                                        break;
                                    case NEGATIVE:
                                        materialDialog.dismiss();
                                        break;
                                }
                            }
                        });
                    } else {
                        OAuthCredentials(dialogInterface, loginResponse.getLoginModel().getId());
                        return;
                    }
                    dialogInterface.dismiss();
                } else {
                    switch (response.code()) {
                        case 404:
                            clearPins();
                            TextChangedListener();
                            Util.ShowDialog(ActivityLogin.this, "fonebayad", "An error occurred while trying to connect to server.", "RETRY", "CANCEL", ActivityLogin.this);
                            break;
                    }
                    dialogInterface.dismiss();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                clearPins();
                TextChangedListener();
                loginResponseCall.cancel();
                dialogInterface.dismiss();
                Util.ShowDialog(ActivityLogin.this, "fonebayad", "An error occurred while trying to connect to server.", "RETRY", "CANCEL", ActivityLogin.this);
            }
        });

    }

    private void OAuthCredentials(DialogInterface dialogInterface, String app_id) {
        dialogInterface.dismiss();
        ModelLogin modelLogin;
        if (ModelLogin.countUser() > 0) {
            modelLogin = new Select().from(ModelLogin.class).limit(1).executeSingle();
            modelLogin.setApp_id(app_id);
        } else {
            modelLogin = new ModelLogin();
            modelLogin.setApp_id(app_id);
        }
        modelLogin.save();
        finish();
        Util.startNextActivity(ActivityLogin.this, ActivityDashboard.class);
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
    }

    public TextWatcher PinWatcher() {
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Button pin = (Button) findViewById(Util.pins[start]);
                if (pin.isEnabled() == false) {
                    pin.setEnabled(true);
                } else {
                    pin.setEnabled(false);
                    if (start == 3) {
                        LoginAuth();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        };
        return watcher;
    }

    public void LoginAuth() {
        if (Network.isConnected(this)) {
            new MaterialDialog.Builder(ActivityLogin.this)
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
                            fonebayadLoginSession(dialogInterface);
                        }
                    }).show();
        } else {
            Util.ShowMessage(coordinatorLayout, "No Internet connection!");
        }
    }

    private void ShowHideSoftKeyboard() {
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

    private void clearPins() {
        txtPin.setText(null);
        for (int i = 0; i < Util.pins.length; i++) {
            Button pin = (Button) findViewById(Util.pins[i]);
            pin.setEnabled(true);
        }
    }

    public void TextChangedListener() {
        txtPin.clearTextChangedListeners();
        txtPin.addTextChangedListener(PinWatcher());
    }

    @Override
    protected void onResume() {
        super.onResume();
        clearPins();
    }

    @Override
    protected void onPause() {
        super.onPause();
        clearPins();
    }

    @Override
    public void onBackPressed() {

        if (backpressed) {
            super.onBackPressed();
            return;
        }

        backpressed = true;
        Toast.makeText(this, "Please once again to exit!", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backpressed = false;
            }
        }, 2000);
    }

    @Override
    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
        switch (dialogAction) {
            case POSITIVE:
                materialDialog.dismiss();
                LoginAuth();
                break;
            case NEGATIVE:
            case NEUTRAL:
                materialDialog.dismiss();
                break;
            default:
                materialDialog.dismiss();
                break;
        }

    }
}

