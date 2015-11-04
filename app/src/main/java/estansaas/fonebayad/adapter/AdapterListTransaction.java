package estansaas.fonebayad.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import estansaas.fonebayad.R;
import estansaas.fonebayad.model.ModelTransaction;

/**
 * Created by gerald.tayag on 11/4/2015.
 */
public class AdapterListTransaction extends BaseAdapter {

    private ModelTransaction modelTransaction;
    private List<ModelTransaction> modelTransactions;
    private Activity activity;

    public AdapterListTransaction(Activity activity, List<ModelTransaction> modelTransactions) {
        this.activity = activity;
        this.modelTransactions = modelTransactions;
    }

    @Override
    public int getCount() {
        return modelTransactions.size();
    }

    @Override
    public ModelTransaction getItem(int position) {
        return modelTransactions.get(position);
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
            convertView = inflater.inflate(R.layout.view_list_transaction_history, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        modelTransaction = getItem(position);

        holder.txtName.setText(modelTransaction.getRecipient_name());
        holder.txtStatus.setText(modelTransaction.getTransfer_status() == 0 ? "Processing" : "Accepted");

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            Date newFormat = sdf.parse(modelTransaction.getTransfer_datecreated());
            sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
            holder.txtDate.setText(sdf.format(newFormat).toUpperCase());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.txtName)
        TextView txtName;

        @Bind(R.id.txtStatus)
        TextView txtStatus;

        @Bind(R.id.txtDate)
        TextView txtDate;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
