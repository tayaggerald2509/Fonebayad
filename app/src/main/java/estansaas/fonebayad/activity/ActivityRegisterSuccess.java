package estansaas.fonebayad.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import estansaas.fonebayad.R;
import estansaas.fonebayad.utils.Util;

/**
 * Created by gerald.tayag on 10/9/2015.
 */
public class ActivityRegisterSuccess extends AppCompatActivity {

    @Bind(R.id.btnVerify)
    public Button btnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_success);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnVerify)
    public void Verified(){
        finish();
        Util.startNextActivity(this, ActivityLogin.class);
    }

}
