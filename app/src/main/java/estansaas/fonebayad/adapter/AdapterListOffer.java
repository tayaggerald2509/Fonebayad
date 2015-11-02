package estansaas.fonebayad.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import estansaas.fonebayad.R;
import estansaas.fonebayad.model.ModelOffer;

/**
 * Created by gerald.tayag on 10/30/2015.
 */
public class AdapterListOffer extends BaseAdapter {

    private ModelOffer modelOffer;
    private List<ModelOffer> modelOfferList;
    private Activity activity;

    public AdapterListOffer(Activity activity, List<ModelOffer> modelOffersList) {
        this.activity = activity;
        this.modelOfferList = modelOffersList;
    }

    @Override
    public int getCount() {
        return modelOfferList.size();
    }

    @Override
    public ModelOffer getItem(int position) {
        return modelOfferList.get(position);
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
            convertView = inflater.inflate(R.layout.view_offer, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        modelOffer = getItem(position);

        if (position % 2 == 1) {
            holder.ll_row.setBackgroundColor(activity.getResources().getColor(R.color.f9f9f9));
        } else {
            holder.ll_row.setBackgroundColor(Color.WHITE);
        }

        holder.txtBillername.setText(modelOffer.getBiller_name());
        holder.txtInstruction.setText(modelOffer.getInstruction());
        holder.txtPoints.setText(modelOffer.getOffer_numpoints());

        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.ll_row)
        LinearLayout ll_row;

        @Bind(R.id.txtBillerName)
        TextView txtBillername;

        @Bind(R.id.txtInstruction)
        TextView txtInstruction;

        @Bind(R.id.txtPoints)
        TextView txtPoints;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
