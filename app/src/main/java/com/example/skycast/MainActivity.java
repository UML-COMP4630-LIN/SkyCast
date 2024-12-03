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

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppBarConfiguration appBarConfiguration;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the Top App Bar menu
        getMenuInflater().inflate(R.menu.top_app_bar_menu, menu);
        return true;
    }

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

    @Override
    public boolean onSupportNavigateUp() {
        // Handle Up button navigation
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
