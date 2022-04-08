package com.example.teame_hopreview.ui.professors;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.teame_hopreview.MainActivity;
import com.example.teame_hopreview.R;
import com.example.teame_hopreview.databinding.FragmentProfessorsBinding;

public class ProfessorFragment extends Fragment {

    private MainActivity myAct;
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_professors, container, false);
        context = getActivity().getApplicationContext();
        myAct = (MainActivity) getActivity();
        myAct.getSupportActionBar().setTitle("Professors");

        return myView;
    }

}