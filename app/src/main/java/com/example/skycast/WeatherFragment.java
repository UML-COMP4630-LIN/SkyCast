package com.example.skycast;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.skycast.databinding.FragmentWeatherBinding;

import retrofit2.Retrofit;


public class WeatherFragment extends Fragment {
    private FragmentWeatherBinding binding;
    private String city;
    private String tempRangeString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherBinding.inflate(inflater,container, false);
        View view = binding.getRoot();

        //obtain the city String and then ensuring its valid for the api call
        city = WeatherFragmentArgs.fromBundle(requireArguments()).getCity();

        TextView currentTemp = binding.currentTemp;
        TextView windSpeed = binding.windSpeed;
        TextView feelsLike = binding.feelsLike;
        TextView tempRange = binding.TempRange;
        TextView cityName = binding.cityName;
        cityName.setText(city);


        GetWeather getWeather = new ViewModelProvider(requireActivity()).get(GetWeather.class);
        getWeather.getData(city);

        //Observe implementation example
        getWeather.getCurrTemp().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                currentTemp.setText(s);
            }
        });

        getWeather.getLowTemp().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tempRangeString = s;
            }
        });
        getWeather.getHighTemp().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tempRangeString += "- " + s;
                tempRange.setText(tempRangeString);
            }
        });

        getWeather.getFeels_like().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                feelsLike.setText(s);
            }
        });
        getWeather.getSpeed().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                windSpeed.setText(s);
            }
        });

        getWeather.getDescription().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                // update UI that contains weather description
            }
        });

        getWeather.getMain().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                // update the imageView based on the description
            }
        });

        getWeather.getSunrise().observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                //update UI for sunrise value
            }
        });
        getWeather.getSunset().observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                    // update UI for sunset value
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
