package estansaas.fonebayad.adapter;

import android.app.Activity;
import android.content.Context;
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
import estansaas.fonebayad.model.ModelBillInformation;

/**
 * Created by gerald.tayag on 10/15/2015.
 */
public class AdapterIconViewBillStatement extends BaseAdapter {

    private Calendar calendar;
    private LayoutInflater inflater;
    private List<ModelBillInformation> billStatements;
    private ModelBillInformation billStatement;
    private Activity activity;
    private String message = "";

    public AdapterIconViewBillStatement(Activity activity, List<ModelBillInformation> billStatements) {
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
        if (convertView == null) {
            inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (billStatement.getType().contains("OVERDUE")) {
                convertView = inflater.inflate(R.layout.view_list_icon_overdue, parent, false);
            } else {
                if (billStatement.getType().contains("WEEK")) {
                    convertView = inflater.inflate(R.layout.view_list_icon_week, parent, false);
                } else {
                    convertView = inflater.inflate(R.layout.view_list_icon, parent, false);
                }
            }
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Log.i("Over Due", billStatement.getBalance());
        holder.paramId = billStatement.getBill_Id();
        holder.paramBillerName.setText(billStatement.getBiller_name());
        holder.paramNoOfDays.setText(billStatement.getNoOfDays());
        holder.paramOverDue.setText("PHP " + new DecimalFormat("#,##0.00").format(Double.valueOf(billStatement.getBalance())));


        if (estansaas.fonebayad.model.ModelBillInformation.getSelected(billStatement.getBill_Id()) == true) {
            holder.paramSelect.setVisibility(View.VISIBLE);
        } else {
            holder.paramSelect.setVisibility(View.INVISIBLE);
        }
        holder.paramSelect.setChecked(estansaas.fonebayad.model.ModelBillInformation.getSelected(billStatement.getBill_Id()));

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
