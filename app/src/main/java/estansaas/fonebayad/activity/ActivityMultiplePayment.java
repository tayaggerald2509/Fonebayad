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
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

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
    private Double ttlAmnt = 0.0;

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
        bill_list.setOnTouchListener(this);
        setListViewHeightBasedOnChildren(bill_list);

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
                break;
            case NEGATIVE:
            case NEUTRAL:
                materialDialog.dismiss();
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.getParent().requestDisallowInterceptTouchEvent(true);
        return false;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LinearLayout.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @OnClick(R.id.expanded_menu)
    public void ShowSideMenu() {
        showMenu();
    }

    @OnClick(R.id.back)
    public void OnBack() {
        onBackPressed();
    }
}
