package estansaas.fonebayad.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import estansaas.fonebayad.R;
import estansaas.fonebayad.adapter.AdapterIconViewBillStatement;
import estansaas.fonebayad.adapter.AdapterListViewBillStatement;
import estansaas.fonebayad.auth.Responses.ResponseBankAccount;
import estansaas.fonebayad.auth.Responses.ResponseBillStatement;
import estansaas.fonebayad.auth.RestClient;
import estansaas.fonebayad.model.ModelBankAccount;
import estansaas.fonebayad.model.ModelBillCategory;
import estansaas.fonebayad.model.ModelBillInformation;
import estansaas.fonebayad.model.ModelBillStatement;
import estansaas.fonebayad.model.ModelLogin;
import estansaas.fonebayad.utils.Network;
import estansaas.fonebayad.utils.Util;
import estansaas.fonebayad.view.AutofitTextView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by gerald.tayag on 10/12/2015.
 */
public class ActivityMyBills extends BaseActivity implements AdapterView.OnItemClickListener, CheckBox.OnCheckedChangeListener, View.OnClickListener, MaterialDialog.ListCallback, MaterialDialog.SingleButtonCallback {

    public static final String SAVED_DATA_KEY = "SAVED_DATA";

    @Bind(R.id.coordinatorLayout)
    public CoordinatorLayout coordinatorLayout;

    @Bind(R.id.ll_message)
    public LinearLayout ll_message;

    @Bind(R.id.ll_toolbar)
    public LinearLayout ll_toolbar;

    @Bind(R.id.chk_select)
    public CheckBox chk_select;

    @Bind(R.id.chk_category)
    public CheckBox chk_category;

    @Bind(R.id.chk_zoom)
    public CheckBox chk_zoom;

    @Bind(R.id.chk_delete)
    public CheckBox chk_delete;

    @Bind(R.id.chk_list_type)
    public CheckBox chk_list_type;

    @Bind(R.id.listview_icon)
    public GridView listview_icon;

    @Bind(R.id.listview_list)
    public ListView listview_list;

    @Bind(R.id.txtNoOfSelected)
    public TextView txtNoOfSelected;

    @Bind(R.id.txtNoOfBills)
    public TextView txtNoOfBills;

    @Bind(R.id.txtTotalSelected)
    public AutofitTextView txtTotalSelected;

    @Bind(R.id.txtSelect)
    public TextView txtSelect;

    @Bind(R.id.txtView)
    public TextView txtView;

    @Bind(R.id.chk_mybills)
    public CheckBox chk_mybills;

    @Bind(R.id.txtAccountBalance)
    public TextView txtAccountBalance;

    LinearLayout ll_tool, ll_view, ll_share, ll_pay;

    private AdapterIconViewBillStatement iconAdapter;
    private AdapterListViewBillStatement listAdapter;

    private ModelBillInformation modelBillInfo;

    private List<ModelBillInformation> billStatements;
    private List<ModelBillInformation> selectedBill;
    private List<ModelBillCategory> mItems;
    private List<String> listSelected;
    private String[] mListableItems;

    private int selected = 0;
    private Double totalSelected = 0.0;
    private Double AccountBalance = 0.0;
    private Animation bottom_up, bottom_down;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mybills);
        ButterKnife.bind(this);

        modelBillInfo = new ModelBillInformation();

        bottom_up = AnimationUtils.loadAnimation(this, R.anim.bottom_up);
        bottom_down = AnimationUtils.loadAnimation(this, R.anim.bottom_down);

        ll_tool = (LinearLayout) this.findViewById(R.id.ll_tool);
        ll_view = (LinearLayout) this.findViewById(R.id.ll_view);
        ll_share = (LinearLayout) this.findViewById(R.id.ll_share);
        ll_pay = (LinearLayout) this.findViewById(R.id.ll_pay);

        ll_view.setOnClickListener(this);
        ll_share.setOnClickListener(this);
        ll_pay.setOnClickListener(this);

        ll_tool.setVisibility(View.INVISIBLE);

        InitializeData();

        listview_icon.setNumColumns(getResources().getInteger(R.integer.grid_column_count));
        listview_icon.setOnItemClickListener(this);

        chk_select.setOnCheckedChangeListener(this);
        chk_zoom.setOnCheckedChangeListener(this);
        chk_list_type.setOnCheckedChangeListener(this);
        chk_delete.setOnCheckedChangeListener(this);
        chk_category.setOnCheckedChangeListener(this);

        listSelected = new ArrayList<>();
        selectedBill = new ArrayList<>();

        chk_mybills.setChecked(true);
        AuthPrimaryBankDetails();
    }

    @OnClick(R.id.back)
    public void Back() {
        onBackPressed();
    }

    @OnClick(R.id.expanded_menu)
    public void toggleMenu() {
        showMenu();
    }

    @OnClick(R.id.ll_message)
    public void AddBill() {
        GoToAddBill();
    }

    @OnClick(R.id.ll_dashboard)
    public void GoToDashboard() {
        finish();
        Util.startNextActivity(this, ActivityCalendar.class);
    }

    @OnClick(R.id.ll_bills)
    public void GoToMyBills() {
        // finish();
        // Util.startNextActivity(this, ActivityMyBills.class);
    }

    @OnClick(R.id.ll_add_bill)
    public void GoToAddBill() {
        finish();
        Util.startNextActivity(this, ActivityAddBill.class);
    }

    @OnClick(R.id.ll_cal)
    public void GoToCalculator() {
    }

    @OnClick(R.id.ll_report)
    public void GoToReports() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_share:
                ActivityShareBill.billStatements = selectedBill;
                Intent intent = new Intent(this, ActivityShareBill.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                break;
            case R.id.ll_view:
            case R.id.ll_pay:
                if (selected == 1) {

                    modelBillInfo = estansaas.fonebayad.model.ModelBillInformation.viewBillStatement(listSelected.get(0));
                    SimpleDateFormat sdf = new SimpleDateFormat("ccc MMMM dd hh:mm:ss zzzz yyyy");

                    try {
                        Date date = sdf.parse(modelBillInfo.getDue_date().toString());
                        sdf = new SimpleDateFormat("MMM dd, yyyy");
                        modelBillInfo.setBill_due_date(sdf.format(date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    intent = new Intent(this, ActivityViewBills.class);
                    intent.putExtra("EXTRA_BILL_ADDED", true);
                    intent.putExtra("ModelBillInformation", modelBillInfo);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                } else {
                    for (ModelBillInformation modelBillInformation : selectedBill) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(modelBillInformation.getDue_date());
                        if (new Date().before(calendar.getTime())) {
                            ActivityMultiplePayment.selectedBill = selectedBill;
                            intent = new Intent(this, ActivityMultiplePayment.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                            break;
                        } else {
                            Util.ShowNeutralDialog(this, "Warning", "Unable to proceed with payment. Your bill " + modelBillInformation.getBiller_name() + " is already overdue", "Ok", this);
                            break;
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    private void InitiliazeAdapters() {
        iconAdapter = new AdapterIconViewBillStatement(this, billStatements);
        listAdapter = new AdapterListViewBillStatement(this, billStatements);
        listview_icon.setAdapter(iconAdapter);
        listview_list.setAdapter(listAdapter);
    }

    private void InitializeList(int count) {
        listview_icon.setNumColumns(count);
        iconAdapter.notifyDataSetChanged();
    }

    private void InitializeTools(Boolean isEnabled) {
        chk_select.setEnabled(isEnabled);
        chk_zoom.setEnabled(isEnabled);
        chk_category.setEnabled(isEnabled);
        chk_list_type.setEnabled(isEnabled);
        if (selected > 0) {
            chk_delete.setEnabled(true);
        } else {
            chk_delete.setEnabled(false);
        }
    }

    private void InitializeOverDue() {
        Calendar calendar = Calendar.getInstance();
        for (ModelBillInformation billStatement : estansaas.fonebayad.model.ModelBillInformation.getBillStatement()) {
            calendar.setTime(billStatement.getDue_date());
            Calendar today = Calendar.getInstance();
            long diff = calendar.getTimeInMillis() - today.getTimeInMillis();
            long days = diff / (24 * 60 * 60 * 1000);
            long i = days * -1;

            if (new Date().after(calendar.getTime())) {
                if (String.valueOf(i).compareTo("0") == 0) {
                    billStatement.setNoOfDays("OVERDUE");
                } else {
                    billStatement.setNoOfDays(String.valueOf(Util.getDifferenceDays(calendar, today)) + " DAYS OVERDUE");
                }
                billStatement.setType("OVERDUE");
                billStatements.add(billStatement);
            }
        }
    }

    private void InitializeWeek() {
        Calendar calendar = Calendar.getInstance();
        for (ModelBillInformation billStatement : estansaas.fonebayad.model.ModelBillInformation.getBillStatement()) {
            calendar.setTime(billStatement.getDue_date());
            Calendar today = Calendar.getInstance();
            long diff = calendar.getTimeInMillis() - today.getTimeInMillis();
            long days = diff / (24 * 60 * 60 * 1000);
            long i = days * -1;

            if (new Date().before(calendar.getTime())) {
                if (Util.getWeekDate().after(calendar.getTime())) {
                    billStatement.setType("WEEK");
                    billStatement.setNoOfDays(String.valueOf(Util.getDifferenceDays(calendar, today)) + " DAYS LEFT");
                    billStatements.add(billStatement);
                }
            }
        }
    }

    private void InitializeAll() {
        Calendar calendar = Calendar.getInstance();
        for (ModelBillInformation billStatement : estansaas.fonebayad.model.ModelBillInformation.getBillStatement()) {
            calendar.setTime(billStatement.getDue_date());
            Calendar today = Calendar.getInstance();
            long diff = calendar.getTimeInMillis() - today.getTimeInMillis();
            long aday = 24 * 60 * 60 * 1000;
            long days = diff / aday;
            long i = days * -1;

            if (new Date().before(calendar.getTime())) {
                if (Util.getWeekDate().before(calendar.getTime())) {
                    billStatement.setType("DUE");
                } else {
                    billStatement.setType("WEEK");
                }
                billStatement.setNoOfDays(String.valueOf(Util.getDifferenceDays(calendar, today)) + " DAYS LEFT");
            } else {
                if (String.valueOf(i).compareTo("0") == 0) {
                    billStatement.setNoOfDays("OVERDUE");
                } else {
                    billStatement.setNoOfDays(String.valueOf(Util.getDifferenceDays(calendar, today)) + " DAYS OVERDUE");
                }
                billStatement.setType("OVERDUE");
            }
            billStatements.add(billStatement);
        }
    }

    private void InitializeData() {
        billStatements = new ArrayList<>();
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
                            txtNoOfBills.setText(String.valueOf(billStatements.size()));
                            InitiliazeAdapters();
                            ShowMessage(billStatements.size());
                        }
                    })
                    .showListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            AuthMyBillsData(dialogInterface);
                        }
                    }).show();
        } else {
            for (ModelBillInformation billStatement : estansaas.fonebayad.model.ModelBillInformation.getBillStatement()) {
                billStatements.add(billStatement);
            }

            InitiliazeAdapters();
            ShowMessage(ModelBillInformation.getBillStatement().size());
        }
    }

    private void ShowMessage(int size) {
        if (size > 0) {
            ll_message.setVisibility(View.INVISIBLE);
            InitializeTools(true);
        } else {
            ll_message.setVisibility(View.VISIBLE);
            InitializeTools(false);
        }
    }

    private void AuthMyBillsData(final DialogInterface dialogInterface) {

        Call<ResponseBillStatement> billStatementResponseCall = RestClient.get().SyncDashboardRecord(ModelLogin.getUserInfo().getApp_id(), Util.getGUID(this));
        billStatementResponseCall.enqueue(new Callback<ResponseBillStatement>() {
            @Override
            public void onResponse(Response<ResponseBillStatement> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.code() == 200) {
                        estansaas.fonebayad.model.ModelBillInformation.DeleteBillStatement();
                        for (ModelBillStatement modelBillStatement : response.body().getModelBillStatementData().getModelBillStatementList()) {
                            try {
                                String[] date = Util.StringconvertDate(modelBillStatement.getBill_duedate()).split("-");
                                ModelBillInformation billStatement = new ModelBillInformation();
                                billStatement.setBill_Id(modelBillStatement.getBillId());
                                billStatement.setBill_biller(modelBillStatement.getBill_biller());
                                billStatement.setBiller_name(modelBillStatement.getBiller_name());
                                billStatement.setBill_account_number(modelBillStatement.getBill_accountnumber());
                                billStatement.setBill_transaction_number(modelBillStatement.getBill_transactionnumber());
                                billStatement.setBill_amount(modelBillStatement.getBill_amount());
                                billStatement.setBalance(modelBillStatement.getBill_balance());
                                billStatement.setBill_status(modelBillStatement.getBill_status());
                                billStatement.setMonth(String.valueOf(Integer.valueOf(date[1])));
                                billStatement.setYear(date[2]);
                                billStatement.setBill_biller(modelBillStatement.getBill_biller());
                                billStatement.setBiller_category(Integer.valueOf(modelBillStatement.getBiller_category()));
                                billStatement.setBill_share(Integer.valueOf(modelBillStatement.getBill_share()));
                                billStatement.setBill_due_date(modelBillStatement.getBill_duedate());
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                                billStatement.setDue_date(simpleDateFormat.parse(modelBillStatement.getBill_duedate()));
                                billStatement.save();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }

                        switch (getIntent().getIntExtra("EXTRA_SESSION_ID", 0)) {
                            //OverDue
                            case 1:
                                InitializeOverDue();
                                break;
                            //Week
                            case 2:
                                InitializeWeek();
                                break;
                            //ALL
                            default:
                                InitializeAll();
                                break;
                        }
                    } else {
                        Util.ShowMessage(coordinatorLayout, "Can't fetch data from server.");
                    }
                } else {
                    Util.ShowMessage(coordinatorLayout, "Can't fetch data from server.");
                }
                dialogInterface.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                dialogInterface.dismiss();
                Util.ShowNeutralDialog(ActivityMyBills.this, "", t.getMessage(), "OK", ActivityMyBills.this);
            }
        });
    }

    private void AuthPrimaryBankDetails() {
        if (Network.isConnected(this)) {
            Call<ResponseBankAccount> responseBankAccountCall = RestClient.get().getAllActiveBankAccounts(ModelLogin.getUserInfo().getApp_id());
            responseBankAccountCall.enqueue(new Callback<ResponseBankAccount>() {
                @Override
                public void onResponse(Response<ResponseBankAccount> response, Retrofit retrofit) {
                    if (response.isSuccess()) {

                        for (ModelBankAccount modelBankAccount : response.body().getModelBankAccount()) {
                            if (modelBankAccount.getBankaccount_sortorder().equals("1")) {
                                AccountBalance = Double.valueOf(modelBankAccount.getBankaccount_amount());
                                txtAccountBalance.setText("Php " + new DecimalFormat("#,##0.00").format(Double.valueOf(modelBankAccount.getBankaccount_amount())));
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

    private void animateToolPay() {
        if (selected > 0) {
            if (ll_tool.getVisibility() == View.INVISIBLE) {
                ll_tool.startAnimation(bottom_up);
                ll_tool.setVisibility(View.VISIBLE);
            }
        } else {
            if (ll_tool.getVisibility() == View.VISIBLE) {
                ll_tool.startAnimation(bottom_down);
                ll_tool.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.chk_select:
                totalSelected = 0.00;
                selectedBill.clear();
                for (ModelBillInformation billstatement : billStatements) {
                    totalSelected += Double.valueOf(billstatement.getBalance());
                    billstatement.setIsSelected(isChecked);
                    billstatement.save();
                }

                if (isChecked) {
                    txtNoOfSelected.setText(String.valueOf(billStatements.size()));
                    txtTotalSelected.setText("PHP " + new DecimalFormat("#,##0.00").format(totalSelected < 0.00 ? (totalSelected * -1) : totalSelected));
                    selected = Integer.valueOf(txtNoOfSelected.getText().toString());
                    ll_tool.setVisibility(View.VISIBLE);
                    chk_delete.setEnabled(true);
                    selectedBill.addAll(billStatements);
                } else {
                    selected = 0;
                    txtNoOfSelected.setText("0");
                    txtTotalSelected.setText("0.00");
                    chk_delete.setEnabled(false);
                    selectedBill.removeAll(billStatements);
                }

                iconAdapter.notifyDataSetChanged();
                listAdapter.notifyDataSetChanged();
                animateToolPay();
                break;
            case R.id.chk_category:
                mItems = ModelBillCategory.getCategory();
                this.mListableItems = new String[mItems.size() + 1];

                int i = 1;
                mListableItems[0] = "ALL";
                for (ModelBillCategory item : mItems) {
                    mListableItems[i++] = item.getCategory_name();
                }

                new MaterialDialog.Builder(this)
                        .title(R.string.action_category)
                        .items(mListableItems)
                        .negativeText(R.string.action_cancel)
                        .itemsCallback(this)
                        .show();

                break;
            case R.id.chk_zoom:
                if (isChecked) {
                    InitializeList(getResources().getInteger(R.integer.grid_column_count_zoom));
                } else {
                    InitializeList(getResources().getInteger(R.integer.grid_column_count));
                }
                break;
            case R.id.chk_delete:
                Util.ShowDialog(this, "fonebayad", "Do you want to delete this bill?", "YES", "NO", ActivityMyBills.this);
                break;
            case R.id.chk_list_type:
                if (isChecked) {
                    chk_zoom.setEnabled(false);
                    txtView.setText("ICON VIEW");
                    listview_list.setAdapter(listAdapter);
                    listview_icon.setVisibility(View.GONE);
                    listview_list.setVisibility(View.VISIBLE);
                    listview_list.setOnItemClickListener(this);
                } else {
                    chk_zoom.setEnabled(true);
                    txtView.setText("LIST VIEW");
                    listview_list.setVisibility(View.GONE);
                    listview_icon.setVisibility(View.VISIBLE);
                    listview_icon.setAdapter(iconAdapter);
                    listview_icon.setOnItemClickListener(this);
                    listview_icon.setNumColumns(getResources().getInteger(R.integer.grid_column_count));
                }
                break;
        }
    }

    @Override
    public void onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
        billStatements.clear();
        switch (i) {
            case 0:
                billStatements = ModelBillInformation.getBillStatement();
                break;
            default:
                billStatements = ModelBillInformation.getBillStatementByCategory(mItems.get(i - 1).getCategory_id());
                break;
        }

        txtNoOfBills.setText(String.valueOf(billStatements.size()));
        InitiliazeAdapters();

        iconAdapter.notifyDataSetChanged();
        listAdapter.notifyDataSetChanged();

        ShowMessage(billStatements.size());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        try {
            if (selected == 0) totalSelected = 0.00;

            CheckBox selected_ = (CheckBox) view.findViewById(R.id.chk_select);

            if (selected_.isChecked()) {
                selected -= 1;
                selected_.setChecked(false);
                selected_.setVisibility(View.INVISIBLE);
                totalSelected -= Double.valueOf(billStatements.get(position).getBalance());
                listSelected.remove(billStatements.get(position).getBill_Id());
                selectedBill.remove(ModelBillInformation.viewBillStatement(billStatements.get(position).getBill_Id()));
            } else {
                selected += 1;
                selected_.setChecked(true);
                selected_.setVisibility(View.VISIBLE);
                totalSelected += Double.valueOf(billStatements.get(position).getBalance());
                listSelected.add(billStatements.get(position).getBill_Id());
                selectedBill.add(ModelBillInformation.viewBillStatement(billStatements.get(position).getBill_Id()));
            }

            if (selected == billStatements.size()) chk_select.setChecked(true);

            if (selected > 0) {
                chk_delete.setEnabled(true);
            } else {
                listSelected.clear();
                chk_select.setChecked(false);
                chk_delete.setEnabled(false);
                totalSelected = 0.00;
            }

            animateToolPay();
            txtNoOfSelected.setText(String.valueOf(selected));
            txtTotalSelected.setText("Php " + new DecimalFormat("#,##0.00").format(totalSelected < 0.00 ? (totalSelected * -1) : totalSelected));
            txtAccountBalance.setText("Php " + new DecimalFormat("#,##0.00").format(AccountBalance - totalSelected));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
        switch (dialogAction) {
            case POSITIVE:
                Toast.makeText(this, "Wait mamaya!!", Toast.LENGTH_SHORT).show();
                break;
            case NEGATIVE:
            case NEUTRAL:
                materialDialog.dismiss();
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        listSelected.clear();
        try {
            for (ModelBillInformation billstatement : estansaas.fonebayad.model.ModelBillInformation.getBillStatement()) {
                billstatement.setIsSelected(false);
                billstatement.save();
            }

            AuthPrimaryBankDetails();

            ll_tool.setVisibility(View.INVISIBLE);
            chk_delete.setEnabled(false);

            InitiliazeAdapters();
            ShowMessage(ModelBillInformation.getBillStatement().size());

            iconAdapter.notifyDataSetChanged();
            listAdapter.notifyDataSetChanged();

            txtNoOfSelected.setText("0");
            txtTotalSelected.setText("0.00");

            selected = 0;
            totalSelected = 0.00;
            selectedBill.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        estansaas.fonebayad.model.ModelBillInformation.DeleteBillStatement();
    }

    @Override
    public void onBackPressed() {
        finish();
        Util.startNextActivity(this, ActivityDashboard.class);
    }
}
