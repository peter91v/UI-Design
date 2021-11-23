package com.example.easydo.ui.slideshow;

import com.example.easydo.ui.transform.TransformViewModel;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.easydo.MainActivity;
import com.example.easydo.R;
import com.example.easydo.databinding.FragmentSlideshowBinding;
import com.example.easydo.ui.transform.TransformFragment;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

    binding = FragmentSlideshowBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        //final TextView textView = binding.textSlideshow;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
            }
        });
        Context context = getContext();
        binding.setTaskDate.setOnClickListener(v ->{
            final Dialog fbDialogue = new Dialog(requireContext());
            //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
            fbDialogue.setContentView(R.layout.fragment_calendar);
            //fbDialogue.setContentView(R.layout.calendar_layout);
            fbDialogue.setTitle("Add new Task");

            fbDialogue.setCancelable(true);
            fbDialogue.show();});

        List<String> array = new ArrayList<>();
        binding.addTaskCancel.setOnClickListener(view -> {
            FragmentManager manager = getParentFragmentManager();
            manager.beginTransaction().remove(this).commit();

        });
        binding.save.setOnClickListener(view -> {
            FragmentManager manager = getParentFragmentManager();
            array.add("1");
            array.add("2");
            array.add("3");

            TransformViewModel.arrayList.setValue(array);

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