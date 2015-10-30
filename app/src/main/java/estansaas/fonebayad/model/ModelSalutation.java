package estansaas.fonebayad.model;

import estansaas.fonebayad.utils.Listable;

/**
 * Created by gerald.tayag on 10/21/2015.
 */
public class ModelSalutation implements Listable {

    private String salutation;

    public ModelSalutation() {
    }

    public ModelSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    @Override
    public String getLabel() {
        return getSalutation();
    }
}
