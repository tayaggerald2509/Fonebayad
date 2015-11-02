package estansaas.fonebayad.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.telerik.widget.calendar.CalendarElement;
import com.telerik.widget.calendar.CalendarSelectionMode;
import com.telerik.widget.calendar.RadCalendarView;
import com.telerik.widget.calendar.WeekNumbersDisplayMode;
import com.telerik.widget.calendar.events.Event;

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
import estansaas.fonebayad.auth.Responses.ResponseBillStatement;
import estansaas.fonebayad.auth.RestClient;
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
 * Created by gerald.tayag on 10/27/2015.
 */
public class ActivityCalendar extends BaseActivity implements MaterialDialog.SingleButtonCallback, RadCalendarView.OnSelectedDatesChangedListener {

    private Calendar calendar = Calendar.getInstance();

    @Bind(R.id.coordinatorLayout)
    public CoordinatorLayout coordinatorLayout;

    @Bind(R.id.calendarView)
    public RadCalendarView calendarView;

    @Bind(R.id.lblTotalUnpaidBills)
    public TextView lblTotalUnpaidBills;

    @Bind(R.id.txtTotalUnpaidBills)
    public AutofitTextView txtTotalUnpaidBills;

    @Bind(R.id.lblNoOfBillsOverdue)
    public TextView lblNoOfBillsOverdue;

    @Bind(R.id.txtNoOfBillsOverdue)
    public TextView txtNoOfBillsOverdue;

    @Bind(R.id.lblNoBillsDue)
    public TextView lblNoBillsDue;

    @Bind(R.id.txtNoOfBillsDue)
    public TextView txtNoOfBillsDue;

    private Event overdue;
    private Event week;
    private Event due;

    private List<Event> data;

    private List<Long> dates = new ArrayList<Long>();

    private long eventStart, eventEnd;
    private Double totalBills = 0.0;
    private int Due, OverDue = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_calendar);
        ButterKnife.bind(this);

        calendarView.setWeekNumbersDisplayMode(WeekNumbersDisplayMode.Inline);
        calendarView.setSelectionMode(CalendarSelectionMode.Single);
        calendarView.setWeekNumbersDisplayMode(WeekNumbersDisplayMode.None);
        calendarView.getAdapter().setDateTextPosition(CalendarElement.CENTER);
        calendarView.getAdapter().setDateTextSize(getResources().getDimension(R.dimen.calender_text_size));
        calendarView.setHorizontalScroll(true);

        InitializeView();
        SyncCalendarData();
        OnCalendarMonthChange();

        calendarView.setOnSelectedDatesChangedListener(this);
    }

    private void InitializeView() {
        lblTotalUnpaidBills.setTypeface(Util.setTypeface(this, Util.ROBOTO), Typeface.BOLD);
        lblNoOfBillsOverdue.setTypeface(Util.setTypeface(this, Util.ROBOTO), Typeface.BOLD);
        lblNoBillsDue.setTypeface(Util.setTypeface(this, Util.ROBOTO), Typeface.BOLD);

        txtTotalUnpaidBills.setTypeface(Util.setTypeface(this, Util.ROBOTO_LIGHT));
        txtNoOfBillsOverdue.setTypeface(Util.setTypeface(this, Util.ROBOTO_LIGHT));
        txtNoOfBillsDue.setTypeface(Util.setTypeface(this, Util.ROBOTO_LIGHT));
    }

    @Override
    public void onResume() {
        super.onResume();
        SyncCalendarData();
    }

    @Override
    public void onSelectedDatesChanged(RadCalendarView.SelectionContext selectionContext) {
        Intent intent = new Intent(this, ActivityMyBills.class);
        intent.putExtra("EXTRA_SESSION_ID", 1);
        startActivity(intent);
        this.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    private void OnCalendarMonthChange() {
        calendarView.setOnDisplayDateChangedListener(new RadCalendarView.OnDisplayDateChangedListener() {
            Calendar calendar = Calendar.getInstance();

            @Override
            public void onDisplayDateChanged(long oldDate, long newDate) {

                totalBills = 0.0;
                OverDue = 0;
                Due = 0;

                calendar.setTimeInMillis(newDate);

                for (ModelBillInformation bill : ModelBillInformation.getBillStatementByMonth(String.valueOf(calendar.get(Calendar.MONTH) + 1), String.valueOf(calendar.get(Calendar.YEAR)))) {
                    calendar.setTime(bill.getDue_date());
                    if (new Date().after(calendar.getTime())) {
                        OverDue += 1;
                    }
                    Due += 1;
                    if (bill.getBill_status().contains("Unpaid"))
                        totalBills += Double.valueOf(bill.getBalance());

                }
                txtTotalUnpaidBills.setText(new DecimalFormat("#,##0.00").format(totalBills));
                txtNoOfBillsOverdue.setText(String.valueOf(OverDue));
                txtNoOfBillsDue.setText(String.valueOf(Due));
            }
        });
    }

    private void SyncCalendarData() {
        if (Network.isConnected(this)) {
            new MaterialDialog.Builder(this)
                    .content(R.string.action_wait)
                    .contentGravity(GravityEnum.CENTER)
                    .theme(Theme.DARK)
                    .widgetColor(Color.WHITE)
                    .progressIndeterminateStyle(false)
                    .progress(true, 0)
                    .showListener(new DialogInterface.OnShowListener() {
                        @Override
                        public void onShow(DialogInterface dialogInterface) {
                            AuthCalendarData(dialogInterface);
                        }
                    }).show();

        } else {
            Util.ShowMessage(coordinatorLayout, "No Internet Connection");
        }
    }

    private void AuthCalendarData(final DialogInterface dialogInterface) {

        Call<ResponseBillStatement> billStatementResponseCall = RestClient.get().SyncDashboardRecord(ModelLogin.getUserInfo().getApp_id(), Util.getGUID(this));

        billStatementResponseCall.enqueue(new Callback<ResponseBillStatement>() {
            @Override
            public void onResponse(Response<ResponseBillStatement> response, Retrofit retrofit) {

                if (response.isSuccess()) {
                    if (response.code() == 200) {
                        totalBills = 0.0;
                        Due = 0;
                        OverDue = 0;

                        data = new ArrayList<Event>();
                        estansaas.fonebayad.model.ModelBillInformation.DeleteBillStatement();
                        ActiveAndroid.beginTransaction();
                        try {
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

                                    if (new Date().before(Util.convertDate(modelBillStatement.getBill_duedate()))) {
                                        calendar.setTime(Util.convertDate(modelBillStatement.getBill_duedate()));
                                        eventStart = calendar.getTimeInMillis();
                                        calendar.add(Calendar.HOUR, 12);
                                        eventEnd = calendar.getTimeInMillis();
                                        if (Util.getWeekDate().before(calendar.getTime())) {
                                            due = new Event("", eventStart, eventEnd);
                                            due.setEventColor(getResources().getColor(R.color.app_subtext_color));
                                            data.add(due);
                                        } else {
                                            week = new Event("", eventStart, eventEnd);
                                            week.setEventColor(getResources().getColor(R.color.color_week));
                                            data.add(week);
                                        }
                                    } else {
                                        calendar.setTime(Util.convertDate(modelBillStatement.getBill_duedate()));
                                        eventStart = calendar.getTimeInMillis();
                                        calendar.add(Calendar.HOUR, 12);
                                        eventEnd = calendar.getTimeInMillis();

                                        overdue = new Event("", eventStart, eventEnd);
                                        overdue.setEventColor(getResources().getColor(R.color.color_overdue));

                                        data.add(overdue);
                                    }

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            ActiveAndroid.setTransactionSuccessful();
                        } finally {
                            ActiveAndroid.endTransaction();
                        }

                        calendarView.getEventAdapter().setEvents(data);
                        calendarView.getAdapter().setInlineEventsBackgroundColor(Color.BLACK);
                        calendarView.getAdapter().setInlineEventTimeStartTextColor(Color.WHITE);
                        calendarView.getAdapter().setInlineEventTimeEndTextColor(Color.WHITE);
                        calendarView.getAdapter().setInlineEventTitleTextSize(42.0F);

                        calendar = Calendar.getInstance();
                        int month = calendar.get(Calendar.MONTH) + 1;

                        for (ModelBillInformation bill : estansaas.fonebayad.model.ModelBillInformation.getBillStatementByMonth(String.valueOf(month).length() == 1 ? "0" + month : String.valueOf(month), String.valueOf(calendar.get(Calendar.YEAR)))) {
                            Log.i("Balance", bill.getBalance());
                            calendar.setTime(bill.getDue_date());
                            if (new Date().after(calendar.getTime())) {
                                OverDue += 1;
                            }
                            Due += 1;
                            if (bill.getBill_status().contains("Unpaid"))
                                totalBills += Double.valueOf(bill.getBalance());
                        }

                        txtTotalUnpaidBills.setText(new DecimalFormat("#,###.00").format(totalBills));
                        txtNoOfBillsOverdue.setText(String.valueOf(OverDue));
                        txtNoOfBillsDue.setText(String.valueOf(Due));
                        dialogInterface.dismiss();
                        return;
                    }
                }
                Util.ShowDialog(ActivityCalendar.this, "Dashboard", "Failed to connect to server", "RETRY", "CANCEL", ActivityCalendar.this);
                dialogInterface.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                dialogInterface.dismiss();
                Util.ShowDialog(ActivityCalendar.this, "Dashboard", "Unable to connect to server", "RETRY", "CANCEL", ActivityCalendar.this);
            }
        });
    }

    @Override
    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
        switch (dialogAction) {
            case POSITIVE:
                SyncCalendarData();
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

    @OnClick(R.id.ll_dashboard)
    public void GoToDashboard() {
        finish();
        Util.startNextActivity(this, ActivityDashboard.class);
    }

    @OnClick(R.id.ll_bills)
    public void GoToMyBills() {
        finish();
        Util.startNextActivity(this, ActivityMyBills.class);
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

    @OnClick(R.id.expanded_menu)
    public void toggleMenu() {
        showMenu();
    }


    @OnClick(R.id.back)
    public void Back() {
        finish();
    }


}
