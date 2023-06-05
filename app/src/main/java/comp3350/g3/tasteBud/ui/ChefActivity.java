package comp3350.g3.tasteBud.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import comp3350.g3.tasteBud.R;


public class ChefActivity extends Fragment {
    //The Layout connect with "Chef_Icon"

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.chef_activity, container, false);
    }
}