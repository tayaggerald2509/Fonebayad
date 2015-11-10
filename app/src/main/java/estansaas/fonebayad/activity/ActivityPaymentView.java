package estansaas.fonebayad.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import estansaas.fonebayad.R;
import estansaas.fonebayad.auth.Responses.ResponseForexRate;
import estansaas.fonebayad.auth.RestClient;
import estansaas.fonebayad.model.ModelBankAccount;
import estansaas.fonebayad.model.ModelBillInformation;
import estansaas.fonebayad.model.ModelCurrency;
import estansaas.fonebayad.model.ModelLogin;
import estansaas.fonebayad.utils.Connection;
import estansaas.fonebayad.utils.Network;
import estansaas.fonebayad.utils.Util;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by gerald.tayag on 10/26/2015.
 */
public class ActivityPaymentView extends BaseActivity implements MaterialDialog.SingleButtonCallback {

    public static final String ACTIVITY_PAYMENT_VIEW = "ActivityPaymentView";
    public static final int REQUEST_PAYMENT_METHOD = 201;

    @Bind(R.id.rl_bill_paid)
    public RelativeLayout rl_bill_paid;

    @Bind(R.id.txtBiller)
    public TextView txtBiller;

    @Bind(R.id.txtAmountDue)
    public TextView txtAmountDue;

    @Bind(R.id.txtConvertion)
    public TextView txtConvertion;

    @Bind(R.id.img_currency)
    public ImageView img_currency;

    @Bind(R.id.lblCurrency)
    public TextView lblCurrency;

    @Bind(R.id.txtTotalAmount)
    public TextView txtTotalAmount;

    @Bind(R.id.txtPaymentMethod)
    public TextView txtPaymentMethod;

    @Bind(R.id.btnProceed)
    public Button btnProceed;

    private ModelBillInformation modelBillInformation;
    private ModelBankAccount modelBankAccount;
    private ModelCurrency modelCurrency;
    private String[] currency;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_view);
        ButterKnife.bind(this);

        modelBillInformation = (ModelBillInformation) getIntent().getSerializableExtra("ModelBillInformation");
        InitializeData();

    }

    private void InitializeData() {
        txtBiller.setText(modelBillInformation.getBiller_name());
        txtAmountDue.setText(new DecimalFormat("#,##0.00").format(Double.valueOf(modelBillInformation.getBill_amount())));
        txtTotalAmount.setText(new DecimalFormat("#,##0.00").format(Double.valueOf(modelBillInformation.getBill_amount())));
    }

    @OnClick(R.id.ll_currency)
    public void ShowCurrency() {

        int i = 0;
        currency = new String[ModelCurrency.getCurrency().size()];
        for (ModelCurrency modelCurrency : ModelCurrency.getCurrency()) {
            currency[i++] = modelCurrency.getCurrency_code();
        }

        new MaterialDialog.Builder(this)
                .title("Select Country")
                .items(currency)
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        final ModelCurrency currency = ModelCurrency.getCurrency().get(i);
                        int imageResource = getResources().getIdentifier(currency.getCurrency_code().toLowerCase(), "drawable", getPackageName());
                        img_currency.setImageDrawable(getResources().getDrawable(imageResource));

                        new MaterialDialog.Builder(ActivityPaymentView.this)
                                .content("Loading")
                                .contentGravity(GravityEnum.CENTER)
                                .theme(Theme.DARK)
                                .widgetColor(Color.WHITE)
                                .progressIndeterminateStyle(false)
                                .progress(true, 0)
                                .cancelable(true)
                                .showListener(new DialogInterface.OnShowListener() {
                                    @Override
                                    public void onShow(DialogInterface dialogInterface) {
                                        AuthForexRate(dialogInterface, currency.getCurrency_code());
                                    }
                                }).show();


                        return true;
                    }
                })
                .positiveText(R.string.ok)
                .show();
    }

    private void AuthForexRate(final DialogInterface dialogInterface, final String currency) {
        if (Network.isConnected(this)) {
            Call<ResponseForexRate> forexRateCall = RestClient.get().getForexRate("PHP", currency.toUpperCase());
            forexRateCall.enqueue(new Callback<ResponseForexRate>() {
                @Override
                public void onResponse(Response<ResponseForexRate> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        Double totalAmnt = Double.valueOf(modelBillInformation.getBill_amount()) * Double.valueOf(response.body().getRate());
                        lblCurrency.setText(currency);
                        txtConvertion.setText("1 PHP = " + response.body().getRate() + " " + response.body().getTarget());
                        txtTotalAmount.setText(currency.toUpperCase() + " " + new DecimalFormat("#,##0.00").format(totalAmnt));
                    }

                    dialogInterface.dismiss();
                }

                @Override
                public void onFailure(Throwable t) {
                    dialogInterface.dismiss();
                    Util.ShowNeutralDialog(ActivityPaymentView.this, "fonebayad", t.getMessage(), "OK", ActivityPaymentView.this);
                }
            });
        } else {
            dialogInterface.dismiss();
            Util.ShowNeutralDialog(this, "fonebayad", "Please connect to internet", "OK", this);
        }

    }

    @OnClick(R.id.ll_payment_method)
    public void ShowPaymentMethod() {
        Intent intent = new Intent(this, ActivityPaymentMethod.class);
        intent.putExtra("ModelBillInformation", modelBillInformation);
        intent.putExtra("PAYMENT_VIEW", ACTIVITY_PAYMENT_VIEW);
        startActivityForResult(intent, REQUEST_PAYMENT_METHOD);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.btnProceed)
    public void Proceed() {
        if (modelBankAccount != null) {
            Util.ShowDialog(this, "Payment Confirmation", "Are you sure you want to pay now?", "YES", "NO", this);
        } else {
            Util.ShowNeutralDialog(this, "fonebayad", "Please choose your payment method.", "OK", this);
        }
    }

    @Override
    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
        switch (dialogAction) {
            case POSITIVE:
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(modelBillInformation.getDue_date());
                if (new Date().before(calendar.getTime())) {
                    if (Double.valueOf(modelBankAccount.getBankaccount_amount()) >= Double.valueOf(modelBillInformation.getBill_amount())) {
                        ShowDialogAuth();
                    } else {
                        Util.ShowNeutralDialog(this, "Warning", "Payment unsuccessful. Insufficient credit.", "OK", this);
                    }
                } else {
                    Util.ShowNeutralDialog(this, "Warning", "Unable to proceed with payment. Your bill is already overdue", "Ok", this);
                }

                break;
            case NEGATIVE:
            case NEUTRAL:
                materialDialog.dismiss();
                break;
        }
    }

    private void ShowDialogAuth() {
        if (Network.isConnected(this)) {
            new MaterialDialog.Builder(this)
                    .content("Loading")
                    .contentGravity(GravityEnum.CENTER)
                    .theme(Theme.DARK)
                    .widgetColor(Color.WHITE)
                    .progressIndeterminateStyle(false)
                    .progress(true, 0)
                    .cancelable(true)
                    .showListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            AuthPayment(dialogInterface);
                        }
                    }).show();
        } else {
            Util.ShowNeutralDialog(this, "fonebayad", "No Internet connection!", "OK", this);
        }
    }

    private void AuthPayment(final DialogInterface dialogInterface) {

        Double newBalance = Double.valueOf(modelBankAccount.getBankaccount_amount()) - Double.valueOf(modelBillInformation.getBill_amount());
        Call<estansaas.fonebayad.auth.Responses.Response> responseCall = RestClient.get().paybillsMobile(modelBillInformation.getBill_Id(), ModelLogin.getUserInfo().getApp_id(), "Paid", modelBillInformation.getBill_amount(), newBalance.toString(), modelBankAccount.getBankaccount_id(), modelBillInformation.getBill_amount(), "1");

        responseCall.enqueue(new Callback<estansaas.fonebayad.auth.Responses.Response>() {
            @Override
            public void onResponse(Response<estansaas.fonebayad.auth.Responses.Response> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body().getStatus().equals(Connection.STATUS_ACCEPTED)) {
                        rl_bill_paid.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.Landing).duration(1200).playOn(findViewById(R.id.img_stamp));
                    }
                } else {
                    Util.ShowNeutralDialog(ActivityPaymentView.this, "", "An error occured while trying to connect to server.", "OK", ActivityPaymentView.this);
                }
                dialogInterface.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                dialogInterface.dismiss();
                Util.ShowNeutralDialog(ActivityPaymentView.this, "", "An error occured while trying to connect to server.", "OK", ActivityPaymentView.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_PAYMENT_METHOD:
                if(resultCode == Activity.RESULT_OK){
                    modelBankAccount = (ModelBankAccount) data.getSerializableExtra("PAY_METHOD");
                    txtPaymentMethod.setText(modelBankAccount.getBankaccount_accountname());
                }
                break;
        }
    }

    @OnClick(R.id.ll_finish)
    public void goto_finish() {
        onBackPressed();
    }

    @OnClick(R.id.back)
    public void Back() {
        onBackPressed();
    }

    @OnClick(R.id.expanded_menu)
    public void toggleMenu() {
        showMenu();
    }

    @Override
    public void onBackPressed() {
        if (rl_bill_paid.getVisibility() == View.VISIBLE) {
            finish();
            Util.startNextActivity(this, ActivityDashboard.class);
        } else {
            finish();
            Util.startNextActivity(this, ActivityMyBills.class);
        }
    }
}
