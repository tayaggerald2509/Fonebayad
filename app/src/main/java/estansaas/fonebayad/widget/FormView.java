package estansaas.fonebayad.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.EditText;

import estansaas.fonebayad.activity.ActivityLogin;

/**
 * Created by gerald.tayag on 10/6/2015.
 */
public class FormView extends EditText {

    private ActivityLogin activityLogin = new ActivityLogin();
    public FormView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setError(CharSequence error, Drawable icon) {
        setCompoundDrawables(null, null, icon, null);
    }

}
