package com.example.easydo.ui.transform;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class TransformViewModel extends ViewModel {

    public static MutableLiveData<List<String>> arrayList;

    public TransformViewModel() {
        arrayList = new MutableLiveData<>();
        List<String> texts = new ArrayList<>();
        arrayList.setValue(texts);

    }
    public LiveData<List<String>> getarray(){
        MutableLiveData<List<String>> array = TransformViewModel.arrayList;
        return array;
    }
    public LiveData<List<String>> getTexts() {
        return arrayList;
    }
}