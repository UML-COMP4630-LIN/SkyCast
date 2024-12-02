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

public class CitiesFragment extends Fragment {
    private FragmentCitiesBinding binding;
    private CityAdapter cityAdapter;
    private List<City> cityList;

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Avoid memory leaks
    }

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