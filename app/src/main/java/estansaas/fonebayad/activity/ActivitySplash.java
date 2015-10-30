package estansaas.fonebayad.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import estansaas.fonebayad.R;
import estansaas.fonebayad.auth.Responses.ResponseSyncData;
import estansaas.fonebayad.auth.RestClient;
import estansaas.fonebayad.model.ModelBillCategory;
import estansaas.fonebayad.model.ModelBillers;
import estansaas.fonebayad.model.ModelCountry;
import estansaas.fonebayad.model.ModelCurrency;
import estansaas.fonebayad.model.ModelDataHolder;
import estansaas.fonebayad.model.ModelLogin;
import estansaas.fonebayad.utils.Network;
import estansaas.fonebayad.utils.Util;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by gerald.tayag on 10/9/2015.
 */
public class ActivitySplash extends AppCompatActivity {

    private Calendar calendar;
    private ModelBillers modelBillers;
    private ModelBillCategory modelBillCategory;
    private ModelCurrency modelCurrency;
    private ModelCountry modelCountry;

    @Bind(R.id.lblFone)
    public TextView lblFone;

    private Thread timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        lblFone.setTypeface(Util.setTypeface(this, Util.MYRIAD_BOLD_ITALIC));

        SyncDataFromServer();
        startNewScreen();
    }

    private void SyncDataFromServer() {
        calendar = Calendar.getInstance();
        if (Network.isConnected(this)) {
            ModelDataHolder.DeleteSyncData();

            final Call<ResponseSyncData> syncDataResponseCall = RestClient.get().SyncData("1");

            syncDataResponseCall.enqueue(new Callback<ResponseSyncData>() {
                @Override
                public void onResponse(Response<ResponseSyncData> response, Retrofit retrofit) {
                    if (response.isSuccess()) {

                        if (response.code() == 200) {
                            for (ModelBillers billsModel : response.body().getModelSyncData().getModelBillerses()) {
                                modelBillers = billsModel;
                                billsModel.save();
                            }

                            Log.i("Biller", String.valueOf(ModelBillers.count()));

                            for (ModelBillCategory categoryModel : response.body().getModelSyncData().getModelBillCategories()) {
                                modelBillCategory = categoryModel;
                                modelBillCategory.save();
                            }

                            Log.i("Category", String.valueOf(ModelBillCategory.count()));

                            for (ModelCurrency currency : response.body().getModelSyncData().getModelCurrencies()) {
                                modelCurrency = currency;
                                modelCurrency.save();
                            }

                            Log.i("Currency", String.valueOf(ModelCurrency.count()));

                            for (ModelCountry country : response.body().getModelSyncData().getModelCountries()) {
                                modelCountry = country;
                                modelCountry.save();
                            }

                            Log.i("Country", String.valueOf(ModelCountry.count()));
                        }

                        ResponseSyncData syncDataResponse = response.body();
                        Log.i("Login Response", String.valueOf(syncDataResponse.getStatus()));
                    } else {
                        int statusCode = response.code();
                        Log.i("Login Error", String.valueOf(statusCode));
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    syncDataResponseCall.cancel();
                }
            });
        }
    }

    private void startNewScreen() {
        timer = new Thread() {
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                    try {
                        int id = getIntent().getExtras().getInt("EXTRA_EDGE_SESSION_ID");
                        if (ModelLogin.countUser() > 0) {
                            if (id == R.id.btn_addBill) {
                                Util.startNextActivity(ActivitySplash.this, ActivityAddManualBill.class);
                            } else if (id == R.id.btn_transpera) {
                                Util.startNextActivity(ActivitySplash.this, ActivityTranspera.class);
                            } else {
                                Util.startNextActivity(ActivitySplash.this, ActivitySplash.class);
                            }
                        } else {

                        }
                    } catch (Exception e) {
                        gotoNext();
                    }

                }
            }
        };
        timer.start();
    }

    private void gotoNext() {
        finish();
        Util.startNextActivity(ActivitySplash.this, ActivityLogin.class);
    }
}
