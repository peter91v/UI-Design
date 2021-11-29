package com.example.easydo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
        LanguageSetting languageSetting = new LanguageSetting();
       radioButtonEng.setOnClickListener(set ->{
            ((MainActivity)getActivity()).setLocale((MainActivity)getContext(),"en");
            radioButtonEng.setChecked(true);
           getParentFragmentManager().beginTransaction().replace(R.id.host_fragment_content_main, languageSetting).commit();

       });
        radioButtonGer.setOnClickListener(set ->{
            ((MainActivity)getActivity()).setLocale((MainActivity)getContext(),"de");
            radioButtonGer.setChecked(true);
            getParentFragmentManager().beginTransaction().replace(R.id.host_fragment_content_main, languageSetting).commit();

        });
        radioButtonHun.setOnClickListener(set ->{
            ((MainActivity)getActivity()).setLocale((MainActivity)getContext(), "hu");
            radioButtonHun.setChecked(true);
            getParentFragmentManager().beginTransaction().replace(R.id.host_fragment_content_main, languageSetting).commit();

        });
        return viewLanguageSetting;
    }
}
