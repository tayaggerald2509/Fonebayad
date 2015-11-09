package estansaas.fonebayad.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import estansaas.fonebayad.adapter.AdapterListOffer;
import estansaas.fonebayad.auth.Responses.ResponseOffer;
import estansaas.fonebayad.auth.RestClient;
import estansaas.fonebayad.model.ModelLogin;
import estansaas.fonebayad.model.ModelOffer;
import estansaas.fonebayad.utils.Network;
import estansaas.fonebayad.utils.Util;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by gerald.tayag on 10/7/2015.
 */
public class FragmentOffer extends Fragment implements MaterialDialog.SingleButtonCallback, AdapterView.OnItemClickListener {

    private List<ModelOffer> modelOfferList;
    private AdapterListOffer adapterListOffer;

    @Bind(R.id.list_offer)
    public ListView list_offer;

    private static Intent intent;
    private static Activity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);
        ButterKnife.bind(this, view);

        activity = getActivity();

        ShowAuthDialog();
        return view;
    }

    private void ShowAuthDialog() {
        modelOfferList = new ArrayList<ModelOffer>();
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
                            AuthOfferData(dialogInterface);
                        }
                    }).show();
        } else {
            Util.ShowNeutralDialog(getActivity(), "fonebayad", "Please connect to internet.", "OK", this);
        }
    }

    private void AuthOfferData(final DialogInterface dialogInterface) {
        Call<ResponseOffer> responseOfferCall = RestClient.get().getOffers(ModelLogin.getUserInfo().getApp_id());

        responseOfferCall.enqueue(new Callback<ResponseOffer>() {
            @Override
            public void onResponse(Response<ResponseOffer> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body().getMessage().toLowerCase().equals("success")) {
                        modelOfferList = response.body().getModelOfferList();

                        adapterListOffer = new AdapterListOffer(getActivity(), modelOfferList);
                        list_offer.setOnItemClickListener(FragmentOffer.this);
                        list_offer.setAdapter(adapterListOffer);
                    }
                } else {
                    Util.ShowDialog(getActivity(), "Offers", "Failed to conenct to server!", "RETRY", "CANCEL", FragmentOffer.this);
                }
                dialogInterface.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                dialogInterface.dismiss();
                Util.ShowDialog(getActivity(), "Offers", t.getMessage(), "RETRY", "CANCEL", FragmentOffer.this);
            }
        });
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, long id) {
        Util.ShowDialog(getActivity(), "fonebayad", "Do you want to go to this site " + modelOfferList.get(position).getUrl() + "?", "YES", "NO", new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                switch (dialogAction) {
                    case POSITIVE:
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + modelOfferList.get(position).getUrl()));
                        activity.startActivity(intent);
                        break;
                    case NEGATIVE:
                        materialDialog.dismiss();
                        break;
                    default:
                        break;
                }
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
}
