package estansaas.fonebayad.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import estansaas.fonebayad.R;
import estansaas.fonebayad.auth.Responses.ResponseForexRate;
import estansaas.fonebayad.auth.RestClient;
import estansaas.fonebayad.model.ModelBillInformation;
import estansaas.fonebayad.model.ModelCurrency;
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

    private ModelBillInformation modelBillInformation;
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

        /*int i = 0;
        for (ModelCurrency modelCurrency : ModelCurrency.getCurrency()) {
            currency[i++] = modelCurrency.getCurrency_code();
        }
        */

        currency = new String[3];
        currency[0] = "PHP";
        currency[1] = "AUD";
        currency[2] = "USD";

        new MaterialDialog.Builder(this)
                .title("Select Country")
                .items(currency)
                .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                        final String currency_selected = currency[i];
                        Drawable drawable = null;
                        switch (i) {
                            case 0:
                                drawable = getResources().getDrawable(R.drawable.php);
                                break;
                            case 1:
                                drawable = getResources().getDrawable(R.drawable.aud);
                                break;
                            case 2:
                                drawable = getResources().getDrawable(R.drawable.usd);
                                break;
                            default:
                                break;
                        }
                        img_currency.setImageDrawable(drawable);

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
                                        AuthForexRate(dialogInterface, currency_selected);
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
                        txtTotalAmount.setText(new DecimalFormat("#,##0.00").format(totalAmnt));
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

    @Override
    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
        switch (dialogAction) {
            case POSITIVE:

                break;
            case NEGATIVE:

                break;
            case NEUTRAL:
                materialDialog.dismiss();
                break;
        }
    }

    @OnClick(R.id.back)
    public void Back() {
        finish();
    }

    @OnClick(R.id.expanded_menu)
    public void toggleMenu() {
        showMenu();
    }
}
