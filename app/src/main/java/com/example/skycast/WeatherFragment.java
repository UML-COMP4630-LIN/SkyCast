package com.example.skycast;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.skycast.databinding.FragmentWeatherBinding;


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
        Button metricConvertor = binding.button;
        cityName.setText(city);


        GetWeather getWeather = new ViewModelProvider(requireActivity()).get(GetWeather.class);
        getWeather.getData(city);

        metricConvertor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean temp = new Boolean(!getWeather.getIsMetric().getValue());
                getWeather.setIsMetric(temp);

            }
        });

        getWeather.getIsMetric().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    metricConvertor.setText("CONVERT TO IMPERIAL");
                    String temp;
                    currentTemp.setText(convertKelvinToCelsius(getWeather.getCurrTemp().getValue()));
                    temp = convertKelvinToCelsius(getWeather.getLowTemp().getValue()) + "- " + convertKelvinToCelsius(getWeather.getHighTemp().getValue());
                    tempRange.setText(temp);
                    feelsLike.setText(convertKelvinToCelsius(getWeather.getFeels_like().getValue()));
                    windSpeed.setText(String.valueOf(getWeather.getSpeed().getValue()) + " m/s");
                } else {
                    metricConvertor.setText("CONVERT TO METRIC");
                    String temp;
                    currentTemp.setText(convertKelvinToFahrenheit(getWeather.getCurrTemp().getValue()));
                    temp = convertKelvinToFahrenheit(getWeather.getLowTemp().getValue()) + "- " + convertKelvinToFahrenheit(getWeather.getHighTemp().getValue());
                    tempRange.setText(temp);
                    feelsLike.setText(convertKelvinToFahrenheit(getWeather.getFeels_like().getValue()));
                }
            }
        });

        //Observe implementation example
        getWeather.getCurrTemp().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double s) {
                String temp = getWeather.getIsMetric().getValue()? convertKelvinToCelsius(s):convertKelvinToFahrenheit(s);
                currentTemp.setText(temp);
            }
        });

        getWeather.getLowTemp().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double s) {
                tempRangeString = getWeather.getIsMetric().getValue()? convertKelvinToCelsius(s):convertKelvinToFahrenheit(s);
            }
        });
        getWeather.getHighTemp().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double s) {
                String temp = getWeather.getIsMetric().getValue()? convertKelvinToCelsius(s):convertKelvinToFahrenheit(s);
                tempRangeString += "- " + temp;
                tempRange.setText(tempRangeString);
            }
        });

        getWeather.getFeels_like().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double s) {
                String temp = getWeather.getIsMetric().getValue()? convertKelvinToCelsius(s):convertKelvinToFahrenheit(s);
                feelsLike.setText(temp);
            }
        });
        getWeather.getSpeed().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double s) {
                String temp = getWeather.getIsMetric().getValue()? String.valueOf(s) + " m/s":meterPerSecondToFeetPerSecond(s);
                windSpeed.setText(temp);
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

    private String convertKelvinToFahrenheit(double kelvin) {
        return String.format("%.1f", (kelvin - 273.15) * 9 / 5 + 32) + " F°";
    }

    private String convertKelvinToCelsius(double kelvin) {
        return String.format("%.1f", kelvin - 273.15) + " °C";
    }
    private String meterPerSecondToFeetPerSecond(Double value) {
        return String.format("%.1f", value * 3.28084) + " ft/s";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
