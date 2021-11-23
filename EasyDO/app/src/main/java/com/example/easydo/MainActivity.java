package com.example.easydo;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.example.easydo.ui.slideshow.SlideshowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.app.AppCompatActivity;
import com.example.easydo.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    View view;
    static ArrayList<Integer> arrayList = new ArrayList<>();

    Context context;
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Activity mActivity = MainActivity.this;

        CoordinatorLayout coordinatorLayout = findViewById(R.id.add_task);
        setSupportActionBar(Objects.requireNonNull(binding.appBarMain).toolbar);

        SlideshowFragment slideshowFragment = new SlideshowFragment();



/*
            AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
            // Set a title for alert dialog
            builder.setTitle("Say Hello!");
            // Show a message on alert dialog

            builder.setMessage("Are you want to do this?");

            // Set the positive button

            builder.setPositiveButton("Say",null);
            // Set the negative button

            builder.setNegativeButton("No", null);
            // Set the neutral button

            builder.setNeutralButton("Cancel", null);
            // Create the alert dialog

            AlertDialog dialog = builder.create();
            // Finally, display the alert dialog

            dialog.show();

            // Get the alert dialog buttons reference

            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            Button neutralButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);

            // Change the alert dialog buttons text and background color
            positiveButton.setTextColor(Color.parseColor("#FF0B8B42"));

            positiveButton.setBackgroundColor(Color.parseColor("#FFE1FCEA"));
            negativeButton.setTextColor(Color.parseColor("#FFFF0400"));
            negativeButton.setBackgroundColor(Color.parseColor("#FFFCB9B7"));
            neutralButton.setTextColor(Color.parseColor("#FF1B5AAC"));
            neutralButton.setBackgroundColor(Color.parseColor("#FFD9E9FF"));
*/


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationView navigationView = binding.navView;
        if (navigationView != null) {
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_transform, R.id.nav_reflow, R.id.nav_slideshow, R.id.nav_calendar, R.id.nav_settings)
                    .setOpenableLayout(binding.drawerLayout)
                    .build();
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
        }

        BottomNavigationView bottomNavigationView = binding.appBarMain.contentMain.bottomNavView;
        if (bottomNavigationView != null) {
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_transform, R.id.nav_reflow, R.id.nav_slideshow, R.id.nav_calendar)
                    .build();
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
            getSupportFragmentManager().beginTransaction().remove(slideshowFragment).commit();

        }
       binding.appBarMain.fab.setOnClickListener(v -> {
           getSupportFragmentManager().beginTransaction().replace(R.id.activity_container, slideshowFragment).setReorderingAllowed(true).addToBackStack("replacement").commit();
       });
        LanguageFragment language = new LanguageFragment();
        ViewGroup container = null;
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        view.findViewById(R.id.text_settings_lang).setOnClickListener(vi ->{
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_container, language ).setReorderingAllowed(true).addToBackStack("replacement").commit();
        });
        View view2 = inflater.inflate(R.layout.language_fragment, container, false);

        view2.findViewById(R.id.radioButton_en).setOnClickListener(vi ->{
            setLocale("en");
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        // Using findViewById because NavigationView exists in different layout files
        // between w600dp and w1240dp
        NavigationView navView = findViewById(R.id.nav_view);
        if (navView == null) {
            // The navigation drawer already has the items including the items in the overflow menu
            // We only inflate the overflow menu if the navigation drawer isn't visible

            getMenuInflater().inflate(R.menu.overflow, menu);
        }
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.nav_settings) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.nav_settings);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }





    public void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
        //recreate();
        recreate();
    }
}