package estansaas.fonebayad.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import estansaas.fonebayad.R;
import estansaas.fonebayad.utils.Util;

/**
 * Created by gerald.tayag on 10/28/2015.
 */
public class ActivityReloadEWallet extends BaseActivity {

    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reload_ewallet);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.expanded_menu)
    public void ShowSideMenu() {
        showMenu();
    }

    @OnClick(R.id.back)
    public void OnBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Util.startNextActivity(this, ActivityDashboard.class);
    }
}
