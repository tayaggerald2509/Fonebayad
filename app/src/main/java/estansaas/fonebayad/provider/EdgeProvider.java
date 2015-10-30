package estansaas.fonebayad.provider;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.samsung.android.sdk.look.cocktailbar.SlookCocktailManager;
import com.samsung.android.sdk.look.cocktailbar.SlookCocktailProvider;

import estansaas.fonebayad.R;
import estansaas.fonebayad.activity.ActivitySplash;

/**
 * Created by gerald.tayag on 10/20/2015.
 */
public class EdgeProvider extends SlookCocktailProvider {

    @Override
    public void onUpdate(Context context, SlookCocktailManager cocktailManager, int[] cocktailIds) {

        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.view_edge);
        /*String str = context.getResources().getString(R.string.);
        rv.setTextViewText(R.id.text, str);

        for (int i = 0; i < cocktailIds.length; i++) {
            cocktailBarManager.updateCocktail(cocktailIds[i], rv);
        }*/
        setPendingIntent(context, rv);
        for (int i = 0; i < cocktailIds.length; i++) {
            cocktailManager.updateCocktail(cocktailIds[i], rv);
        }
    }

    private void setPendingIntent(Context context, RemoteViews rv) {
        setPendingIntent(context, R.id.btn_fonebayad, new Intent(context, ActivitySplash.class), rv);
        setPendingIntent(context, R.id.btn_transpera, new Intent(context, ActivitySplash.class), rv);
        setPendingIntent(context, R.id.btn_addBill, new Intent(context, ActivitySplash.class), rv);
    }

    private void setPendingIntent(Context context, int rscId, Intent intent, RemoteViews rv) {
        intent.putExtra("EXTRA_EDGE_SESSION_ID", rscId);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setOnClickPendingIntent(rscId, pendingIntent);
    }
}
