package estansaas.fonebayad.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by gerald.tayag on 10/16/2015.
 */
public class ModelSyncData {

    @SerializedName("billers")
    private ArrayList<ModelBillers> modelBillerses;

    @SerializedName("billcategories")
    private ArrayList<ModelBillCategory> modelBillCategories;

    @SerializedName("currency")
    private ArrayList<ModelCurrency> modelCurrencies;

    @SerializedName("country")
    private ArrayList<ModelCountry> modelCountries;

    public ModelSyncData() {
    }

    public ModelSyncData(ArrayList<ModelBillers> modelBillerses, ArrayList<ModelBillCategory> modelBillCategories, ArrayList<ModelCurrency> modelCurrencies, ArrayList<ModelCountry> modelCountries, String status) {
        this.modelBillerses = modelBillerses;
        this.modelBillCategories = modelBillCategories;
        this.modelCurrencies = modelCurrencies;
        this.modelCountries = modelCountries;
    }

    public ArrayList<ModelBillers> getModelBillerses() {
        return modelBillerses;
    }

    public void setModelBillerses(ArrayList<ModelBillers> modelBillerses) {
        this.modelBillerses = modelBillerses;
    }

    public ArrayList<ModelBillCategory> getModelBillCategories() {
        return modelBillCategories;
    }

    public void setModelBillCategories(ArrayList<ModelBillCategory> modelBillCategories) {
        this.modelBillCategories = modelBillCategories;
    }

    public ArrayList<ModelCurrency> getModelCurrencies() {
        return modelCurrencies;
    }

    public void setModelCurrencies(ArrayList<ModelCurrency> modelCurrencies) {
        this.modelCurrencies = modelCurrencies;
    }

    public ArrayList<ModelCountry> getModelCountries() {
        return modelCountries;
    }

    public void setModelCountries(ArrayList<ModelCountry> modelCountries) {
        this.modelCountries = modelCountries;
    }

}
