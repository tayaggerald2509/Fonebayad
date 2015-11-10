package estansaas.fonebayad.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import estansaas.fonebayad.R;
import estansaas.fonebayad.adapter.AdapterPaymentMethod;
import estansaas.fonebayad.auth.Responses.ResponseBankAccount;
import estansaas.fonebayad.auth.RestClient;
import estansaas.fonebayad.model.ModelBankAccount;
import estansaas.fonebayad.model.ModelBillInformation;
import estansaas.fonebayad.model.ModelLogin;
import estansaas.fonebayad.utils.Network;
import estansaas.fonebayad.utils.Util;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by gerald.tayag on 11/3/2015.
 */
public class ActivityPaymentMethods extends BaseActivity implements MaterialDialog.SingleButtonCallback {

    @Bind(R.id.list_bank)
    public ListView list_bank;

    private ArrayList<ModelBankAccount> modelBankAccounts;
    private AdapterPaymentMethod adapterPaymentMethod;
    private ModelBillInformation modelBillInformation;
    private SharedPreferences pref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_methods);
        ButterKnife.bind(this);

        LoadData();
    }

    private void LoadData() {
        if (Network.isConnected(this)) {
            new MaterialDialog.Builder(this)
                    .content(R.string.action_wait)
                    .contentGravity(GravityEnum.CENTER)
                    .theme(Theme.DARK)
                    .widgetColor(Color.WHITE)
                    .progressIndeterminateStyle(false)
                    .progress(true, 0)
                    .cancelable(false)
                    .dismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            list_bank.setAdapter(adapterPaymentMethod);
                            //list_bank.setOnItemClickListener(ActivityPaymentMethods.this);
                        }
                    })
                    .showListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            InitializeData(dialogInterface);
                        }
                    }).show();
        } else {
            Util.ShowNeutralDialog(ActivityPaymentMethods.this, "fonebayad", "Please connect to internet", "OK", ActivityPaymentMethods.this);
        }
    }

    private void InitializeData(final DialogInterface dialogInterface) {
        modelBankAccounts = new ArrayList<ModelBankAccount>();

        Call<ResponseBankAccount> responseBank = RestClient.get().getAllActiveBankAccounts(ModelLogin.getUserInfo().getApp_id());
        responseBank.enqueue(new Callback<ResponseBankAccount>() {
                                 @Override
                                 public void onResponse(Response<ResponseBankAccount> response, Retrofit retrofit) {
                                     if (response.isSuccess()) {
                                         if (response.code() == 200) {
                                             ActiveAndroid.beginTransaction();
                                             try {
                                                 for (ModelBankAccount bankaccountModelBankAccount : response.body().getModelBankAccount()) {
                                                     modelBankAccounts.add(bankaccountModelBankAccount);
                                                 }
                                                 adapterPaymentMethod = new AdapterPaymentMethod(ActivityPaymentMethods.this, modelBankAccounts);
                                                 ActiveAndroid.setTransactionSuccessful();
                                             } finally {
                                                 ActiveAndroid.endTransaction();
                                             }
                                         }
                                     } else {
                                         Util.ShowDialog(ActivityPaymentMethods.this, "fonebayad", "Failed to connect to server", "RETRY", "CANCEL", ActivityPaymentMethods.this);
                                     }
                                     dialogInterface.dismiss();
                                 }

                                 @Override
                                 public void onFailure(Throwable t) {
                                     dialogInterface.dismiss();
                                     Util.ShowDialog(ActivityPaymentMethods.this, "fonebayad", "Unable to connect to server", "RETRY", "CANCEL", ActivityPaymentMethods.this);
                                 }
                             }
        );
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

    @Override
    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {

        switch (dialogAction) {
            case POSITIVE:
                LoadData();
                break;
            case NEGATIVE:
            case NEUTRAL:
                materialDialog.dismiss();
                break;
        }

    }
}
