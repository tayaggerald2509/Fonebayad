package estansaas.fonebayad.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by gerald.tayag on 10/12/2015.
 */
public class ViewPagerView extends ViewPager {

    public ViewPagerView(Context context) {
        super(context);
    }

    public ViewPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
