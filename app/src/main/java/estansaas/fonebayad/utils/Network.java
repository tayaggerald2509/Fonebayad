package estansaas.fonebayad.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by gerald.tayag on 10/7/2015.
 */
public class Network {

    public static boolean isConnected(Context context){
        try{
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return  connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
