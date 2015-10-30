package estansaas.fonebayad;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by gerald.tayag on 10/5/2015.
 */
public class Fonebayad extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ActiveAndroid.initialize(this);
    }
}
