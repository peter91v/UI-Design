package com.example.easydo.ui.transform;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class TransformViewModel extends ViewModel {

    //private final MutableLiveData<List<String>> mTexts;
    public static MutableLiveData<List<String>> arrayList;

    public TransformViewModel() {
        arrayList = new MutableLiveData<>();
        List<String> texts = new ArrayList<>();
       /* for (int i = 1; i <= 5; i++) {
            texts.add("This is item # " + i);
        }*/
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