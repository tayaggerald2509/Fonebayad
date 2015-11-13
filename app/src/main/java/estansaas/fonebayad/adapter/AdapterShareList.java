package estansaas.fonebayad.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import estansaas.fonebayad.R;
import estansaas.fonebayad.model.ModelBillInformation;

/**
 * Created by gerald.tayag on 11/12/2015.
 */
public class AdapterShareList extends BaseAdapter {

    private ModelBillInformation billStatement;
    private List<ModelBillInformation> modelBillStatements;
    private Activity activity;

    public AdapterShareList(Activity activity, List<ModelBillInformation> modelBillStatements) {
        this.activity = activity;
        this.modelBillStatements = modelBillStatements;
    }

    @Override
    public int getCount() {
        return modelBillStatements.size();
    }

    @Override
    public ModelBillInformation getItem(int position) {
        return modelBillStatements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.view_share_list, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        billStatement = getItem(position);


        holder.txtBillerName.setText(billStatement.getBiller_name());

        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.txtBillerName)
        TextView txtBillerName;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
