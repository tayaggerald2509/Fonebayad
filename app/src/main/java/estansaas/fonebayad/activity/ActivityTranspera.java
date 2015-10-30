package estansaas.fonebayad.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import estansaas.fonebayad.R;
import estansaas.fonebayad.utils.Util;

/**
 * Created by gerald.tayag on 10/21/2015.
 */
public class ActivityTranspera extends AppCompatActivity {

    @Bind(R.id.txtFname)
    public EditText txtFname;

    @Bind(R.id.txtLname)
    public EditText txtLname;

    @Bind(R.id.txtmobileNo)
    public EditText txtmobileNo;

    @Bind(R.id.txtPostalCode)
    public EditText txtPostalCode;

    @Bind(R.id.txtBday)
    public EditText txtBday;

    @Bind(R.id.txtAmountTransfer)
    public EditText txtAmountTransfer;

    @Bind(R.id.txtTransfer)
    public TextView txtTransfer;

    @Bind(R.id.txtTransferOnDate)
    public TextView txtTransferOnDatea;

    @Bind(R.id.btnProceed)
    public Button btnProceed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transpera);
        ButterKnife.bind(this);
    }

    private void InitializeViews(){
        txtFname.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtLname.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtmobileNo.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtPostalCode.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtBday.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtAmountTransfer.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtTransfer.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtTransferOnDatea.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        btnProceed.setTypeface(Util.setTypeface(this, Util.ROBOTO));
    }
}
