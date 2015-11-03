package estansaas.fonebayad.activity;

import android.os.Bundle;
import android.widget.CompoundButton;

import com.kyleduo.switchbutton.Configuration;
import com.kyleduo.switchbutton.SwitchButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import estansaas.fonebayad.R;

/**
 * Created by gerald.tayag on 11/3/2015.
 */
public class ActivityPaymentMethods extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    @Bind(R.id.toggle)
    public SwitchButton toggle;

    private boolean newConf = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_payment_methods);
        ButterKnife.bind(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!newConf) {
            Configuration conf = Configuration.getDefault(getResources().getDisplayMetrics().density);
            conf.setThumbMargin(2);
            conf.setVelocity(8);
            conf.setThumbWidthAndHeight(24, 14);
            conf.setRadius(6);
            conf.setMeasureFactor(2f);
            toggle.setConfiguration(conf);
        } else {
            Configuration conf = Configuration.getDefault(getResources().getDisplayMetrics().density);
            toggle.setConfiguration(conf);
        }
        newConf = isChecked;
    }
}
