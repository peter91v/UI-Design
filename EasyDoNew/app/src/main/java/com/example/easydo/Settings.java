package com.example.easydo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class Settings extends Fragment {
    private TextView textViewLanguage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View settingsView = inflater.inflate(R.layout.settings, container, false);
        textViewLanguage = settingsView.findViewById(R.id.language);

        textViewLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction().add(R.id.host_fragment_content_main, new LanguageSetting()).addToBackStack("Language").addToBackStack("language view").commit();

            }
        });
        return settingsView;
    }
}
