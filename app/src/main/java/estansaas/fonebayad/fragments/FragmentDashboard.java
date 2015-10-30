package estansaas.fonebayad.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import estansaas.fonebayad.R;
import estansaas.fonebayad.activity.ActivityAddBill;
import estansaas.fonebayad.activity.ActivityCalendar;
import estansaas.fonebayad.activity.ActivityMyBills;
import estansaas.fonebayad.activity.ActivityTranspera;
import estansaas.fonebayad.auth.Responses.ResponseBankAccount;
import estansaas.fonebayad.auth.Responses.ResponseBillStatement;
import estansaas.fonebayad.auth.Responses.ResponseUserSophisticate;
import estansaas.fonebayad.auth.RestClient;
import estansaas.fonebayad.model.ModelBankAccount;
import estansaas.fonebayad.model.ModelBillStatement;
import estansaas.fonebayad.model.ModelLogin;
import estansaas.fonebayad.model.ModelSophisticate;
import estansaas.fonebayad.utils.Constants;
import estansaas.fonebayad.utils.Network;
import estansaas.fonebayad.utils.Util;
import estansaas.fonebayad.view.AutofitTextView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by gerald.tayag on 10/7/2015.
 */
public class FragmentDashboard extends Fragment {

    private static int FRAGMENT_DIALOG_ID;

    @Bind(R.id.coordinatorLayout)
    public CoordinatorLayout coordinatorLayout;

    @Bind(R.id.ll_calendar)
    public LinearLayout ll_calendar;

    @Bind(R.id.ll_overdue)
    public LinearLayout ll_overdue;

    @Bind(R.id.ll_week)
    public LinearLayout ll_week;

    @Bind(R.id.ll_total_due)
    public LinearLayout ll_total_due;

    @Bind(R.id.lblOverDue)
    public TextView lblOverDue;

    @Bind(R.id.lbl_currency_overdue)
    public TextView lbl_currency_overdue;

    @Bind(R.id.txtOverdue)
    public AutofitTextView txtOverdue;

    @Bind(R.id.lblWeek)
    public TextView lblWeek;

    @Bind(R.id.lbl_currency_week)
    public TextView lbl_currency_week;

    @Bind(R.id.txtWeek)
    public AutofitTextView txtWeek;

    @Bind(R.id.txtDateDay)
    public TextView txtDateDay;

    @Bind(R.id.txtDay)
    public TextView txtDay;

    @Bind(R.id.txtMonth)
    public TextView txtMonth;

    @Bind(R.id.lblTotal)
    public TextView lblTotal;

    @Bind(R.id.lblBillsDue)
    public TextView lblBillsDue;

    @Bind(R.id.lbl_currency_total)
    public TextView lbl_currency_total;

    @Bind(R.id.txtTotal)
    public TextView txtTotal;

    @Bind(R.id.txtTranspera)
    public TextView txtTranspera;

    @Bind(R.id.btnAddBill)
    public Button btnAddBill;

    @Bind(R.id.ll_transpera)
    public LinearLayout ll_transpera;

    private Double OverDue = 0.0;
    private Double Week = 0.0;
    private Double TotalBillDue = 0.0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, view);
        InitializeView();

        AuthPrimaryBankDetails();
        SyncDashboardData();

        return view;
    }

    private void InitializeView() {
        lblOverDue.setTypeface(Util.setTypeface(getActivity(), Util.ROBOTO));
        lbl_currency_overdue.setTypeface(Util.setTypeface(getActivity(), Util.ROBOTO));
        txtOverdue.setTypeface(Util.setTypeface(getActivity(), Util.ROBOTO));

        lblWeek.setTypeface(Util.setTypeface(getActivity(), Util.ROBOTO));
        lbl_currency_week.setTypeface(Util.setTypeface(getActivity(), Util.ROBOTO));
        txtWeek.setTypeface(Util.setTypeface(getActivity(), Util.ROBOTO));

        txtDateDay.setTypeface(Util.setTypeface(getActivity(), Util.ROBOTO), Typeface.BOLD);
        txtDay.setTypeface(Util.setTypeface(getActivity(), Util.ROBOTO), Typeface.BOLD);
        txtMonth.setTypeface(Util.setTypeface(getActivity(), Util.ROBOTO));

        lblTotal.setTypeface(Util.setTypeface(getActivity(), Util.ROBOTO));
        lblBillsDue.setTypeface(Util.setTypeface(getActivity(), Util.ROBOTO_LIGHT));
        lbl_currency_total.setTypeface(Util.setTypeface(getActivity(), Util.ROBOTO));
        txtTotal.setTypeface(Util.setTypeface(getActivity(), Util.ROBOTO));

        txtTranspera.setTypeface(Util.setTypeface(getActivity(), Util.ROBOTO_LIGHT), Typeface.BOLD);
        btnAddBill.setTypeface(Util.setTypeface(getActivity(), Util.ROBOTO), Typeface.BOLD);

        txtDay.setText(Util.getDay());
        txtDateDay.setText(Util.getDateDay());
        txtMonth.setText(Util.getMonth());

    }

    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }

    @OnClick(R.id.btnAddBill)
    public void AddBill() {
        Intent intent = new Intent(getActivity(), ActivityAddBill.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.ll_calendar)
    public void ShowCalendar() {
        Intent intent = new Intent(getActivity(), ActivityCalendar.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.ll_overdue)

    public void ShowOverdue() {
        Intent intent = new Intent(getActivity(), ActivityMyBills.class);
        intent.putExtra("EXTRA_SESSION_ID", 1);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.ll_week)
    public void ShowWeek() {
        Intent intent = new Intent(getActivity(), ActivityMyBills.class);
        intent.putExtra("EXTRA_SESSION_ID", 2);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.ll_total_due)
    public void ShowTotalDue() {
        Intent intent = new Intent(getActivity(), ActivityMyBills.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @OnClick(R.id.ll_transpera)
    public void ShowTranspera() {

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
                            AuthUserSophisticate(dialogInterface);
                        }
                    }).show();
        } else {

        }
        //Util.startNextActivity(getActivity(), ActivityTranspera.class);
    }

    private void AuthPrimaryBankDetails() {

        if (Network.isConnected(getActivity())) {
            FRAGMENT_DIALOG_ID = 201;
            Call<ResponseBankAccount> responseBankAccountCall = RestClient.get().getAllActiveBankAccounts(ModelLogin.getUserInfo().getApp_id());
            responseBankAccountCall.enqueue(new Callback<ResponseBankAccount>() {
                @Override
                public void onResponse(Response<ResponseBankAccount> response, Retrofit retrofit) {
                    if (response.isSuccess()) {

                        for (ModelBankAccount modelBankAccount : response.body().getModelBankAccount()) {
                            if (modelBankAccount.getBankaccount_sortorder().equals("1")) {
                                txtTranspera.setText(new DecimalFormat("#,##0.00").format(Double.valueOf(modelBankAccount.getBankaccount_amount())));
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            });

        }
    }

    private void AuthUserSophisticate(final DialogInterface dialogInterface) {
        Call<ResponseUserSophisticate> responseUserSophisticateCall = RestClient.get().checkUserSophisticate(ModelLogin.getUserInfo().getApp_id());
        responseUserSophisticateCall.enqueue(new Callback<ResponseUserSophisticate>() {
            @Override
            public void onResponse(Response<ResponseUserSophisticate> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    String msg;
                    if (response.body().getStatus().contains(Constants.STATUS_ACCEPTED)) {
                        ModelSophisticate modelSophisticate = response.body().getModelSophisticate();
                        if (modelSophisticate.getSop_status().equals("reject")) {
                            msg = "Your application has been rejected. Do you want to proceed to the KYC verification again";
                            Util.ShowDialog(getActivity(), "fonebayad", msg, "CONTINUE", "CANCEL", sophisticateCallback);
                        } else if (modelSophisticate.getSop_status().equals("for_approval")) {
                            msg = "Your application for KYC was successfully sent. Please wait for the administrator's approval.";
                            Util.ShowNeutralDialog(getActivity(), "fonebayad", msg, "OK", sophisticateCallback);
                        } else {
                            Util.startNextActivity(getActivity(), ActivityTranspera.class);
                        }
                    }
                } else {
                    Util.ShowNeutralDialog(getActivity(), "fonebayad", response.message(), "OK", sophisticateCallback);
                }
                dialogInterface.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                dialogInterface.dismiss();
                Util.ShowDialog(getActivity(), "fonebayad", "We are required by law to verify your address and identity. Do you want to proceed to the KYC verification", "CONTINUE", "CANCEL", sophisticateCallback);
            }
        });
    }

    private void SyncDashboardData() {
        if (Network.isConnected(getActivity())) {
            FRAGMENT_DIALOG_ID = 101;
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
                            AuthDashboardData(dialogInterface);
                        }
                    }).show();

        } else {
            Util.ShowNeutralDialog(getActivity(), "fonebayad", "Please connect to internet", "OK", dashboardCallback);
        }
    }

    private void AuthDashboardData(final DialogInterface dialogInterface) {

        final Call<ResponseBillStatement> billStatementResponseCall = RestClient.get().SyncDashboardRecord(ModelLogin.getUserInfo().getApp_id(), Util.getGUID(getActivity()));
        billStatementResponseCall.enqueue(new Callback<ResponseBillStatement>() {
            @Override
            public void onResponse(Response<ResponseBillStatement> response, Retrofit retrofit) {

                if (response.isSuccess()) {
                    if (response.code() == 200) {
                        OverDue = 0.0;
                        Week = 0.0;
                        TotalBillDue = 0.0;

                        for (ModelBillStatement modelBillStatement : response.body().getModelBillStatementData().getModelBillStatementList()) {
                            try {
                                if (new Date().compareTo(Util.convertDate(modelBillStatement.getBill_duedate())) >= 0) {
                                    OverDue += Double.valueOf(modelBillStatement.getBill_balance());
                                } else {
                                    if (Util.getWeekDate().after(Util.convertDate(modelBillStatement.getBill_duedate()))) {
                                        Week += Double.valueOf(modelBillStatement.getBill_balance());
                                    }
                                }
                                TotalBillDue += Double.valueOf(modelBillStatement.getBill_balance());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Log.i("Bill Statements", modelBillStatement.getBillId() + " : " + modelBillStatement.getBill_balance());
                        }

                        txtOverdue.setText(new DecimalFormat("#,##0.00").format(OverDue));
                        txtWeek.setText(new DecimalFormat("#,##0.00").format(Week));
                        txtTotal.setText(new DecimalFormat("#,##0.00").format(TotalBillDue));
                        dialogInterface.dismiss();
                        return;
                    }
                }
                dialogInterface.dismiss();
                Util.ShowDialog(getActivity(), "fonebayad", "Failed to connect to server", "RETRY", "CANCEL", dashboardCallback);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                try {
                    dialogInterface.dismiss();
                    billStatementResponseCall.cancel();
                    Util.ShowDialog(getActivity(), "fonebayad", "Unable to connect to server", "RETRY", "CANCEL", dashboardCallback);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        SyncDashboardData();
    }

    private MaterialDialog.SingleButtonCallback dashboardCallback = new MaterialDialog.SingleButtonCallback() {
        @Override
        public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
            switch (dialogAction) {
                case POSITIVE:
                    materialDialog.dismiss();
                    SyncDashboardData();
                    break;
                case NEGATIVE:
                case NEUTRAL:
                    materialDialog.dismiss();
                    break;
                default:
                    materialDialog.dismiss();
                    break;
            }
        }
    };

    private MaterialDialog.SingleButtonCallback sophisticateCallback = new MaterialDialog.SingleButtonCallback() {
        @Override
        public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
            switch (dialogAction) {
                case POSITIVE:
                    Toast.makeText(getActivity(), "KYC is underconstruction please wait for a while.", Toast.LENGTH_SHORT).show();
                    break;
                case NEGATIVE:
                case NEUTRAL:
                    materialDialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    };

}
