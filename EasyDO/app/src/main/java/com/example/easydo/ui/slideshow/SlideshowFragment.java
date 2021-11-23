package com.example.easydo.ui.slideshow;

import com.example.easydo.CalendarFragment;
import com.example.easydo.TaskManager;
import com.example.easydo.ui.transform.TransformViewModel;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
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
import java.util.Calendar;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private FragmentSlideshowBinding binding;
    private DatePickerDialog datePickerDialog;
    private EditText editText;

    private TaskManager taskManager = new TaskManager();

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
        CalendarFragment calendarFragment = new CalendarFragment();
        binding.setTaskDate.setOnClickListener(v ->{
            /*final Dialog fbDialogue = new Dialog(requireContext());
            //fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
            fbDialogue.setContentView(R.layout.fragment_calendar);
            //fbDialogue.setContentView(R.layout.calendar_layout);
            fbDialogue.setTitle("Add new Task");

            fbDialogue.setCancelable(true);
            fbDialogue.show();*/
            //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_container, calendarFragment).setReorderingAllowed(true).addToBackStack("replacement").commit();
            View view = inflater.inflate(R.layout.fragment_slideshow, container, false);

            editText = view.findViewById(R.id.set_task_date);
            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int mounth = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            Context context1 = getContext();
            datePickerDialog = new DatePickerDialog(context1, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int mounth, int day) {
                    binding.setTaskDate.setText(day+ "/" + (mounth + 1 ) + "/" + year);
                }
            },year,mounth,day);
            datePickerDialog.show();
        });

        List<String> array = new ArrayList<>();
        binding.addTaskCancel.setOnClickListener(view -> {
            FragmentManager manager = getParentFragmentManager();
            binding.setTaskDate.setText("");
            binding.setTaskName.setText("");
            binding.editTextTextMultiLine.setText("");
            manager.beginTransaction().remove(this).commit();

        });
        binding.save.setOnClickListener(view -> {
            FragmentManager manager = getParentFragmentManager();
            array.add("1");
            array.add("2");
            array.add("3");
            array.add("3");

            TransformViewModel.arrayList.setValue(array);
            binding.setTaskDate.setText("");
            binding.setTaskName.setText("");
            binding.editTextTextMultiLine.setText("");
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