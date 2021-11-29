package com.example.easydo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LanguageSetting extends Fragment {
    private RadioButton radioButtonEng;
    private RadioButton radioButtonGer;
    private RadioButton radioButtonHun;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewLanguageSetting = inflater.inflate(R.layout.language_setting, container, false);
        radioButtonEng = viewLanguageSetting.findViewById(R.id.redio_eng);
        radioButtonGer = viewLanguageSetting.findViewById(R.id.redio_ger);
        radioButtonHun = viewLanguageSetting.findViewById(R.id.redio_hun);

       /* radioButtonEng.setOnClickListener(set ->{
            ((MainActivity)getActivity()).setLocale("en");
            radioButtonEng.setChecked(true);
        });
        radioButtonGer.setOnClickListener(set ->{
            ((MainActivity)getActivity()).setLocale("de");
            radioButtonGer.setChecked(true);

        });
        radioButtonHun.setOnClickListener(set ->{
            ((MainActivity)getActivity()).setLocale("hu");
            radioButtonHun.setChecked(true);

        });*/
        return viewLanguageSetting;
    }
}
