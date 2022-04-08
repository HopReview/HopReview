package com.example.teame_hopreview.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.example.teame_hopreview.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private MainActivity myAct;
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_home, container, false);
        context = getActivity().getApplicationContext();
        myAct = (MainActivity) getActivity();
        myAct.getSupportActionBar().setTitle(" ");
        return myView;
    }

}