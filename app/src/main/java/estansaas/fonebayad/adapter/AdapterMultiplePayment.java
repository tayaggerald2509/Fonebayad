package estansaas.fonebayad.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import estansaas.fonebayad.R;
import estansaas.fonebayad.model.ModelBillCategory;
import estansaas.fonebayad.model.ModelBillInformation;
import estansaas.fonebayad.model.ModelBillers;
import estansaas.fonebayad.utils.Util;

/**
 * Created by gerald.tayag on 11/10/2015.
 */
public class AdapterMultiplePayment extends BaseAdapter {

    private List<ModelBillInformation> modelBillInformations;
    private ModelBillInformation modelBillInformation;
    private Activity activity;

    public AdapterMultiplePayment(Activity activity, List<ModelBillInformation> modelBillInformations) {
        this.activity = activity;
        this.modelBillInformations = modelBillInformations;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ModelBillInformation getItem(int position) {
        return modelBillInformations.get(position);
    }

    @Override
    public int getCount() {
        return modelBillInformations.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.view_multiple_payment, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        modelBillInformation = getItem(position);

        holder.txtBiller.setText(modelBillInformation.getBiller_name());
        holder.txtCategory.setText(ModelBillCategory.getCategoryName(Integer.valueOf(ModelBillers.getBillers(modelBillInformation.getBill_biller()).getBiller_category())));
        holder.txtAmnt.setText("Php " + new DecimalFormat("#,##0.00").format(Double.valueOf(modelBillInformation.getBill_amount())));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(modelBillInformation.getDue_date());
        if (new Date().before(calendar.getTime())) {
            if (Util.getWeekDate().before(calendar.getTime())) {
                holder.txtNoOfDays.setTextColor(activity.getResources().getColor(R.color.color_total));
            } else {
                holder.txtNoOfDays.setTextColor(activity.getResources().getColor(R.color.color_week));
            }
        } else {
            holder.txtNoOfDays.setTextColor(activity.getResources().getColor(R.color.color_overdue));
        }
        holder.txtNoOfDays.setText(modelBillInformation.getNoOfDays());

        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.txtBiller)
        TextView txtBiller;

        @Bind(R.id.txtCategory)
        TextView txtCategory;

        @Bind(R.id.txtAmnt)
        TextView txtAmnt;

        @Bind(R.id.txtNoOfDays)
        TextView txtNoOfDays;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
