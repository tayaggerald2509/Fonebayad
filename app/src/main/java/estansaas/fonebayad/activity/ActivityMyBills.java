package estansaas.fonebayad.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
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
import estansaas.fonebayad.auth.Responses.ResponseBillStatement;
import estansaas.fonebayad.auth.RestClient;
import estansaas.fonebayad.model.ModelBillCategory;
import estansaas.fonebayad.model.ModelBillStatement;
import estansaas.fonebayad.model.ModelLogin;
import estansaas.fonebayad.model.ModelBillInformation;
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

    LinearLayout ll_tool, ll_view, ll_share, ll_pay;

    public static final int SAMPLE_DATA_ITEM_COUNT = 3;

    private ModelBillInformation modelBillInfo;
    private List<ModelBillInformation> billStatements;
    private AdapterIconViewBillStatement iconAdapter;
    private AdapterListViewBillStatement listAdapter;
    private int selected = 0;
    private Double totalSelected = 0.0;
    private Animation bottom_up, bottom_down;
    private List<ModelBillCategory> mItems;
    private String[] mListableItems;
    private List<String> listSelected;

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

    }

    @OnClick(R.id.back)
    public void Back() {
        finish();
    }

    @OnClick(R.id.expanded_menu)
    public void toggleMenu() {
        showMenu();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        estansaas.fonebayad.model.ModelBillInformation.DeleteBillStatement();
    }

    @OnClick(R.id.ll_dashboard)
    public void GoToDashboard() {
        finish();
        Util.startNextActivity(this, ActivityDashboard.class);
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
            case R.id.ll_view:
                if (selected == 1) {

                    Toast.makeText(this, "CLICKED", Toast.LENGTH_SHORT).show();

                    modelBillInfo = estansaas.fonebayad.model.ModelBillInformation.viewBillStatement(listSelected.get(0));
                    SimpleDateFormat sdf = new SimpleDateFormat("ccc MMMM dd hh:mm:ss zzzz yyyy");

                    try {
                        Date date = sdf.parse(modelBillInfo.getDue_date().toString());
                        sdf = new SimpleDateFormat("MMM dd, yyyy");
                        modelBillInfo.setBill_due_date(sdf.format(date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(this, ActivityViewBills.class);
                    intent.putExtra("EXTRA_BILL_ADDED", true);
                    intent.putExtra("ModelBillInformation", modelBillInfo);
                    startActivity(intent);
                    this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                }
                break;
            case R.id.ll_share:
                Toast.makeText(this, "SHARE", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ll_pay:
                Toast.makeText(this, "PAY", Toast.LENGTH_SHORT).show();
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
        chk_category.setEnabled(isEnabled);
        chk_zoom.setEnabled(isEnabled);
        chk_list_type.setEnabled(isEnabled);
        if (selected > 0) {
            chk_delete.setEnabled(true);
        } else {
            chk_delete.setEnabled(false);
        }
    }

    private void ShowMessage() {
        if (ModelBillInformation.getBillStatement().size() > 0) {
            ll_message.setVisibility(View.INVISIBLE);
            InitializeTools(true);
        } else {
            ll_message.setVisibility(View.VISIBLE);
            InitializeTools(false);
        }
    }

    private void InitializeData() {
        billStatements = new ArrayList<ModelBillInformation>();
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
                            txtNoOfBills.setText(String.valueOf(estansaas.fonebayad.model.ModelBillInformation.CountBillStatement()));
                            InitiliazeAdapters();
                            ShowMessage();
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
            ShowMessage();
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
                                    billStatement.setNoOfDays(String.valueOf(i) + " DAYS OVERDUE");
                                }
                                billStatement.setType("OVERDUE");
                            } else {
                                if (String.valueOf(i).compareTo("0") == 0) {
                                    billStatement.setNoOfDays("OVERDUE");
                                    billStatement.setType("OVERDUE");
                                }else{
                                    if (Util.getWeekDate().after(calendar.getTime())) {
                                        billStatement.setType("WEEK");
                                    } else {
                                        billStatement.setType("DUE");
                                    }
                                    billStatement.setNoOfDays(String.valueOf(days) + " DAYS LEFT");
                                }
                            }
                            billStatements.add(billStatement);
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
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (selected == 0) totalSelected = 0.00;

        CheckBox selected_ = (CheckBox) view.findViewById(R.id.chk_select);

        if (selected_.isChecked()) {
            selected -= 1;
            selected_.setChecked(false);
            selected_.setVisibility(View.INVISIBLE);
            totalSelected -= Double.valueOf(billStatements.get(position).getBalance());
            listSelected.remove(billStatements.get(position).getBill_Id());
        } else {
            selected += 1;
            selected_.setChecked(true);
            selected_.setVisibility(View.VISIBLE);
            totalSelected += Double.valueOf(billStatements.get(position).getBalance());
            listSelected.add(billStatements.get(position).getBill_Id());
        }

        if (selected > 0) {
            chk_delete.setEnabled(true);
        } else {
            chk_select.setChecked(false);
            chk_delete.setEnabled(false);
            totalSelected = 0.00;
        }

        animateToolPay();
        txtNoOfSelected.setText(String.valueOf(selected));
        txtTotalSelected.setText("PHP " + new DecimalFormat("#,##0.00").format(totalSelected < 0.00 ? (totalSelected * -1) : totalSelected));
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

                if (isChecked) {
                    txtSelect.setText("UNSELECT ALL");
                } else {
                    txtSelect.setText("SELECT ALL");
                }
                for (ModelBillInformation billstatement : estansaas.fonebayad.model.ModelBillInformation.getBillStatement()) {
                    Log.i("TOTAL SELECTED", String.valueOf(totalSelected));
                    totalSelected += Double.valueOf(billstatement.getBalance());
                    billstatement.setIsSelected(isChecked);
                    billstatement.save();
                }

                if (isChecked) {
                    txtNoOfSelected.setText(String.valueOf(estansaas.fonebayad.model.ModelBillInformation.CountBillStatement()));
                    txtTotalSelected.setText("PHP " + new DecimalFormat("#,##0.00").format(totalSelected < 0.00 ? (totalSelected * -1) : totalSelected));
                    selected = Integer.valueOf(txtNoOfSelected.getText().toString());
                    ll_tool.setVisibility(View.VISIBLE);
                    chk_delete.setEnabled(true);
                } else {
                    selected = 0;
                    txtNoOfSelected.setText("0");
                    txtTotalSelected.setText("0.00");
                    chk_delete.setEnabled(false);
                }

                iconAdapter.notifyDataSetChanged();
                listAdapter.notifyDataSetChanged();

                animateToolPay();

                break;
            case R.id.chk_category:
                mItems = ModelBillCategory.getCategory();
                this.mListableItems = new String[mItems.size()];

                int i = 0;
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

        billStatements = estansaas.fonebayad.model.ModelBillInformation.getBillStatementByCategory(mListableItems[i]);
        txtNoOfBills.setText(String.valueOf(billStatements.size()));
        InitiliazeAdapters();
        iconAdapter.notifyDataSetChanged();
        listAdapter.notifyDataSetChanged();
        ShowMessage();

        Toast.makeText(this, mListableItems[i], Toast.LENGTH_SHORT).show();
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
        try {
            for (ModelBillInformation billstatement : estansaas.fonebayad.model.ModelBillInformation.getBillStatement()) {
                billstatement.setIsSelected(false);
                billstatement.save();
            }

            ll_tool.setVisibility(View.INVISIBLE);
            chk_delete.setEnabled(false);

            InitiliazeAdapters();
            ShowMessage();

            iconAdapter.notifyDataSetChanged();
            listAdapter.notifyDataSetChanged();

            txtNoOfSelected.setText("0");
            txtTotalSelected.setText("0.00");

            selected = 0;
            totalSelected = 0.00;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
