package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btnC, btnseven, btnfour, btnone, btnPerc, btnDiv, btneight, btnfive, btntwo, btnNull;
    private Button btnMult, btnNine, btnSix, btnTree, btnKomm, btnDEL, btnMin, btnPlus, btnRes;
    ImageButton btnMenu, imbtnx;
    private TextView tvRes;
    private LinearLayout tvHist, Main;
    private String result;
    private List<Button> buttons;
    private Boolean plus,minus,division,multiply;
    private Integer id = 0, tvid = 0;

    private static final int[] BUTTON_IDS = new int[]{
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9};

    private final String[] values = {"0","1","2","3","4","5","6","7","8","9"};

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btnC= findViewById(R.id.btnc);
        btnPerc= findViewById(R.id.btnpr);
        btnDiv= findViewById(R.id.btndiv);
        btnMult= findViewById(R.id.btnmult);
        btnKomm= findViewById(R.id.btncomma);
        btnDEL= findViewById(R.id.btndel);
        btnMin= findViewById(R.id.btnminus);
        btnPlus= findViewById(R.id.btnplus);
        btnRes= findViewById(R.id.btnresult);
        tvRes= findViewById(R.id.calcres);
        btnDEL.setEnabled(false);
        btnMenu = findViewById(R.id.btnmenu);
        tvHist = findViewById(R.id.tvhist);
        imbtnx = findViewById(R.id.imbtnX);
        Main = findViewById(R.id.main);
        buttons = new ArrayList<Button>();
        int x = 0;
        for(int btnid : BUTTON_IDS) {

            Button button = (Button)findViewById(btnid);
            int finalX = x;
            button.setOnClickListener(v -> {
                tvRes.setText(tvRes.getText() + values[finalX]);
                btnDEL.setEnabled(true);
                result = (String) tvRes.getText();

            });
            buttons.add(button);
            x++;
        }

        imbtnx.setOnClickListener(v -> {
            tvHist.setVisibility(View.INVISIBLE);

        });
        btnMenu.setOnClickListener(v -> {

            tvHist.setVisibility(View.VISIBLE);
            Main.setOnClickListener(c -> {
                tvHist.setVisibility(View.INVISIBLE);

            });
        });

        btnC.setOnClickListener(v -> {

        });
        btnDEL.setOnClickListener(v -> {
            if (result.length() != 0){

                result = result.substring(0, result.length() - 1);}

            else{
                btnDEL.setEnabled(false);}
                tvRes.setText(result);
        });
        btnPlus.setOnClickListener(v -> {


        });
        btnMin.setOnClickListener(v -> {

        });
        btnRes.setOnClickListener(v -> {

            TextView newtv = new TextView(this);
            newtv.setText(tvRes.getText());
            newtv.setTextColor(Color.WHITE);
            newtv.setTextSize(24);
            //newtv.setBackgroundColor(Color.GRAY);

            newtv.setId(tvid);
            newtv.setOnClickListener(c -> {
                tvRes.setText(newtv.getText());
                tvHist.setVisibility(View.INVISIBLE);

            });
            tvid++;
            newtv.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            ((LinearLayout) tvHist).addView(newtv);
        });


    }


}