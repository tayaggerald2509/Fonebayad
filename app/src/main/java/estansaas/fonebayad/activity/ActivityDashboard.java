package estansaas.fonebayad.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import estansaas.fonebayad.R;
import estansaas.fonebayad.adapter.AdapterViewPager;
import estansaas.fonebayad.fragments.FragmentDashboard;
import estansaas.fonebayad.fragments.FragmentMessages;
import estansaas.fonebayad.fragments.FragmentNotification;
import estansaas.fonebayad.fragments.FragmentOffer;
import estansaas.fonebayad.utils.Util;
import estansaas.fonebayad.view.BadgeView;

/**
 * Created by gerald.tayag on 10/7/2015.
 */
public class ActivityDashboard extends BaseActivity {

    @Bind(R.id.coordinatorLayout)
    public CoordinatorLayout coordinatorLayout;

    @Bind(R.id.toolbar)
    public Toolbar toolbar;

    @Bind(R.id.tabs)
    public TabLayout tabLayout;

    @Bind(R.id.viewpager)
    public ViewPager viewPager;

    @Bind(R.id.expanded_menu)
    public ImageView expanded_menu;

    @Bind(R.id.back)
    public ImageView back;

    private AdapterViewPager adapterViewPager;
    private Boolean backpressed = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setUpTabs();

    }

    private void setUpTabs(){
        for(int i = 0; i<Util.Tabs.length; i++) {
            TextView tabs = (TextView) LayoutInflater.from(this).inflate(R.layout.icon_tab, null);
            tabs.setText(Util.Tabs[i].toUpperCase());
            tabs.setCompoundDrawablesWithIntrinsicBounds(0, Util.Tabs_Icon[i], 0, 0);
            tabLayout.getTabAt(i).setCustomView(tabs);
        }
    }

    private void setUpBadges(){
        for(int i = 0; i< Util.Tabs.length; i++){
            TextView tabs = (TextView) LayoutInflater.from(this).inflate(R.layout.icon_tab, null);
            BadgeView badgeView = new BadgeView(this, tabs);
            badgeView.setText("1");
            badgeView.setTextColor(Color.WHITE);
            badgeView.setBackgroundColor(getResources().getColor(R.color.color_total));
            badgeView.toggle();
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        adapterViewPager = new AdapterViewPager(getSupportFragmentManager());
        adapterViewPager.addTab(new FragmentDashboard(), "DASHBOARD");
        adapterViewPager.addTab(new FragmentNotification(), "NOTIFICATION");
        adapterViewPager.addTab(new FragmentOffer(), "OFFERS");
        adapterViewPager.addTab(new FragmentMessages(), "MESSAGES");
        viewPager.setAdapter(adapterViewPager);
    }

    @OnClick(R.id.expanded_menu)
    public void toggleMenu(){
        showMenu();
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
