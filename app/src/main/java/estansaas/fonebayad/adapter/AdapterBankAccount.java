package estansaas.fonebayad.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import estansaas.fonebayad.R;
import estansaas.fonebayad.model.ModelBankAccount;
import estansaas.fonebayad.model.ModelCurrency;
import estansaas.fonebayad.utils.Util;

/**
 * Created by gerald.tayag on 10/21/2015.
 */
public class AdapterBankAccount extends BaseAdapter {

    private ModelBankAccount modelBankAccount;
    private ArrayList<ModelBankAccount> modelBankAccounts;
    private Activity activity;

    public AdapterBankAccount(Activity activity, ArrayList<ModelBankAccount> modelBankAccounts) {
        this.activity = activity;
        this.modelBankAccounts = modelBankAccounts;
    }

    @Override
    public int getCount() {
        return modelBankAccounts.size();
    }

    @Override
    public ModelBankAccount getItem(int position) {
        return modelBankAccounts.get(position);
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
            convertView = inflater.inflate(R.layout.view_payment_method, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        modelBankAccount = getItem(position);
        holder.txtPayment.setText(modelBankAccount.getBank_account_type());
        holder.txtAccountName.setTypeface(Util.setTypeface(activity, Util.ROBOTO));
        holder.txtAccountName.setText(modelBankAccount.getBankaccount_accountname());
        holder.txtCurrency.setTypeface(Util.setTypeface(activity, Util.ROBOTO));
        holder.txtCurrency.setText(ModelCurrency.getCurrency(modelBankAccount.getBankaccount_currency()));
        holder.txtAmount.setTypeface(Util.setTypeface(activity, Util.ROBOTO));
        holder.txtAmount.setText(new DecimalFormat("#,##0.00").format(Double.valueOf(modelBankAccount.getBankaccount_amount())));

        return convertView;
    }

    static class ViewHolder {

        //@Bind(R.id.img_logo)
        //ImageView imageView;

        @Bind(R.id.txtPayment)
        TextView txtPayment;

        @Bind(R.id.txtAccountName)
        TextView txtAccountName;

        @Bind(R.id.txtAmount)
        TextView txtAmount;

        @Bind(R.id.txtCurrency)
        TextView txtCurrency;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
