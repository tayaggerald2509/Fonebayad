package estansaas.fonebayad.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.venmo.android.pin.PinFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import estansaas.fonebayad.R;
import estansaas.fonebayad.utils.Util;

/**
 * Created by gerald.tayag on 11/4/2015.
 */
public class ActivityTapAndPay extends AppCompatActivity implements PinFragment.Listener {

    @Bind(R.id.lblFone)
    public TextView lblFone;

    @Bind(R.id.lblSubText)
    public TextView lblSubText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap_pay);
        ButterKnife.bind(this);

        lblFone.setTypeface(Util.setTypeface(this, Util.MYRIAD_BOLD_ITALIC));
        lblSubText.setTypeface(Util.setTypeface(this, Util.MYRIAD_BOLD_ITALIC));

        Fragment toShow = PinFragment.newInstanceForVerification();

        getFragmentManager().beginTransaction()
                .replace(R.id.root, toShow, toShow.getClass().getSimpleName())
                .commit();
    }

    @Override
    public void onValidated() {

    }

    @Override
    public void onPinCreated() {

    }
}
