package estansaas.fonebayad.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import estansaas.fonebayad.R;
import estansaas.fonebayad.adapter.AdapterBankAccount;
import estansaas.fonebayad.auth.Responses.ResponseAccess;
import estansaas.fonebayad.auth.Responses.ResponseBankAccount;
import estansaas.fonebayad.auth.Responses.ResponseCreateBills;
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
 * Created by gerald.tayag on 10/21/2015.
 */
public class ActivityPaymentMethod extends BaseActivity implements ListView.OnItemClickListener, MaterialDialog.SingleButtonCallback {

    private static int ACTIVITY_CURRENT_PROCESS = 101;

    @Bind(R.id.coordinatorLayout)
    public CoordinatorLayout coordinatorLayout;

    @Bind(R.id.list_bank)
    public ListView list_bank;

    private ArrayList<ModelBankAccount> modelBankAccounts;
    private AdapterBankAccount adapterBankAccount;
    private ModelBillInformation modelBillInformation;
    private SharedPreferences pref;

    @Override
    protected void onStart() {
        super.onStart();
        pref = Util.sharedPreferences(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        ButterKnife.bind(this);

        modelBillInformation = (ModelBillInformation) getIntent().getSerializableExtra("ModelBillInformation");
        Log.i("Bill Information", modelBillInformation.getBill_account_number());
        list_bank.setOnItemClickListener(this);

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
                            list_bank.setAdapter(adapterBankAccount);
                            list_bank.setOnItemClickListener(ActivityPaymentMethod.this);
                        }
                    })
                    .showListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            ACTIVITY_CURRENT_PROCESS = 101;
                            InitializeData(dialogInterface);
                        }
                    }).show();
        } else {
            Util.ShowNeutralDialog(ActivityPaymentMethod.this, "fonebayad", "Please connect to internet", "OK", ActivityPaymentMethod.this);
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
                                                 adapterBankAccount = new AdapterBankAccount(ActivityPaymentMethod.this, modelBankAccounts);
                                                 ActiveAndroid.setTransactionSuccessful();
                                             } finally {
                                                 ActiveAndroid.endTransaction();
                                             }
                                         }
                                     } else {
                                         Util.ShowDialog(ActivityPaymentMethod.this, "fonebayad", "Failed to connect to server", "RETRY", "CANCEL", ActivityPaymentMethod.this);
                                     }
                                     dialogInterface.dismiss();
                                 }

                                 @Override
                                 public void onFailure(Throwable t) {
                                     dialogInterface.dismiss();
                                     Util.ShowDialog(ActivityPaymentMethod.this, "fonebayad", "Unable to connect to server", "RETRY", "CANCEL", ActivityPaymentMethod.this);
                                 }
                             }
        );
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        modelBillInformation.setBill_user_id(ModelLogin.getUserInfo().getApp_id());
        modelBillInformation.setBill_currency(modelBankAccounts.get(position).getBankaccount_currency());
        modelBillInformation.setBill_payment_method(modelBankAccounts.get(position).getBankaccount_id());

        if (ActivityAddManualBill.ACTIVITY_ADDBILL_VIEW.equals(getIntent().getExtras().getString("PAYMENT_VIEW")) == true) {
            ProcessBill();
        } else {
            Intent resultIntent = new Intent();
            // TODO Add extras or a data URI to this intent as appropriate.
            resultIntent.putExtra("ModelBillInformation", modelBillInformation);
            resultIntent.putExtra("PAY_METHOD", modelBankAccounts.get(position));
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }

    }

    private void ProcessBill() {
        if (Network.isConnected(this)) {
            new MaterialDialog.Builder(this)
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
                            ACTIVITY_CURRENT_PROCESS = 201;
                            OAuthCredentials(dialogInterface);
                        }
                    }).show();

        } else {
            Util.ShowNeutralDialog(ActivityPaymentMethod.this, "fonebayad", "Please connect to internet", "OK", ActivityPaymentMethod.this);
        }
    }

    private void OAuthCredentials(final DialogInterface dialogInterface) {
        Call<ResponseAccess> responseAccessCall = RestClient.get().getAccessToken("password", ModelLogin.getUserInfo().getApp_id(), pref.getString("CLIENT_SECRET", ""), pref.getString("USERNAME", ""), pref.getString("PASSWORD", ""));
        responseAccessCall.enqueue(new Callback<ResponseAccess>() {
            @Override
            public void onResponse(Response<ResponseAccess> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    CreateBillStatement(dialogInterface, response.body());
                } else {
                    Util.ShowNeutralDialog(ActivityPaymentMethod.this, "", "An error occurred while trying to connect to server.", "OK", ActivityPaymentMethod.this);
                }
                dialogInterface.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                dialogInterface.dismiss();
                Util.ShowNeutralDialog(ActivityPaymentMethod.this, "", "An error occurred while trying to connect to server.", "OK", ActivityPaymentMethod.this);
            }
        });
    }

    private void CreateBillStatement(final DialogInterface dialogInterface, ResponseAccess access) {

        Call<ResponseCreateBills> responseCall = RestClient.get().createBillStatement(access.getToken_type() + " " + access.getAccess_token(), modelBillInformation.getBill_biller(), modelBillInformation.getBill_account_number(), modelBillInformation.getBill_transaction_number(), modelBillInformation.getBill_currency(), modelBillInformation.getBill_amount(), modelBillInformation.getBill_status(), modelBillInformation.getBill_attachment(), modelBillInformation.getBill_due_date().toString(), modelBillInformation.getBill_schedule_of_payment(), modelBillInformation.getBill_user_id(), modelBillInformation.getBill_payment_method(), modelBillInformation.getBill_type(), modelBillInformation.getBill_user_entity());
        responseCall.enqueue(new Callback<ResponseCreateBills>() {
            @Override
            public void onResponse(Response<ResponseCreateBills> response, Retrofit retrofit) {

                Log.i("ACTIVITY PAYMENT METHOD", response.message());

                if (response.isSuccess()) {
                    if (response.code() == 200) {
                        if (response.body().getMessage().equals("success")) {
                            if (!modelBillInformation.getBill_attachment().isEmpty()) {
                                uploadFile(dialogInterface);
                                return;
                            } else {
                                Intent intent = new Intent(ActivityPaymentMethod.this, ActivityViewBills.class);
                                intent.putExtra("ModelBillInformation", modelBillInformation);
                                startActivity(intent);
                                finish();
                                overridePendingTransition(0, 0);
                            }
                        }
                    }
                } else {
                    Util.ShowDialog(ActivityPaymentMethod.this, "fonebayad", "Process failed please try again", "RETRY", "CANCEL", ActivityPaymentMethod.this);
                }
                dialogInterface.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                dialogInterface.dismiss();
                Util.ShowDialog(ActivityPaymentMethod.this, "fonebayad", "Failed to connect to server", "RETRY", "CANCEL", ActivityPaymentMethod.this);
            }
        });
    }

    private void uploadFile(final DialogInterface dialogInterface) {

        File file = new File(modelBillInformation.getFile_path());

        RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file.getPath());
        RequestBody createdBy = RequestBody.create(MediaType.parse("text/plain"), ModelLogin.getUserInfo().getApp_id());
        RequestBody device = RequestBody.create(MediaType.parse("text/plain"), Util.getDeviceType());
        RequestBody device_guid = RequestBody.create(MediaType.parse("text/plain"), Util.getGUID(this));

        Call<estansaas.fonebayad.auth.Responses.Response> responseCall = RestClient.get().upload(fileBody, createdBy, device_guid, device);
        responseCall.enqueue(new Callback<estansaas.fonebayad.auth.Responses.Response>() {
            @Override
            public void onResponse(Response<estansaas.fonebayad.auth.Responses.Response> response, Retrofit retrofit) {

                Log.i("ACTIVITY PAYMENT METHOD", response.message());

                if (response.isSuccess()) {
                    if (response.code() == 200) {
                        Intent intent = new Intent(ActivityPaymentMethod.this, ActivityViewBills.class);
                        intent.putExtra("ModelBillInformation", modelBillInformation);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                    }
                } else {
                    Util.ShowMessage(coordinatorLayout, "Process Failed please try again");
                }
                dialogInterface.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                dialogInterface.dismiss();
                Util.ShowMessage(coordinatorLayout, "Failed to connect to server!");
            }
        });
    }

    @Override
    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
        switch (dialogAction) {
            case POSITIVE:
                switch (ACTIVITY_CURRENT_PROCESS) {
                    case 101:
                        LoadData();
                        break;
                    case 201:
                        ProcessBill();
                        break;
                    default:
                        break;
                }
                break;
            case NEGATIVE:
            case NEUTRAL:
                materialDialog.dismiss();
                break;
            default:
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
