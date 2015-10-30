package estansaas.fonebayad.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import estansaas.fonebayad.R;
import estansaas.fonebayad.adapter.AdapterNotificationList;
import estansaas.fonebayad.auth.Responses.ResponseNotification;
import estansaas.fonebayad.auth.RestClient;
import estansaas.fonebayad.model.ModelBillStatement;
import estansaas.fonebayad.model.ModelLogin;
import estansaas.fonebayad.utils.Network;
import estansaas.fonebayad.utils.Util;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by gerald.tayag on 10/7/2015.
 */
public class FragmentNotification extends Fragment implements MaterialDialog.SingleButtonCallback {

    private List<ModelBillStatement> billStatements;
    private AdapterNotificationList adapterListViewBillStatement;

    @Bind(R.id.list_notification)
    public ListView list_notification;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, view);

        ShowAuthDialog();
        return view;
    }

    private void ShowAuthDialog() {
        billStatements = new ArrayList<ModelBillStatement>();
        if (Network.isConnected(getActivity())) {
            new MaterialDialog.Builder(getActivity())
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
                            AuthNotifcationData(dialogInterface);
                        }
                    }).show();
        } else {
            Util.ShowNeutralDialog(getActivity(), "fonebayad", "Please connect to internet.", "OK", this);
        }
    }

    private void AuthNotifcationData(final DialogInterface dialogInterface) {
        Call<ResponseNotification> responseNotificationCall = RestClient.get().getNotification(ModelLogin.getUserInfo().getApp_id());

        responseNotificationCall.enqueue(new Callback<ResponseNotification>() {
            @Override
            public void onResponse(Response<ResponseNotification> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body().getMessage().equals("Success")) {
                        billStatements = response.body().getModelUserBillStatemenetData().getModelBillStatementList();

                        adapterListViewBillStatement = new AdapterNotificationList(getActivity(), billStatements);
                        list_notification.setAdapter(adapterListViewBillStatement);
                    }
                } else {

                }
                dialogInterface.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                dialogInterface.dismiss();
            }
        });
    }

    @Override
    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
        switch (dialogAction) {
            case POSITIVE:
                ShowAuthDialog();
                break;
            case NEGATIVE:
            case NEUTRAL:
                materialDialog.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
