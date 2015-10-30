package estansaas.fonebayad.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import estansaas.fonebayad.R;
import estansaas.fonebayad.utils.Util;

/**
 * Created by gerald.tayag on 10/20/2015.
 */
public class ActivityProblemLogin extends Activity {

    @Bind(R.id.lblAppName)
    public TextView lblAppName;

    @Bind(R.id.txtForgetten)
    public TextView txtForgetten;

    @Bind(R.id.txtAuth)
    public TextView txtAuth;

    @Bind(R.id.txtSubTitle)
    public TextView txtSubtitle;

    @Bind(R.id.txtEmail)
    public EditText txtEmail;

    @Bind(R.id.btnSubmit)
    public Button btnSubmit;

    @Bind(R.id.chk_forget_pin)
    public RadioButton chk_forget_pin;

    @Bind(R.id.chk_auth)
    public RadioButton chk_auth;

    @Bind(R.id.ll_forget)
    public LinearLayout ll_forget;

    @Bind(R.id.ll_auth)
    public LinearLayout ll_auth;

    @Bind(R.id.back)
    public ImageView back;

    public Boolean backpressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_login);
        ButterKnife.bind(this);

        InitializeViews();
    }

    @OnClick(R.id.ll_forget)
    public void Forget() {
        chk_auth.setChecked(false);
        chk_forget_pin.setChecked(true);
    }


    @OnClick(R.id.ll_auth)
    public void AuthCode() {
        chk_auth.setChecked(true);
        chk_forget_pin.setChecked(false);
    }

    @OnClick(R.id.btnSubmit)
    public void Submit() {
        new MaterialDialog.Builder(this)
                .title("Forgotten PIN")
                .content("Please follow the link sent to tayaggerald2509@gmail.com to reset your PIN")
                .contentGravity(GravityEnum.CENTER)
                .titleGravity(GravityEnum.CENTER)
                .theme(Theme.LIGHT)
                .neutralText("LOGIN")
                .buttonsGravity(GravityEnum.CENTER)
                .neutralColorRes(R.color.app_color)
                .show();
    }

    private void InitializeViews() {
        lblAppName.setTypeface(Util.setTypeface(this, Util.MYRIAD_BOLD_ITALIC));
        txtForgetten.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtAuth.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtSubtitle.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        txtEmail.setTypeface(Util.setTypeface(this, Util.ROBOTO));
        btnSubmit.setTypeface(Util.setTypeface(this, Util.ROBOTO));
    }

    @OnClick(R.id.back)
    public void Back() {
        finish();
    }

    @Override
    public void onBackPressed() {

        if (backpressed) {
            super.onBackPressed();
            return;
        }

        backpressed = true;
        Toast.makeText(this, "Please once again to exit!", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                backpressed = false;
            }
        }, 2000);
    }
}
