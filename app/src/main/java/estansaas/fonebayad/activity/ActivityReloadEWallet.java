package estansaas.fonebayad.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import java.io.File;
import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import estansaas.fonebayad.R;
import estansaas.fonebayad.auth.Responses.Response;
import estansaas.fonebayad.auth.Responses.ResponseCountryDetails;
import estansaas.fonebayad.auth.RestClient;
import estansaas.fonebayad.model.ModelCountryDetail;
import estansaas.fonebayad.model.ModelCurrency;
import estansaas.fonebayad.model.ModelLogin;
import estansaas.fonebayad.utils.Connection;
import estansaas.fonebayad.utils.Network;
import estansaas.fonebayad.utils.Util;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;

/**
 * Created by gerald.tayag on 10/28/2015.
 */
public class ActivityReloadEWallet extends BaseActivity implements MaterialDialog.SingleButtonCallback, View.OnFocusChangeListener {

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int SELECT_PHOTO = 1;

    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    @Bind(R.id.lblHeader)
    public TextView lblHeader;

    @Bind(R.id.lblContent)
    public TextView lblContent;

    @Bind(R.id.txtCountry)
    public TextView txtCountry;

    @Bind(R.id.txtBankName)
    public TextView txtBankName;

    @Bind(R.id.txtAcntName)
    public TextView txtAcntName;

    @Bind(R.id.txtBSB)
    public TextView txtBSB;

    @Bind(R.id.txtAcntNo)
    public TextView txtAcntNo;

    @Bind(R.id.lblAmntDeposit)
    public TextView lblAmntDeposit;

    @Bind(R.id.txtAmntDeposit)
    public EditText txtAmntDeposit;

    @Bind(R.id.lblUpload)
    public TextView lblUpload;

    @Bind(R.id.txtAttachment)
    public TextView txtAttachment;

    @Bind(R.id.img_currency)
    public ImageView img_currency;

    private ModelCurrency modelCurrency;
    private String[] currency;
    private Uri fileUri;
    private File file; // file url to store image/video
    private String country_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reload_ewallet);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        InitializeViews();
    }

    private void InitializeViews() {
        lblHeader.setTypeface(Util.setTypeface(this, Util.ROBOTO_LIGHT), Typeface.BOLD);
        lblContent.setTypeface(Util.setTypeface(this, Util.ROBOTO_LIGHT));
        txtCountry.setCompoundDrawables(null, null, Util.resizeDrawable(this, R.drawable.ic_keyboard_arrow_down_black_48dp), null);
        txtAmntDeposit.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        lblAmntDeposit.setTypeface(Util.setTypeface(this, Util.ROBOTO_LIGHT), Typeface.BOLD_ITALIC);
        lblUpload.setTypeface(Util.setTypeface(this, Util.ROBOTO_LIGHT), Typeface.BOLD_ITALIC);

        txtAmntDeposit.setOnFocusChangeListener(this);
        txtAmntDeposit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == 6 || actionId == KeyEvent.KEYCODE_BACK) {
                    String bill_amount = txtAmntDeposit.getText().toString();
                    if (!txtAmntDeposit.getText().toString().equals("") && !txtAmntDeposit.getText().equals(null)) {
                        txtAmntDeposit.setText("Php " + new DecimalFormat("#,##0.00").format(Double.valueOf(bill_amount.toString())));
                    }
                    InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.toggleSoftInput(0, 0);
                    txtAmntDeposit.clearFocus();
                }
                return false;
            }
        });
    }

    @OnClick(R.id.btnSubmit)
    public void Submit() {
        if (file == null) {
            Util.ShowNeutralDialog(this, "Warning", "Please attach a photo", "OK", this);
            return;
        }

        if (txtAmntDeposit.getText().toString() == null && txtAmntDeposit.getText().toString() == "") {
            Util.ShowNeutralDialog(this, "Warning", "Please enter amount to deposit", "OK", this);
            return;
        }

        ShowAuthDialog();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.txtAmntDeposit:
                if (hasFocus) {
                    txtAmntDeposit.setText(txtAmntDeposit.getText().toString().replace("Php", "").replace(",", "").trim());
                }
                break;
        }
    }

    private void ShowAuthDialog() {
        if (Network.isConnected(this)) {
            new MaterialDialog.Builder(this)
                    .content(R.string.action_wait)
                    .contentGravity(GravityEnum.CENTER)
                    .theme(Theme.DARK)
                    .widgetColor(Color.WHITE)
                    .progressIndeterminateStyle(false)
                    .progress(true, 0)
                    .showListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            AuthReloadEWallet(dialogInterface);
                        }
                    }).show();
        } else {
            Util.ShowNeutralDialog(this, "Warning", "Please connect to internet!", "OK", this);
        }
    }

    private void AuthReloadEWallet(final DialogInterface dialogInterface) {
        Call<Response> responseCall = RestClient.get().saveReloadWalletDetails(ModelLogin.getUserInfo().getApp_id(), file.getName(), Util.getGUID(this), Util.getDeviceName(), "", txtAmntDeposit.getText().toString(), "1", "1");
        responseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(retrofit.Response<Response> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body().getStatus().equals(Connection.STATUS_ACCEPTED)) {
                        Util.ShowNeutralDialog(ActivityReloadEWallet.this, "", "Your E-Wallet reload receipt will be received by our Fonebayad team. Please wait for up to 24 hours for the amount to reflect on your E-Wallet account. For further inquired, e-mail us at support@fonebayad.com", "OK", new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                switch (dialogAction) {
                                    case NEUTRAL:
                                        onBackPressed();
                                        break;
                                }
                            }
                        });
                    } else {
                        Util.ShowNeutralDialog(ActivityReloadEWallet.this, "", "Sorry failed to reload your e-wallet", "OK", ActivityReloadEWallet.this);
                    }
                } else {
                    Util.ShowNeutralDialog(ActivityReloadEWallet.this, "", "An error occurred while trying to connect to server.", "OK", ActivityReloadEWallet.this);
                }

                dialogInterface.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                dialogInterface.dismiss();
                Util.ShowNeutralDialog(ActivityReloadEWallet.this, "", "An error occurred while trying to connect to server.", "OK", ActivityReloadEWallet.this);
            }
        });

    }

    @OnClick(R.id.txtCountry)
    public void txtCountry() {
        SelectCountry();
    }

    @OnClick(R.id.ll_country)
    public void SelectCountry() {
        //int i = 0;
        currency = new String[ModelCurrency.getCurrency().size()];

        for (int i = ModelCurrency.getCurrency().size() - 1; i >= 0; i--) {
            currency[i] = ModelCurrency.getCurrency().get(i).getCurrency_name();
        }

        new MaterialDialog.Builder(this)
                .title("Select Country")
                .items(currency)
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        final ModelCurrency currency = ModelCurrency.getCurrency().get(i);

                        int imageResource = getResources().getIdentifier(currency.getCurrency_code().toLowerCase(), "drawable", getPackageName());
                        country_id = currency.getCurrency_countryid();
                        img_currency.setImageDrawable(getResources().getDrawable(imageResource));
                        txtCountry.setText(currency.getCurrency_name());
                        ShowDialogDetail();

                        return true;
                    }
                })
                .positiveText(R.string.ok)
                .show();
    }

    private void ShowDialogDetail() {
        if (Network.isConnected(this)) {
            new MaterialDialog.Builder(this)
                    .content(R.string.action_wait)
                    .contentGravity(GravityEnum.CENTER)
                    .theme(Theme.DARK)
                    .widgetColor(Color.WHITE)
                    .progressIndeterminateStyle(false)
                    .progress(true, 0)
                    .showListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            AuthCountryDetails(dialogInterface);
                        }
                    }).show();
        } else {
            Util.ShowNeutralDialog(this, "", "An error occured while trying to connect to server", "OK", this);
        }
    }

    private void AuthCountryDetails(final DialogInterface dialogInterface) {
        Log.i("country_id", country_id);
        Call<ResponseCountryDetails> responseCountryDetailsCall = RestClient.get().getTransperaDetailsBasedOnCountry(country_id);
        responseCountryDetailsCall.enqueue(new Callback<ResponseCountryDetails>() {
            @Override
            public void onResponse(retrofit.Response<ResponseCountryDetails> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    ModelCountryDetail modelCountryDetail = response.body().getModelCountryDetails().get(0);
                    txtBankName.setText(modelCountryDetail.getTransperabankname());
                    txtAcntName.setText(modelCountryDetail.getTransperabank_accountname());
                    txtBSB.setText(modelCountryDetail.getTransperabank_bsbnumber());
                    txtAcntNo.setText(modelCountryDetail.getTransperabank_accountnumberv());
                }
                dialogInterface.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                dialogInterface.dismiss();
                Util.ShowNeutralDialog(ActivityReloadEWallet.this, "", "An error occured while trying to connect to server", "OK", ActivityReloadEWallet.this);
            }
        });
    }

    @OnClick(R.id.ll_upload)
    public void UploadPhoto() {

        String[] photo = new String[2];
        photo[0] = "Camera";
        photo[1] = "Upload Photo";
        new MaterialDialog.Builder(this)
                .title("Upload Photo")
                .items(photo)
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        Intent intent;
                        switch (i) {
                            case 0:
                                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                fileUri = Util.getMediaFile();
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                                startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
                                break;
                            case 1:
                                intent = new Intent(Intent.ACTION_PICK);
                                fileUri = Util.getMediaFile();
                                intent.setType("image/*");
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                                startActivityForResult(intent, SELECT_PHOTO);
                                break;
                        }
                        return true;
                    }
                })
                .positiveText(R.string.ok)
                .show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                file = new File(fileUri.getPath());
                txtAttachment.setVisibility(View.VISIBLE);
                txtAttachment.setText(file.getName());
            }
        } else if (requestCode == SELECT_PHOTO) {
            file = new File(Util.getPath(this, data));
            txtAttachment.setVisibility(View.VISIBLE);
            txtAttachment.setText(file.getName());
        }
    }

    @Override
    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
        switch (dialogAction) {
            case POSITIVE:
                break;
            case NEGATIVE:
            case NEUTRAL:
                materialDialog.dismiss();
                break;

        }
    }

    @OnClick(R.id.expanded_menu)
    public void ShowSideMenu() {
        showMenu();
    }

    @OnClick(R.id.back)
    public void OnBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Util.startNextActivity(this, ActivityDashboard.class);
    }
}
