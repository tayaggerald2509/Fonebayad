package estansaas.fonebayad.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import estansaas.fonebayad.R;
import estansaas.fonebayad.utils.Listable;

/**
 * Created by gerald.tayag on 10/20/2015.
 */
public class FormSelector<T extends Listable> extends EditText {

    List<T> mItems;
    String[] mListableItems;
    CharSequence mHint;

    OnItemSelectedListener<T> onItemSelectedListener;

    public FormSelector(Context context) {
        super(context);

        mHint = getHint();
    }

    public FormSelector(Context context, AttributeSet attrs) {
        super(context, attrs);

        mHint = getHint();
    }

    public FormSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mHint = getHint();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FormSelector(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        mHint = getHint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setFocusable(false);
        setClickable(true);
    }

    public void setItems(List<T> items) {
        this.mItems = items;
        this.mListableItems = new String[items.size()];

        int i = 0;

        for (T item : mItems) {
            mListableItems[i++] = item.getLabel();
        }

        configureOnClickListener();
    }

    public void setItems(String[] mListableItems) {
        this.mListableItems = new String[mListableItems.length];
        int i = 0;

        for (T item : mItems) {
            this.mListableItems[i++] = item.getLabel();
        }

        configureOnClickListener();
    }

    private void configureOnClickListener() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(view.getContext())
                        .title(mHint)
                        .items(mListableItems)
                        .itemsCallbackSingleChoice(0, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {
                                setText(mListableItems[i]);

                                if (onItemSelectedListener != null) {
                                    onItemSelectedListener.onItemSelectedListener(mItems.get(i), i);
                                }
                                return true;
                            }
                        })
                        .positiveText(R.string.ok)
                        .show();
            }
        });
    }

    public void setOnItemSelectedListener(OnItemSelectedListener<T> onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public interface OnItemSelectedListener<T> {
        void onItemSelectedListener(T item, int selectedIndex);
    }

    @Override
    public void setError(CharSequence error, Drawable icon) {
        setCompoundDrawables(null, null, icon, null);
    }


}
