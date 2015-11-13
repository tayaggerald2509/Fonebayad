package estansaas.fonebayad.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import estansaas.fonebayad.R;
import estansaas.fonebayad.model.ModelContact;

/**
 * Created by gerald.tayag on 11/12/2015.
 */
public class AdapterContactList extends BaseAdapter implements SectionIndexer {

    private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private ModelContact modelContact;
    private List<ModelContact> modelContacts;
    private Activity activity;

    public AdapterContactList(Activity activity, List<ModelContact> modelContacts) {
        this.activity = activity;
        this.modelContacts = modelContacts;
    }

    @Override
    public int getCount() {
        return modelContacts.size();
    }

    @Override
    public ModelContact getItem(int position) {
        return modelContacts.get(position);
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
            convertView = inflater.inflate(R.layout.view_list_contact, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        modelContact = getItem(position);

        holder.txtContactName.setText(modelContact.getContact_name());

        return convertView;
    }

    @Override
    public Object[] getSections() {
        String[] sections = new String[mSections.length()];
        for (int i = 0; i < mSections.length(); i++)
            sections[i] = String.valueOf(mSections.charAt(i));
        return sections;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = sectionIndex; i >= 0; i--) {
            for (int j = 0; j < getCount(); j++) {
                if (i == 0) {
                    // For numeric section
                    for (int k = 0; k <= 9; k++) {
                        if (String.valueOf(getItem(j).getContact_name().charAt(0)).equals(String.valueOf(k)))
                            return j;
                    }
                } else {
                    if (String.valueOf(getItem(j).getContact_name().charAt(0)).equals(String.valueOf(mSections.charAt(i))))
                        return j;
                }
            }
        }
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    static class ViewHolder {

        @Bind(R.id.txtContactName)
        TextView txtContactName;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
