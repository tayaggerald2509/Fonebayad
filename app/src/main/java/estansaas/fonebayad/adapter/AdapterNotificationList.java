package estansaas.fonebayad.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import estansaas.fonebayad.R;
import estansaas.fonebayad.model.ModelBillStatement;
import estansaas.fonebayad.model.ModelBillers;
import estansaas.fonebayad.utils.Util;

/**
 * Created by gerald.tayag on 10/29/2015.
 */
public class AdapterNotificationList extends BaseAdapter {
    private ModelBillStatement billStatement;
    private List<ModelBillStatement> modelBillStatements;
    private Activity activity;

    public AdapterNotificationList(Activity activity, List<ModelBillStatement> modelBillStatements) {
        this.activity = activity;
        this.modelBillStatements = modelBillStatements;
    }

    @Override
    public int getCount() {
        return modelBillStatements.size();
    }

    @Override
    public ModelBillStatement getItem(int position) {
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
            convertView = inflater.inflate(R.layout.view_notification, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        billStatement = getItem(position);

        if (position % 2 == 1) {
            holder.ll_row.setBackgroundColor(activity.getResources().getColor(R.color.f9f9f9));
        } else {
            holder.ll_row.setBackgroundColor(Color.WHITE);
        }

        holder.txtBillername.setText(ModelBillers.getBillers(billStatement.getBill_biller()).getBiller_name());
        try {
            if (new Date().before(Util.convertDate(billStatement.getBill_duedate()))) {
                holder.txtMsg.setText("Your bill is now overdue.");
            } else {
                holder.txtMsg.setText("will due on " + billStatement.getBill_duedate() + ".");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.txtCategory.setText("");

        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.ll_row)
        LinearLayout ll_row;

        @Bind(R.id.txtBillerName)
        TextView txtBillername;

        @Bind(R.id.txtMsg)
        TextView txtMsg;

        @Bind(R.id.txtSubMsg)
        TextView txtSubMsg;

        @Bind(R.id.txtCategory)
        TextView txtCategory;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
