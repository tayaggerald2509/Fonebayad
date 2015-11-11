package estansaas.fonebayad.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import estansaas.fonebayad.R;
import estansaas.fonebayad.adapter.AdapterMultiplePayment;
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
 * Created by gerald.tayag on 11/10/2015.
 */
public class ActivityMultiplePayment extends BaseActivity implements View.OnTouchListener, MaterialDialog.SingleButtonCallback {

    public static final String ACTIVITY_PAYMENT_VIEW = "ActivityPaymentView";
    public static final int REQUEST_PAYMENT_METHOD = 201;

    @Bind(R.id.ll_view)
    public LinearLayout ll_view;

    @Bind(R.id.rl_bill_paid)
    public RelativeLayout rl_bill_paid;

    @Bind(R.id.bill_list)
    public ListView bill_list;

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

    private AdapterMultiplePayment adapterMultiplePayment;
    private ModelBankAccount modelBankAccount;
    public static List<ModelBillInformation> selectedBill;
    private String[] currency;
    private double ttlAmnt = 0.0;
    private BigDecimal newBalance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_payment);
        ButterKnife.bind(this);

        InitializeDataView();
    }

    private void InitializeDataView() {
        adapterMultiplePayment = new AdapterMultiplePayment(this, selectedBill);
        bill_list.setAdapter(adapterMultiplePayment);
        for (ModelBillInformation modelbillInfo : selectedBill) {
            ttlAmnt += Double.valueOf(modelbillInfo.getBill_amount());
        }
        txtAmountDue.setText(new DecimalFormat("#,##0.00").format(ttlAmnt));
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

                        new MaterialDialog.Builder(ActivityMultiplePayment.this)
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
                        Double totalAmnt = ttlAmnt * Double.valueOf(response.body().getRate());
                        lblCurrency.setText(currency);
                        txtConvertion.setText("1 PHP = " + response.body().getRate() + " " + response.body().getTarget());
                        txtTotalAmount.setText(currency.toUpperCase() + " " + new DecimalFormat("#,##0.00").format(totalAmnt));
                    }

                    dialogInterface.dismiss();
                }

                @Override
                public void onFailure(Throwable t) {
                    dialogInterface.dismiss();
                    Util.ShowNeutralDialog(ActivityMultiplePayment.this, "", "An error occured while trying to connect to server.", "OK", ActivityMultiplePayment.this);
                }
            });
        } else {
            dialogInterface.dismiss();
            Util.ShowNeutralDialog(this, "", "Please connect to internet", "OK", this);
        }
    }

    @OnClick(R.id.ll_payment_method)
    public void ShowPaymentMethod() {
        Intent intent = new Intent(this, ActivityPaymentMethod.class);
        intent.putExtra("ModelBillInformation", selectedBill.get(0));
        intent.putExtra("PAYMENT_VIEW", ACTIVITY_PAYMENT_VIEW);
        startActivityForResult(intent, REQUEST_PAYMENT_METHOD);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUEST_PAYMENT_METHOD:
                if (resultCode == Activity.RESULT_OK) {
                    modelBankAccount = (ModelBankAccount) data.getSerializableExtra("PAY_METHOD");
                    txtPaymentMethod.setText(modelBankAccount.getBankaccount_accountname());
                }
                break;
        }
    }

    @OnClick(R.id.btnProceed)
    public void Proceed() {
        if (modelBankAccount != null) {
            Util.ShowDialog(this, "Payment Confirmation", "Are you sure you want to pay now?", "YES", "NO", this);
        } else {
            Util.ShowNeutralDialog(this, "", "Please choose your payment method.", "OK", this);
        }
    }

    @Override
    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
        switch (dialogAction) {
            case POSITIVE:
                if (Double.valueOf(modelBankAccount.getBankaccount_amount()) >= ttlAmnt) {
                    ShowDialogAuth();
                } else {
                    Util.ShowNeutralDialog(this, "Warning", "Payment unsuccessful. Insufficient credit.", "OK", this);
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
        newBalance = new BigDecimal(modelBankAccount.getBankaccount_amount());
        for (ModelBillInformation modelBillInformation : selectedBill) {
            newBalance = newBalance.subtract(new BigDecimal(modelBillInformation.getBill_amount()));
            Call<estansaas.fonebayad.auth.Responses.Response> responseCall = RestClient.get().paybillsMobile(modelBillInformation.getBill_Id(), ModelLogin.getUserInfo().getApp_id(), "Paid", modelBillInformation.getBill_amount(), String.valueOf(newBalance), modelBankAccount.getBankaccount_id(), modelBillInformation.getBill_amount(), "1");
            responseCall.enqueue(new Callback<estansaas.fonebayad.auth.Responses.Response>() {
                @Override
                public void onResponse(Response<estansaas.fonebayad.auth.Responses.Response> response, Retrofit retrofit) {
                    if (response.isSuccess()) {
                        if (response.body().getStatus().equals(Connection.STATUS_ACCEPTED)) {
                            ll_view.setEnabled(false);
                            rl_bill_paid.setVisibility(View.VISIBLE);
                            modelBankAccount.setBankaccount_amount(String.valueOf(newBalance));
                            YoYo.with(Techniques.Landing).duration(1200).playOn(findViewById(R.id.img_stamp));
                        }
                    } else {
                        Util.ShowNeutralDialog(ActivityMultiplePayment.this, "", "An error occured while trying to connect to server.", "OK", ActivityMultiplePayment.this);
                    }
                    dialogInterface.dismiss();
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                    dialogInterface.dismiss();
                    Util.ShowNeutralDialog(ActivityMultiplePayment.this, "", "An error occured while trying to connect to server.", "OK", ActivityMultiplePayment.this);
                }
            });
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()) {
            case R.id.bill_list:
                v.getParent().requestDisallowInterceptTouchEvent(true);
                break;
        }

        return false;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
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
