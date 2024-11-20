package com.example.skycast;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.skycast.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Access the shared ViewModel
        CityViewModel cityViewModel = new ViewModelProvider(requireActivity()).get(CityViewModel.class);

        // Observe the selected cities list and update the spinner
        cityViewModel.getSelectedCities().observe(getViewLifecycleOwner(), cities -> {
            List<String> cityNames = new ArrayList<>();
            for (City city : cities) {
                cityNames.add(city.getName());
            }

            // Set up the Spinner with the updated list
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                    android.R.layout.simple_spinner_item, cityNames);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.selectCitySpinner.setAdapter(adapter);
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Avoid memory leaks
    }
}
