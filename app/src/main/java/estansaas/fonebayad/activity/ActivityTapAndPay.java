package estansaas.fonebayad.activity;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
import com.venmo.android.pin.PinFragment;
import com.venmo.android.pin.PinFragmentConfiguration;
import com.venmo.android.pin.Validator;
import com.venmo.android.pin.util.PinHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import estansaas.fonebayad.R;
import estansaas.fonebayad.service.NFCTapPayService;
import estansaas.fonebayad.utils.Util;

/**
 * Created by gerald.tayag on 11/4/2015.
 */
public class ActivityTapAndPay extends BaseActivity implements PinFragment.Listener {

    public static final String TAG = "SampleActivityBase";

    @Bind(R.id.lblFone)
    public TextView lblFone;

    @Bind(R.id.lblSubText)
    public TextView lblSubText;

    private MaterialDialog.Builder materialDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap_pay);
        ButterKnife.bind(this);

        registerReceiver(broadcastReceiver, new IntentFilter("TapPayReceiver"));

        lblFone.setTypeface(Util.setTypeface(this, Util.MYRIAD_BOLD_ITALIC));
        lblSubText.setTypeface(Util.setTypeface(this, Util.MYRIAD_BOLD_ITALIC));

        PinFragmentConfiguration config =
                new PinFragmentConfiguration(this)
                        .validator(new Validator() {
                            @Override
                            public boolean isValid(String input) {
                                Intent intent = new Intent(ActivityTapAndPay.this, NFCTapPayService.class);
                                if (PinHelper.doesMatchDefaultPin(ActivityTapAndPay.this, input)) {
                                    intent.putExtra("authenticated", true);
                                    startService(intent);

                                    CreateDialog("Confirmation", "Please place your phone again on NFC reader", "OK", new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                                            materialDialog.dismiss();
                                            finish();
                                        }
                                    });

                                    return true;
                                }
                                intent.putExtra("authenticated", false);
                                startService(intent);
                                return false;
                            }
                        });


        if (PinHelper.hasDefaultPinSaved(this)) {
            Fragment toShow = PinFragment.newInstanceForVerification(config);
            getFragmentManager().beginTransaction()
                    .replace(R.id.root, toShow, toShow.getClass().getSimpleName())
                    .commit();
        } else {
            Util.ShowNeutralDialog(this, "fonebayad", "You need to login first on fonebayad app.", "OK", new MaterialDialog.SingleButtonCallback() {
                @Override
                public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                    finish();
                    Util.startNextActivity(ActivityTapAndPay.this, ActivitySplash.class);
                }
            });
        }

    }

    private void CreateDialog(String title, String content, String neutral, MaterialDialog.SingleButtonCallback singleButtonCallback) {
        materialDialog = new MaterialDialog.Builder(this);
        materialDialog.title(title);
        materialDialog.content(content);
        materialDialog.contentGravity(GravityEnum.CENTER);
        materialDialog.titleGravity(GravityEnum.CENTER);
        materialDialog.titleColor(getResources().getColor(R.color.app_color));
        materialDialog.theme(Theme.LIGHT);
        materialDialog.neutralText(neutral);
        materialDialog.onNeutral(singleButtonCallback);
        materialDialog.buttonsGravity(GravityEnum.CENTER);
        materialDialog.cancelable(false);
        materialDialog.autoDismiss(true);
        materialDialog.neutralColor(getResources().getColor(R.color.app_color));
        materialDialog.show();
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onValidated() {

    }


    @Override
    public void onPinCreated() {

    }

}
