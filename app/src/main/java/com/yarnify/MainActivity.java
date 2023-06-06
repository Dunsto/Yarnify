package com.yarnify;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.yarnify.R;
import com.example.yarnify.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.yarnify.API.Request;
import com.yarnify.viewmodel.PatternViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding binding;
    private PatternViewModel patternViewModel;
    //background thread for API request
    private static final ExecutorService networkExecutorService =
            Executors.newFixedThreadPool(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout using the View Binding library and set the activity's content view
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide(); //hides title
        setUpBottomNav();

        networkExecutorService.execute(() -> {
            Request request = new Request("patterns.json?ids=1335913");
        });

    }

    public void setUpBottomNav() {
        BottomNavigationView navView = findViewById(R.id.nav_view); // finds the BottomNavigationView from main layout and assigns to navView object
        // Create an AppBarConfiguration object that specifies the top-level destinations for the navigation graph
        // and set it to include the three menu items in the BottomNavigationView
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        // finds the NavController associated with the navigation graph in the activity by its ID
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        // sets up the action bar to work with the NavController
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        // sets up the BottomNavigationView to work with the NavController
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}