/***********************************************
 Authors: <Theodor Farag, Afsa Nafe, Angelos Boules>
 Date: <12/6/24>
 Purpose: <The purpose of the app is display the weather information on a selected city based on the information retrived based
 on the API from openWeather >
 What Learned: <We learned how to put the skills we have ben taught all in one app to make it more dynamic is useful,
 We also learned to how to use and access information from an API. How to parse a JSON file using Retrofits and a api interface
 to set up the API URL, As well as how to make animations and cool designs based by making a resourse file and sepecifying
 the snimation feature within the file>
 Sources of Help: <
 https://developer.android.com/studio
 https://www.youtube.com/watch?v=T5v74K9fOUw&t=879s&ab_channel=Code24
 >
 Time Spent (Hours): <5-7 days>
 ***********************************************/
package com.example.skycast;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.skycast.databinding.ActivityMainBinding;

/**
 * MainActivity is the entry point of the application and handles navigation
 * between fragments, toolbar setup, and the bottom navigation menu.
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppBarConfiguration appBarConfiguration;

    /**
     * Initializes the activity, sets up navigation components, and configures the app bar and
     * bottom navigation.
     *
     * @param savedInstanceState A Bundle containing the saved state of the activity, if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up the MaterialToolbar as the app bar
        setSupportActionBar(binding.toolbar);

        // Set up NavController
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(binding.navHostFragment.getId());
        NavController navController = navHostFragment.getNavController();

        // Configure AppBarConfiguration for top-level destinations
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.citiesFragment, R.id.profileFragment
        ).build();

        // Link the toolbar with the NavController (without automatic title updates)
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Disable automatic title updates and set app title manually
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            getSupportActionBar().setTitle(R.string.app_name);
        });

        // Set up BottomNavigationView with the NavController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
    }

    /**
     * Inflates the options menu in the toolbar.
     *
     * @param menu The options menu in which items are placed.
     * @return True to display the menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Top App Bar menu
        getMenuInflater().inflate(R.menu.top_app_bar_menu, menu);
        return true;
    }

    /**
     * Handles item selection from the options menu.
     *
     * @param item The selected menu item.
     * @return True if the menu item was successfully handled.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Get the NavController
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        // Handle menu item clicks
        if (item.getItemId() == R.id.helpFragment) {
            navController.navigate(R.id.helpFragment);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handles the Up button navigation.
     *
     * @return True if navigation up was handled successfully.
     */
    @Override
    public boolean onSupportNavigateUp() {
        // Handle Up button navigation
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
/*
Mobile App Development I -- COMP.4630 Honor Statement
The practice of good ethical behavior is essential for maintaining good order
in the classroom, providing an enriching learning experience for students,
and training as a practicing computing professional upon graduation. This
practice is manifested in the University's Academic Integrity policy.
Students are expected to strictly avoid academic dishonesty and adhere to the
Academic Integrity policy as outlined in the course catalog. Violations will
be dealt with as outlined therein. All programming assignments in this class
are to be done by the student alone unless otherwise specified. No outside
help is permitted except the instructor and approved tutors.
I certify that the work submitted with this assignment is mine and was
generated in a manner consistent with this document, the course academic
policy on the course website on Blackboard, and the UMass Lowell academic
code.
Date: 12/6/24
Names:Theodor Farag, Afsa Nafe, Angelos Boules
*/