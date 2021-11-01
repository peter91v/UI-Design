package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.calculator.MathParser.MathParser;
import com.example.calculator.MathParser.SimpleParser;

public class MainActivity extends AppCompatActivity {

    private Button btnDEL,btnRes,btnC;
    private TextView tvRes;
    private LinearLayout tvHist,tvHist2, Main;
    private String result;
    private List<Button> buttons;
    private Integer tvid = 0;
    private static final int[] BUTTON_IDS = new int[]{
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnplus,R.id.btnminus,R.id.btnmult, R.id.btndiv, R.id.btncomma };

    private static final String[] values = {"0","1","2","3","4","5","6","7","8","9","+","-","*","/",","};

    @SuppressLint({"SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide(); //<< this
        setContentView(R.layout.activity_main);

        btnC = findViewById(R.id.btnc);
        btnDEL= findViewById(R.id.btndel);
        Button btnMenu = findViewById(R.id.btnmenu);
        btnRes = findViewById(R.id.btnresult);
        ImageButton imbtnx = findViewById(R.id.imbtnX);

        tvRes= findViewById(R.id.calcres);
        tvHist = findViewById(R.id.tvhist);
        tvHist2 = findViewById(R.id.hist2);

        Main = findViewById(R.id.main);
        Button btnclear = findViewById(R.id.clear);
        btnDEL.setEnabled(false);

        buttons = new ArrayList<>();
        buttons = creatButtons(BUTTON_IDS, values);


        imbtnx.setOnClickListener(v -> {
            tvHist.setVisibility(View.INVISIBLE);
            setVisibility(true);

        });
        btnMenu.setOnClickListener(v -> {

            tvHist.setVisibility(View.VISIBLE);
            setVisibility(false);

            Main.setOnClickListener(c ->{
                tvHist.setVisibility(View.INVISIBLE);
                setVisibility(true);

            });
        });

        btnC.setOnClickListener(v -> {
            result = "";
            tvRes.setText("");
        });

        btnDEL.setOnClickListener(v -> {
            if (result.length() != 0)
            {
                result = result.substring(0, result.length() - 1);
            }
            else
                {
                btnDEL.setEnabled(false);}
                tvRes.setText(result);
        });

        Context context = getApplicationContext();
        btnclear.setOnClickListener(v -> tvHist2.removeAllViews());

        btnRes.setOnClickListener(v -> {

            TextView newtv = new TextView(this);
            newtv.setText(tvRes.getText());
            newtv.setTextColor(Color.WHITE);
            newtv.setTextSize(24);
            newtv.setPadding(25, 0, 0, 0);
            newtv.setPaintFlags(newtv.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

            newtv.setId(tvid);
            newtv.setOnClickListener(c -> {
                tvRes.setText(newtv.getText());
                result = (String) tvRes.getText();
                tvHist.setVisibility(View.INVISIBLE);
                setVisibility(true);

            });
            tvid++;
            newtv.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            tvHist2.addView(newtv);

            MathParser mP = new SimpleParser();
            try {
                tvRes.setText(mP.calculateExpression(result));
                result = (String) tvRes.getText();
            }
            catch (Exception e){
                CharSequence text = e.getMessage();
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });
    }

    /** Creating buttons and adding functions to numeric/operator buttons
     *
     * @param ids int[]
     * @param characters String[]
     * @return A list will be returned
     */
     @SuppressLint("SetTextI18n")
     List<Button> creatButtons(int[] ids, String[] characters){
         List<Button> Buttons = new ArrayList<>();
        int x = 0;
         tvRes= findViewById(R.id.calcres);

         for(int btnid : ids) {

            Button button = findViewById(btnid);
            int finalX = x;
            button.setOnClickListener(v -> {
                tvRes.setText(tvRes.getText() + characters[finalX]);
                btnDEL.setEnabled(true);
                result = (String) tvRes.getText();

            });
            Buttons.add(button);
            x++;
        }
        return Buttons;
    }

    /** This function by True set buttons Enabled, by false set buttons disabled
     *
     * @param state Boolean,
     */
    void setVisibility(Boolean state){
        for(Button i : buttons){
            i.setEnabled(state);
        }
        btnC.setEnabled(state);
        btnRes.setEnabled(state);
        btnDEL.setEnabled(state);
    }
}