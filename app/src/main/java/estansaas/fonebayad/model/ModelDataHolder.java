package estansaas.fonebayad.model;

import com.activeandroid.query.Delete;

/**
 * Created by gerald.tayag on 10/16/2015.
 */
public class ModelDataHolder {

    public static void DeleteSyncData(){
        new Delete().from(ModelBillers.class).execute();
        new Delete().from(ModelBillCategory.class).execute();
        new Delete().from(ModelCurrency.class).execute();
        new Delete().from(ModelCountry.class).execute();
    }
}
