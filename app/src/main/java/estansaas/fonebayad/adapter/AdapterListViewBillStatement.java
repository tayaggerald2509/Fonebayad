package estansaas.fonebayad.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import estansaas.fonebayad.R;
import estansaas.fonebayad.model.ModelBillCategory;
import estansaas.fonebayad.model.ModelBillInformation;
import estansaas.fonebayad.utils.Util;

/**
 * Created by gerald.tayag on 10/16/2015.
 */
public class AdapterListViewBillStatement extends BaseAdapter {

    private Calendar calendar;
    private LayoutInflater inflater;
    private List<ModelBillInformation> billStatements;
    private ModelBillInformation billStatement;
    private Activity activity;
    public static String message = "";

    public AdapterListViewBillStatement(Activity activity, List<ModelBillInformation> billStatements) {
        this.activity = activity;
        this.billStatements = billStatements;
    }

    @Override
    public int getCount() {
        return billStatements.size();
    }

    @Override
    public ModelBillInformation getItem(int position) {
        return billStatements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        convertView = null;

        billStatement = getItem(position);
        if (Util.checkIfNull(convertView)) {
            LayoutInflater inflater = activity.getLayoutInflater();
            if (billStatement.getType() == "OVERDUE") {
                convertView = inflater.inflate(R.layout.view_list_overdue, parent, false);
            } else {
                if (billStatement.getType() == "WEEK") {
                    convertView = inflater.inflate(R.layout.view_list_week, parent, false);
                } else {
                    convertView = inflater.inflate(R.layout.view_list, parent, false);
                }
            }
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            convertView.forceLayout();
        }

        Log.i("Category", String.valueOf(billStatement.getBiller_category()));

        holder.paramId = billStatement.getBill_Id();
        holder.paramSelect.setChecked(estansaas.fonebayad.model.ModelBillInformation.getSelected(billStatement.getBill_Id()));
        holder.paramCategory.setText(ModelBillCategory.getCategoryName(billStatement.getBiller_category()));
        holder.paramBillerName.setText(billStatement.getBiller_name());
        holder.paramNoOfDays.setText(billStatement.getNoOfDays());
        Log.i("Over Due", billStatement.getBalance());
        holder.paramOverDue.setText("PHP " + new DecimalFormat("#,##0.00").format(Double.valueOf(billStatement.getBalance())));

        holder.paramSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                billStatement = new Select().from(ModelBillInformation.class).where("bill_Id=?", holder.paramId).executeSingle();
                billStatement.setIsSelected(isChecked);
                billStatement.save();
            }
        });

        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.chk_select)
        CheckBox paramSelect;

        @Bind(R.id.txtBillerName)
        TextView paramBillerName;

        @Bind(R.id.txtCategory)
        TextView paramCategory;

        @Bind(R.id.txtOverdue)
        TextView paramOverDue;

        @Bind(R.id.txtNoOfDays)
        TextView paramNoOfDays;

        String paramId;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
