package com.example.easydo;

import android.content.SharedPreferences;
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
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewLanguageSetting = inflater.inflate(R.layout.language_setting, container, false);
        radioButtonEng = viewLanguageSetting.findViewById(R.id.radio_eng);
        radioButtonGer = viewLanguageSetting.findViewById(R.id.radio_ger);
        radioButtonHun = viewLanguageSetting.findViewById(R.id.radio_hun);

        prefs = ((MainActivity) getActivity()).getApplicationContext().getSharedPreferences("EasyDoLanguageSetting", 0);
        editor = prefs.edit();

        switch(prefs.getString("lang", "")){
            case "de":
                radioButtonGer.setChecked(true);
                break;
            case "hu":
                radioButtonHun.setChecked(true);
                break;
            default:
                radioButtonEng.setChecked(true);
        }

        LanguageSetting languageSetting = new LanguageSetting();
        radioButtonEng.setOnClickListener(set -> {
            setAppLanguage("en", languageSetting);
        });
        radioButtonGer.setOnClickListener(set -> {
            setAppLanguage("de", languageSetting);
        });
        radioButtonHun.setOnClickListener(set -> {
            setAppLanguage("hu", languageSetting);
        });
        return viewLanguageSetting;
    }

    private void setAppLanguage(String language, LanguageSetting langSetting) {
        ((MainActivity) getActivity()).setLocale(language);
        editor.putString("lang", language);
        editor.commit();

        getParentFragmentManager().beginTransaction().replace(R.id.host_fragment_content_main, langSetting).commit();
        ((MainActivity) getActivity()).recreate();
    }
}
