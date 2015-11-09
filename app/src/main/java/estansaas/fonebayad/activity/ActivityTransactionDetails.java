package estansaas.fonebayad.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import estansaas.fonebayad.R;
import estansaas.fonebayad.model.ModelTransaction;
import estansaas.fonebayad.utils.Util;

/**
 * Created by gerald.tayag on 11/4/2015.
 */
public class ActivityTransactionDetails extends AppCompatActivity {

    @Bind(R.id.txtName)
    public TextView txtName;

    @Bind(R.id.txtBankName)
    public TextView txtBankName;

    @Bind(R.id.lblBankName)
    public TextView lblBankName;

    @Bind(R.id.txtActName)
    public TextView txtActName;

    @Bind(R.id.lblActName)
    public TextView lblActName;

    @Bind(R.id.txtAcntNo)
    public TextView txtAcntNo;

    @Bind(R.id.lblAcntNo)
    public TextView lblAcntNo;

    @Bind(R.id.txtDateCreated)
    public TextView txtDateCreated;

    @Bind(R.id.lblDateCreated)
    public TextView lblDateCreated;

    @Bind(R.id.lblAmntSend)
    public TextView lblAmntSend;

    @Bind(R.id.txtAmntSend)
    public TextView txtAmntSend;

    @Bind(R.id.txtWePayout)
    public TextView txtWePayout;

    @Bind(R.id.lblWePayout)
    public TextView lblWePayout;

    @Bind(R.id.txtExchangeRate)
    public TextView txtExchangeRate;

    @Bind(R.id.lblExchangeRate)
    public TextView lblExchangeRate;

    @Bind(R.id.txtAmntDeposit)
    public TextView txtAmntDeposit;

    @Bind(R.id.lblAmntDeposit)
    public TextView lblAmntDeposit;

    private ModelTransaction modelTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_details);
        ButterKnife.bind(this);

        modelTransaction = (ModelTransaction) getIntent().getSerializableExtra("TRANSACTION");
        InitializeViews();
        InitializeData();
    }

    private void InitializeViews() {
        lblBankName.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        lblActName.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        lblAcntNo.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        lblDateCreated.setTypeface(Util.setTypeface(this, Util.ROBOTO), Typeface.BOLD);
        lblAmntSend.setTypeface(Util.setTypeface(this, Util.ROBOTO), Typeface.BOLD);
        lblWePayout.setTypeface(Util.setTypeface(this, Util.ROBOTO), Typeface.BOLD);
        lblExchangeRate.setTypeface(Util.setTypeface(this, Util.ROBOTO), Typeface.BOLD);
        lblAmntDeposit.setTypeface(Util.setTypeface(this, Util.ROBOTO), Typeface.BOLD);

        txtName.setTypeface(Util.setTypeface(this, Util.ROBOTO),Typeface.BOLD);
        txtBankName.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtActName.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtAcntNo.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtDateCreated.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtAmntSend.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtWePayout.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtExchangeRate.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtAmntDeposit.setTypeface(Util.setTypeface(this, Util.ROBOTO));

    }

    private void InitializeData() {
        txtName.setText(modelTransaction.getRecipient_bankaccount_name());

        txtBankName.setText(modelTransaction.getRecipient_bank());
        txtActName.setText(modelTransaction.getRecipient_bankaccount_name());
        txtAcntNo.setText(modelTransaction.getRecipient_bankaccount_number());
        txtAmntSend.setText(modelTransaction.getTransfer_base_currency() + " " + modelTransaction.getTransfer_base_amount());
        txtWePayout.setText("PHP " + modelTransaction.getTransfer_amount());
        txtExchangeRate.setText(modelTransaction.getTransfer_base_amount());
        txtAmntDeposit.setText(modelTransaction.getTransfer_base_currency() + " " + modelTransaction.getTransfer_base_amount());

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            Date newFormat = sdf.parse(modelTransaction.getTransfer_datecreated());
            sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
            txtDateCreated.setText(sdf.format(newFormat).toUpperCase());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.back)
    public void OnBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        finish();
        Util.startNextActivity(this, ActivityTransactionHistory.class);
    }
}
