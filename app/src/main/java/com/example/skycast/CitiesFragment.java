package com.example.skycast;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skycast.databinding.FragmentCitiesBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * CitiesFragment is responsible for displaying a list of cities in a RecyclerView.
 * It dynamically adjusts the layout based on the device orientation and saves
 * the checked states of cities during configuration changes.
 */

public class CitiesFragment extends Fragment {
    private FragmentCitiesBinding binding;
    private CityAdapter cityAdapter;
    private List<City> cityList;


    /**
     * Called to initialize the fragment's UI and set up the RecyclerView.
     *
     * @param inflater           The LayoutInflater used to inflate views in the fragment.
     * @param container          The container where the fragment's UI will be placed.
     * @param savedInstanceState A Bundle containing the saved state of the fragment, if any.
     * @return The root view of the fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCitiesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Initialize the ViewModel
        CityViewModel cityViewModel = new ViewModelProvider(requireActivity()).get(CityViewModel.class);

        // Get the list of cities
        cityList = getCities();

        // Restore saved states for cities (if any)
        if (savedInstanceState != null) {
            boolean[] checkedStates = savedInstanceState.getBooleanArray("cities_checked");
            if (checkedStates != null) {
                for (int i = 0; i < cityList.size(); i++) {
                    cityList.get(i).setChecked(checkedStates[i]);
                }
            }
        }

        // Set up the RecyclerView layout manager
        setLayoutManager();

        // Set up the adapter for RecyclerView
        cityAdapter = new CityAdapter(cityList, cityViewModel);
        binding.cityListRecycler.setAdapter(cityAdapter);

        return view;
    }

    /**
     * Saves the checked states of the cities when the fragment's state is saved.
     *
     * @param state The Bundle where the state will be saved.
     */
    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        // Save the checked states of cities
        boolean[] checkedStates = new boolean[cityList.size()];
        for (int i = 0; i < cityList.size(); i++) {
            checkedStates[i] = cityList.get(i).isChecked();
        }
        state.putBooleanArray("cities_checked", checkedStates);
    }

    /**
     * Cleans up resources when the fragment's view is destroyed.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Avoid memory leaks
    }

    /**
     * Sets the appropriate layout manager for the RecyclerView based on device orientation.
     */
    private void setLayoutManager() {
        // Determine the orientation of the device
        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Use GridLayoutManager with 2 columns in landscape mode
            binding.cityListRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            // Use LinearLayoutManager in portrait mode
            binding.cityListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }

    /**
     * Creates and returns a predefined list of cities.
     *
     * @return A list of City objects.
     */
    private List<City> getCities() {
        // Predefined list of cities
        List<City> cities = new ArrayList<>();
        cities.add(new City("Boston"));
        cities.add(new City("Lowell"));
        cities.add(new City("Alexandria"));
        cities.add(new City("Cairo"));
        cities.add(new City("Tokyo"));
        cities.add(new City("New York City"));
        cities.add(new City("London"));
        cities.add(new City("Paris"));
        cities.add(new City("Moscow"));
        cities.add(new City("Hong Kong"));
        cities.add(new City("New Delhi"));
        cities.add(new City("Athens"));
        cities.add(new City("Rome"));
        cities.add(new City("Florence"));
        cities.add(new City("Madrid"));
        cities.add(new City("Barcelona"));

        return cities;
    }
}