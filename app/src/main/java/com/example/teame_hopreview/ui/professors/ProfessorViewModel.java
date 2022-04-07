package com.example.teame_hopreview.ui.professors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfessorViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ProfessorViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is professor fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}