package estansaas.fonebayad.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import estansaas.fonebayad.R;
import estansaas.fonebayad.model.ModelMenu;

/**
 * Created by gerald.tayag on 10/9/2015.
 */
public class AdapterDrawerMenu extends BaseAdapter {

    private Context context;
    private ArrayList<ModelMenu> navDrawerItems;
    private Typeface tf;

    public AdapterDrawerMenu(Context context, ArrayList<ModelMenu> navDrawerItems) {
        this.context = context;
        this.navDrawerItems = navDrawerItems;
    }

    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.menu_item, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
        holder.txtTitle.setText(navDrawerItems.get(position).getTitle());

        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.icon)
        ImageView imgIcon;

        @Bind(R.id.title)
        TextView txtTitle;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
