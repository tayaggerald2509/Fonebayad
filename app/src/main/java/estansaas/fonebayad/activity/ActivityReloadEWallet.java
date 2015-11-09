package estansaas.fonebayad.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import java.io.File;

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
import nl.changer.polypicker.Config;
import nl.changer.polypicker.ImagePickerActivity;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Retrofit;

/**
 * Created by gerald.tayag on 10/28/2015.
 */
public class ActivityReloadEWallet extends BaseActivity implements MaterialDialog.SingleButtonCallback {

    private static final int FILE_CHOOSER_IMAGE_REQUEST_CODE = 200;

    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    @Bind(R.id.txtHeader)
    public TextView txtHeader;

    @Bind(R.id.txtContent)
    public TextView txtContent;

    @Bind(R.id.txtCountry)
    public TextView txtCountry;

    @Bind(R.id.txtBankName)
    public TextView txtBankName;

    @Bind(R.id.txtAcntName)
    public TextView txtAcntName;

    @Bind(R.id.txtBSB)
    public TextView txtBSE;

    @Bind(R.id.txtAcntNo)
    public TextView txtAcntNo;

    @Bind(R.id.lblAmntDeposit)
    public TextView lblAmntDeposit;

    @Bind(R.id.txtAmntDeposit)
    public TextView txtAmntDeposit;

    @Bind(R.id.lblUpload)
    public TextView lblUpload;

    @Bind(R.id.txtAttachment)
    public TextView txtAttachment;

    @Bind(R.id.img_currency)
    public ImageView img_currency;

    private ModelCurrency modelCurrency;
    private String[] currency;
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
        txtHeader.setTypeface(Util.setTypeface(this, Util.ROBOTO), Typeface.BOLD);
        txtContent.setTypeface(Util.setTypeface(this, Util.ROBOTO_LIGHT));

        txtCountry.setError(null, Util.resizeDrawable(this, R.drawable.ic_keyboard_arrow_down_black_48dp));

        lblAmntDeposit.setTypeface(Util.setTypeface(this, Util.ROBOTO_LIGHT), Typeface.BOLD_ITALIC);
        lblUpload.setTypeface(Util.setTypeface(this, Util.ROBOTO_LIGHT), Typeface.BOLD_ITALIC);
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

    private void ShowAuthDialog(){
        if(Network.isConnected(this)){
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
        }else{
            Util.ShowNeutralDialog(this, "Warning", "Please connect to internet!", "OK", this);
        }
    }

    private void AuthReloadEWallet(final DialogInterface dialogInterface){
        Call<Response> responseCall = RestClient.get().saveReloadWalletDetails(ModelLogin.getUserInfo().getApp_id(), file.getName(), Util.getGUID(this), Util.getDeviceName(), "", txtAmntDeposit.getText().toString(), "1", "1");
        responseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(retrofit.Response<Response> response, Retrofit retrofit) {
                if(response.isSuccess()){
                    if(response.body().getStatus().equals(Connection.STATUS_ACCEPTED)){
                        Util.ShowNeutralDialog(ActivityReloadEWallet.this, "", "Your E-Wallet reload receipt will be received by our Fonebayad team. Please wait for up to 24 hours for the amount to reflect on your E-Wallet account. For further inquired, e-mail us at support@fonebayad.com", "OK", ActivityReloadEWallet.this);
                    }
                }

                dialogInterface.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                dialogInterface.dismiss();
                Util.ShowNeutralDialog(ActivityReloadEWallet.this, "Warning", t.getMessage(), "OK", ActivityReloadEWallet.this);
            }
        });

    }

    @OnClick(R.id.ll_country)
    public void SelectCountry(){
        int i = 0;
        currency = new String[ModelCurrency.getCurrency().size()];
        for (ModelCurrency modelCurrency : ModelCurrency.getCurrency()) {
            currency[i++] = modelCurrency.getCurrency_name();
        }

        new MaterialDialog.Builder(this)
                .title("Select Country")
                .items(currency)
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        final ModelCurrency currency = ModelCurrency.getCurrency().get(i-1);
                        int imageResource = getResources().getIdentifier(currency.getCurrency_code().toLowerCase(), "drawable", getPackageName());
                        country_id = currency.getCurrency_id();
                        img_currency.setImageDrawable(getResources().getDrawable(imageResource));
                        txtCountry.setText(currency.getCurrency_name());
                        ShowDialogDetail();

                        return true;
                    }
                })
                .positiveText(R.string.ok)
                .show();
    }

    private void ShowDialogDetail(){
        if(Network.isConnected(this)){
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
        }else{
            Util.ShowNeutralDialog(this, "", "An error occured while trying to connect to server", "OK", this);
        }
    }

    private void AuthCountryDetails(final DialogInterface dialogInterface){
        Log.i("country_id", country_id);
        Call<ResponseCountryDetails> responseCountryDetailsCall = RestClient.get().getTransperaDetailsBasedOnCountry(country_id);
        responseCountryDetailsCall.enqueue(new Callback<ResponseCountryDetails>() {
            @Override
            public void onResponse(retrofit.Response<ResponseCountryDetails> response, Retrofit retrofit) {
                if(response.isSuccess()){

                    ModelCountryDetail modelCountryDetail = response.body().getModelCountryDetails().get(0);

                    txtBankName.setText(modelCountryDetail.getTransperabank_name());
                }
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
        Intent intent = new Intent(this, ImagePickerActivity.class);
        Config config = new Config.Builder()
                .setTabBackgroundColor(R.color.app_color)    // set tab background color. Default white.
                .setTabSelectionIndicatorColor(R.color.app_color)
                .setCameraButtonColor(R.color.white)
                .setSelectionLimit(1)
                .build();
        ImagePickerActivity.setConfig(config);
        startActivityForResult(intent, FILE_CHOOSER_IMAGE_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == FILE_CHOOSER_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Parcelable[] parcelableUris = data.getParcelableArrayExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);

                if (parcelableUris == null) {
                    return;
                }

                Uri[] uris = new Uri[parcelableUris.length];
                System.arraycopy(parcelableUris, 0, uris, 0, parcelableUris.length);

                if (uris != null) {
                    for (Uri uri : uris) {
                        Log.i("FILE", " uri: " + uri);
                        file = new File(uri.getPath());
                        txtAttachment.setVisibility(View.VISIBLE);
                        txtAttachment.setText(file.getName());
                    }
                }
            } else {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
        switch (dialogAction){
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
