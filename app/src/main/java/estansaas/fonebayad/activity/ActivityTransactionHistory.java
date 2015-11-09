package estansaas.fonebayad.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import estansaas.fonebayad.R;
import estansaas.fonebayad.adapter.AdapterListTransaction;
import estansaas.fonebayad.auth.Responses.ResponseTransaction;
import estansaas.fonebayad.auth.RestClient;
import estansaas.fonebayad.model.ModelTransaction;
import estansaas.fonebayad.utils.Network;
import estansaas.fonebayad.utils.Util;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by gerald.tayag on 10/28/2015.
 */
public class ActivityTransactionHistory extends BaseActivity implements MaterialDialog.SingleButtonCallback, AdapterView.OnItemClickListener {

    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    @Bind(R.id.list_transaction)
    public ListView list_transaction;

    private List<ModelTransaction> modelTransactionList;
    private AdapterListTransaction adapterListTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ShowDialog();
    }


    private void ShowDialog() {
        if (Network.isConnected(this)) {
            new MaterialDialog.Builder(this)
                    .content(R.string.action_wait)
                    .contentGravity(GravityEnum.CENTER)
                    .theme(Theme.DARK)
                    .widgetColor(Color.WHITE)
                    .progressIndeterminateStyle(false)
                    .progress(true, 0)
                    .cancelable(true)
                    .showListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            AuthTransactionHistory(dialogInterface);
                        }
                    }).show();
        }
    }

    private void AuthTransactionHistory(final DialogInterface dialogInterface) {
        Call<ResponseTransaction> responseTransactionCall = RestClient.get().getUserTransactionHistory("249");
        responseTransactionCall.enqueue(new Callback<ResponseTransaction>() {
            @Override
            public void onResponse(Response<ResponseTransaction> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    modelTransactionList = response.body().getModelTransaction();
                    InitializeAdapter();
                }
                dialogInterface.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                dialogInterface.dismiss();
                Util.ShowNeutralDialog(ActivityTransactionHistory.this, "warning", "Unable to connect to server!", "OK", ActivityTransactionHistory.this);
            }
        });
    }

    private void InitializeAdapter() {
        adapterListTransaction = new AdapterListTransaction(this, modelTransactionList);
        list_transaction.setAdapter(adapterListTransaction);
        list_transaction.setOnItemClickListener(this);
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
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, long id) {
        Intent intent = new Intent(this, ActivityTransactionDetails.class);
        intent.putExtra("TRANSACTION", modelTransactionList.get(position));
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
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
