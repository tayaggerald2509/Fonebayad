package estansaas.fonebayad.activity;

import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import estansaas.fonebayad.R;

/**
 * Created by gerald.tayag on 10/26/2015.
 */
public class ActivityPaymentView extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_view);
        ButterKnife.bind(this);
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
