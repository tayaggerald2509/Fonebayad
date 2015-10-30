package estansaas.fonebayad.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import estansaas.fonebayad.R;

/**
 * Created by gerald.tayag on 10/7/2015.
 */
public class FragmentOffer extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);

        return view;
    }
}
