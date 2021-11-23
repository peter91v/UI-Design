package com.example.easydo.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.easydo.LanguageFragment;
import com.example.easydo.MainActivity;
import com.example.easydo.R;
import com.example.easydo.databinding.FragmentSettingsBinding;



public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private FragmentSettingsBinding binding;
    TextView lang;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

    binding = FragmentSettingsBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        final TextView textView = binding.textSettings;
        settingsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        LanguageFragment language = new LanguageFragment();
        binding.textSettingsLang.setOnClickListener(view -> {
           /* final Dialog fbDialogue = new Dialog(requireContext());
            //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
            fbDialogue.setContentView(R.layout.fragment_language);
            //fbDialogue.setContentView(R.layout.calendar_layout);
            fbDialogue.setTitle("Add new Task");

            fbDialogue.setCancelable(true);
            fbDialogue.show();*/
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_container, language).setReorderingAllowed(true).addToBackStack("replacement").commit();

        });
        binding.textSettings.setOnClickListener(set ->{
            ((MainActivity)getActivity()).setLocale("en");

        });

        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}