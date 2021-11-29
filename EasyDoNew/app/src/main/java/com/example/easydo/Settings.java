package com.example.easydo;

import android.app.ActionBar;
import android.app.UiModeManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class Settings extends Fragment {
    private TextView textViewLanguage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View settingsView = inflater.inflate(R.layout.settings, container, false);
        View backView = inflater.inflate(R.layout.app_bar_main, container, false);
        textViewLanguage = settingsView.findViewById(R.id.language);
        ImageButton backButton = backView.findViewById(R.id.backButton);
        Switch darkmodeSwitch = settingsView.findViewById(R.id.darkmodeSwitch);
        textViewLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().add(R.id.host_fragment_content_main, new LanguageSetting()).addToBackStack("Language").addToBackStack("language view").commit();

            }
        });
      /*  backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = getParentFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.host_fragment_content_main, fragment).commit();
            }

        });*/
        Context context = (MainActivity)getContext();
        assert context != null;
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        UiModeManager uiMode = MainActivity.getUiModeManager();
        darkmodeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(darkmodeSwitch.isChecked()) {
                    //((MainActivity)getActivity()).setNightMode(true);
                } else{
                  //  ((MainActivity)getActivity()).setNightMode(true);

                }

            }
            });
        return settingsView;
    }
}
