package estansaas.fonebayad.model;

import estansaas.fonebayad.utils.Listable;

/**
 * Created by gerald.tayag on 10/20/2015.
 */
public class ModelStatus implements Listable {

    private String status;

    public ModelStatus() {
    }

    public ModelStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getLabel() {
        return getStatus();
    }
}
