package estansaas.fonebayad.activity;

import android.app.ListFragment;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import estansaas.fonebayad.R;
import estansaas.fonebayad.adapter.AdapterMenu;
import estansaas.fonebayad.model.ModelMenu;
import estansaas.fonebayad.utils.Util;

/**
 * Created by gerald.tayag on 10/5/2015.
 */
public class BaseActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayList<ModelMenu> modelMenuArrayList;
    protected ListFragment mFrag;
    private SlidingMenu slideMenu;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private LayoutInflater layoutInflater;
    private AdapterMenu adapter;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        slideMenu = new SlidingMenu(this);
        slideMenu.setMode(SlidingMenu.RIGHT);
        slideMenu.setTouchModeAbove(SlidingMenu.SLIDING_WINDOW);
        slideMenu.setShadowWidthRes(R.dimen.shadow_width);
        slideMenu.setShadowDrawable(R.drawable.shadow);
        slideMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        slideMenu.setFadeDegree(0.35f);
        slideMenu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        setMenu(slideMenu);
    }

    private void setMenu(SlidingMenu menu) {

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.fragment_menu, null);
        listView = (ListView) view.findViewById(R.id.list_item);

        addMenuLayout();

        adapter = new AdapterMenu(this, modelMenuArrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        menu.setMenu(view);
    }

    public void showMenu() {
        slideMenu.toggle();
    }

    private void addMenuLayout() {
        modelMenuArrayList = new ArrayList<ModelMenu>();
        for (int i = 0; i < navMenuTitles.length; i++) {
            modelMenuArrayList.add(new ModelMenu(navMenuTitles[i], navMenuIcons.getResourceId(i, -1)));
        }
        navMenuIcons.recycle();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Class<?> mclass = null;
        switch (position) {
            case 0:
                mclass = ActivityTransactionHistory.class;
                break;
            case 1:
                mclass = ActivityReloadEWallet.class;
                break;
            case 2:
                mclass = ActivityPaymentMethods.class;
                break;
            case 3:
                mclass = ActivityTransactionHistory.class;
                break;
            case 4:
                mclass = ActivityPaymentMethods.class;
                break;
            case 5:
                mclass = ActivityTransactionHistory.class;
                break;
            case 6:
                mclass = ActivityTransactionHistory.class;
                break;
            case 7:
                mclass = ActivityTransactionHistory.class;
                break;
            case 8:
                mclass = ActivityTransactionHistory.class;
                break;
            case 9:
                mclass = ActivityTransactionHistory.class;
                break;
            case 10:
                mclass = ActivityTransactionHistory.class;
                break;
            default:
                break;
        }

        finish();
        Util.startNextActivity(this, mclass);
    }
}
