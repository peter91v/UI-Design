package com.example.easydo;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.easydo.databinding.FragmentSettingsBinding;
import com.example.easydo.databinding.LanguageFragmentBinding;
import com.example.easydo.ui.settings.SettingsViewModel;

public class LanguageFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private LanguageFragmentBinding binding;
    TextView lang;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = LanguageFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textSettings;
        settingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });
        LanguageFragment language = new LanguageFragment();

        binding.radioButtonEn.setOnClickListener(set ->{
            ((MainActivity)getActivity()).setLocale("en");
            binding.radioButtonEn.setChecked(true);
        });
        binding.radioButtonDe.setOnClickListener(set ->{
            ((MainActivity)getActivity()).setLocale("de");
            binding.radioButtonDe.setChecked(true);

        });
        binding.radioButtonHu.setOnClickListener(set ->{
            ((MainActivity)getActivity()).setLocale("hu");
            binding.radioButtonHu.setChecked(true);

        });
        FragmentManager manager = getParentFragmentManager();
        binding.quit.setOnClickListener(view -> {
            manager.beginTransaction().remove(this).commit();

        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}