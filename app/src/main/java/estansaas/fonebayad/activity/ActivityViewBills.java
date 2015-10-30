package estansaas.fonebayad.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.text.DecimalFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import estansaas.fonebayad.R;
import estansaas.fonebayad.model.ModelBillCategory;
import estansaas.fonebayad.model.ModelBillInformation;
import estansaas.fonebayad.model.ModelBillers;
import estansaas.fonebayad.utils.Util;

/**
 * Created by gerald.tayag on 10/22/2015.
 */
public class ActivityViewBills extends BaseActivity {


    @Bind(R.id.rl_bill_added)
    public RelativeLayout rl_bill_added;

    @Bind(R.id.txtService)
    public TextView txtService;

    @Bind(R.id.txtCategory)
    public TextView txtCategory;

    @Bind(R.id.lblAccountNo)
    public TextView lblAccountNo;

    @Bind(R.id.txtAccountNo)
    public TextView txtAccountNo;

    @Bind(R.id.lblTransNo)
    public TextView lblTransNo;

    @Bind(R.id.txtTransNo)
    public TextView txtTransNo;

    @Bind(R.id.lblPayDate)
    public TextView lblPayDate;

    @Bind(R.id.txtPayDate)
    public TextView txtPayDate;

    @Bind(R.id.lblDueDate)
    public TextView lblDueDate;

    @Bind(R.id.txtDueDate)
    public TextView txtDuedate;

    @Bind(R.id.lblStatus)
    public TextView lblStatus;

    @Bind(R.id.txtStatus)
    public TextView txtStatus;

    @Bind(R.id.lblPaymentStatus)
    public TextView lblPaymentStatus;

    @Bind(R.id.txtPaymentStatus)
    public TextView txtPaymentStatus;

    @Bind(R.id.lblAmountDue)
    public TextView lblAmountDue;

    @Bind(R.id.txtAmountDue)
    public TextView txtAmountDue;

    @Bind(R.id.txtActualBill)
    public TextView txtActualBill;

    @Bind(R.id.txtEditBill)
    public TextView txtEditBill;

    @Bind(R.id.txtDashboard)
    public TextView txtDashboard;

    @Bind(R.id.txtBill)
    public TextView txtBill;

    @Bind(R.id.ll_dashboard)
    public LinearLayout ll_dashboard;

    @Bind(R.id.ll_pay_now)
    public LinearLayout ll_pay_now;

    @Bind(R.id.ll_bill)
    public LinearLayout ll_bill;

    @Bind(R.id.ll_bottom)
    public LinearLayout ll_bottom;

    @Bind(R.id.ll_bill_added)
    public RelativeLayout ll_bill_added;

    private ModelBillInformation modelBillInformation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bills);
        ButterKnife.bind(this);

        YoYo.with(Techniques.Landing).duration(1200).playOn(findViewById(R.id.img_stamp));

        rl_bill_added.setVisibility(getIntent().getExtras().getBoolean("EXTRA_BILL_ADDED", false) ? View.GONE : View.VISIBLE);

        modelBillInformation = (ModelBillInformation) getIntent().getSerializableExtra("ModelBillInformation");

        InitializeView();
        InitializeData();
    }

    @OnClick(R.id.ll_dashboard)
    public void GotoDashboard() {
        finish();
        Util.startNextActivity(this, ActivityDashboard.class);
    }

    @OnClick(R.id.ll_bill)
    public void GotoAddBill() {
        finish();
        Util.startNextActivity(this, ActivityAddBill.class);
    }

    @OnClick(R.id.ll_pay_now)
    public void GoToPayView() {
        finish();
        Util.startNextActivity(this, ActivityPaymentView.class);
    }

    private void InitializeView() {
        txtService.setTypeface(Util.setTypeface(this, Util.ROBOTO), Typeface.BOLD);
        txtCategory.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        lblAccountNo.setTypeface(Util.setTypeface(this, Util.ROBOTO), Typeface.BOLD);
        txtAccountNo.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        lblTransNo.setTypeface(Util.setTypeface(this, Util.ROBOTO), Typeface.BOLD);
        txtTransNo.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        lblDueDate.setTypeface(Util.setTypeface(this, Util.ROBOTO), Typeface.BOLD);
        txtDuedate.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        lblPayDate.setTypeface(Util.setTypeface(this, Util.ROBOTO), Typeface.BOLD);
        txtPayDate.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        lblStatus.setTypeface(Util.setTypeface(this, Util.ROBOTO), Typeface.BOLD);
        txtStatus.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        lblPaymentStatus.setTypeface(Util.setTypeface(this, Util.ROBOTO), Typeface.BOLD);
        txtPaymentStatus.setTypeface(Util.setTypeface(this, Util.ROBOTO));

        lblAmountDue.setTypeface(Util.setTypeface(this, Util.ROBOTO), Typeface.BOLD);
        txtAmountDue.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtActualBill.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtEditBill.setTypeface(Util.setTypeface(this, Util.ROBOTO));

        txtDashboard.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtBill.setTypeface(Util.setTypeface(this, Util.ROBOTO));
    }

    private void InitializeData() {

        txtService.setText(ModelBillers.getBillers(modelBillInformation.getBill_biller()).getBiller_name());
        txtCategory.setText(ModelBillCategory.getCategoryName(Integer.valueOf(ModelBillers.getBillers(modelBillInformation.getBill_biller()).getBiller_category())));
        txtAccountNo.setText(modelBillInformation.getBill_account_number());
        txtTransNo.setText("-");
        txtDuedate.setText(modelBillInformation.getBill_due_date());
        txtStatus.setText(modelBillInformation.getBill_status());
        txtPaymentStatus.setText("-");
        txtPayDate.setText("-");
        txtAmountDue.setText(new DecimalFormat("#,##0.00").format(Double.valueOf(modelBillInformation.getBill_amount().replace("Php", "").trim())));
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
