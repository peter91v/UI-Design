package com.example.easydo;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.easydo.databinding.FragmentCalendarBinding;
import com.example.easydo.databinding.FragmentSlideshowBinding;
import com.example.easydo.ui.transform.TransformViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FragmentCalendarBinding binding;
    FragmentSlideshowBinding slidBlinding;
    EditText textView = null;
    Button savebutton, quitButton;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Calendar.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private StringBuilder toString(long date) {
        return new StringBuilder()
                .append(date);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        View view2 = inflater.inflate(R.layout.fragment_slideshow, container, false);

        Context context = getContext();


        long date = 25;
        savebutton = view.findViewById(R.id.save_date);
        savebutton.setOnClickListener(v->{

            textView = view2.findViewById(R.id.set_task_date);

            textView.setText("sasad");
        });

        quitButton = view.findViewById(R.id.close_date);
        quitButton.setOnClickListener(v->{
            FragmentManager manager = getChildFragmentManager();
                 manager.beginTransaction().remove(this).commit();
        });
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

}