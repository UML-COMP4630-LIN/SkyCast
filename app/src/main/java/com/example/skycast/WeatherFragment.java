package com.example.skycast;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skycast.databinding.FragmentWeatherBinding;

/**
 * WeatherFragment is responsible for displaying the current weather details for a selected city.
 * It observes the weather data from the GetWeather ViewModel and updates the UI dynamically.
 */
public class WeatherFragment extends Fragment {
    private FragmentWeatherBinding binding;
    private String city;
    private String tempRangeString;

    /**
     * Called to create and return the view hierarchy for the fragment.
     *
     * @param inflater           The LayoutInflater object used to inflate views in the fragment.
     * @param container          The parent ViewGroup that the fragment's UI will be attached to.
     * @param savedInstanceState A Bundle containing the saved state of the fragment, if any.
     * @return The root view of the weather fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Obtain the city name from arguments and set it in the UI
        city = WeatherFragmentArgs.fromBundle(requireArguments()).getCity();
        binding.cityName.setText(city);

        TextView currentTemp = binding.currentTemp;
        TextView windSpeed = binding.windSpeed;
        TextView feelsLike = binding.feelsLike;
        TextView tempRange = binding.TempRange;
        Button metricConvertor = binding.button;
        ImageView weatherImage = binding.imageView;

        // Initialize the GetWeather ViewModel
        GetWeather getWeather = new ViewModelProvider(requireActivity()).get(GetWeather.class);
        getWeather.getData(city); // Fetch weather data for the selected city

        // Toggle between metric and imperial units when the button is clicked
        metricConvertor.setOnClickListener(v -> {
            boolean isMetric = !getWeather.getIsMetric().getValue();
            getWeather.setIsMetric(isMetric);
        });

        // Observe changes to the unit system and update the UI accordingly
        getWeather.getIsMetric().observe(getViewLifecycleOwner(), isMetric -> {
            if (isMetric) {
                metricConvertor.setText("CONVERT TO IMPERIAL");
                updateWeatherUI(getWeather, true, currentTemp, tempRange, feelsLike, windSpeed);
            } else {
                metricConvertor.setText("CONVERT TO METRIC");
                updateWeatherUI(getWeather, false, currentTemp, tempRange, feelsLike, windSpeed);
            }
        });

        // Observe specific weather data changes and update the UI dynamically
        observeWeatherData(getWeather, currentTemp, tempRange, feelsLike, windSpeed, weatherImage);

        return view;
    }

    /**
     * Updates the UI elements with weather data based on the selected unit system.
     *
     * @param getWeather  The ViewModel providing weather data.
     * @param isMetric    True if metric units should be used, false otherwise.
     * @param currentTemp The TextView for displaying the current temperature.
     * @param tempRange   The TextView for displaying the temperature range.
     * @param feelsLike   The TextView for displaying the "feels like" temperature.
     * @param windSpeed   The TextView for displaying the wind speed.
     */
    private void updateWeatherUI(GetWeather getWeather, boolean isMetric,
                                 TextView currentTemp, TextView tempRange,
                                 TextView feelsLike, TextView windSpeed) {
        if (isMetric) {
            currentTemp.setText(convertKelvinToCelsius(getWeather.getCurrTemp().getValue()));
            tempRange.setText(convertKelvinToCelsius(getWeather.getLowTemp().getValue()) + "- " +
                    convertKelvinToCelsius(getWeather.getHighTemp().getValue()));
            feelsLike.setText(convertKelvinToCelsius(getWeather.getFeels_like().getValue()));
            windSpeed.setText(getWeather.getSpeed().getValue() + " m/s");
        } else {
            currentTemp.setText(convertKelvinToFahrenheit(getWeather.getCurrTemp().getValue()));
            tempRange.setText(convertKelvinToFahrenheit(getWeather.getLowTemp().getValue()) + "- " +
                    convertKelvinToFahrenheit(getWeather.getHighTemp().getValue()));
            feelsLike.setText(convertKelvinToFahrenheit(getWeather.getFeels_like().getValue()));
            windSpeed.setText(meterPerSecondToFeetPerSecond(getWeather.getSpeed().getValue()));
        }
    }

    /**
     * Observes specific weather data fields and updates the UI dynamically.
     *
     * @param getWeather  The ViewModel providing weather data.
     * @param currentTemp The TextView for displaying the current temperature.
     * @param tempRange   The TextView for displaying the temperature range.
     * @param feelsLike   The TextView for displaying the "feels like" temperature.
     * @param windSpeed   The TextView for displaying the wind speed.
     */
    private void observeWeatherData(GetWeather getWeather, TextView currentTemp,
                                    TextView tempRange, TextView feelsLike, TextView windSpeed,
                                    ImageView weatherImage) {
        getWeather.getCurrTemp().observe(getViewLifecycleOwner(), temp ->
                currentTemp.setText(getWeather.getIsMetric().getValue() ?
                        convertKelvinToCelsius(temp) : convertKelvinToFahrenheit(temp))
        );

        getWeather.getLowTemp().observe(getViewLifecycleOwner(), lowTemp -> {
            tempRangeString = getWeather.getIsMetric().getValue() ?
                    convertKelvinToCelsius(lowTemp) : convertKelvinToFahrenheit(lowTemp);
        });

        getWeather.getHighTemp().observe(getViewLifecycleOwner(), highTemp -> {
            tempRangeString += "- " + (getWeather.getIsMetric().getValue() ?
                    convertKelvinToCelsius(highTemp) : convertKelvinToFahrenheit(highTemp));
            tempRange.setText(tempRangeString);
        });

        getWeather.getFeels_like().observe(getViewLifecycleOwner(), feels ->
                feelsLike.setText(getWeather.getIsMetric().getValue() ?
                        convertKelvinToCelsius(feels) : convertKelvinToFahrenheit(feels))
        );

        getWeather.getSpeed().observe(getViewLifecycleOwner(), speed ->
                windSpeed.setText(getWeather.getIsMetric().getValue() ?
                        speed + " m/s" : meterPerSecondToFeetPerSecond(speed))
        );
        getWeather.getMain().observe(getViewLifecycleOwner(), main -> {
            if (main.equals("Rain")) {
                weatherImage.setImageResource(R.drawable.rain);
            } else if (main.equals("Snow")) {
                weatherImage.setImageResource(R.drawable.snow);
            } else if (main.equals("Clouds")) {
                weatherImage.setImageResource(R.drawable.cloud);
            } else if (main.equals("Thunder")) {
                weatherImage.setImageResource(R.drawable.thunder_cloud);
            } else {
                weatherImage.setImageResource(R.drawable.cloudandsun);
            }
        });
    }

    /**
     * Converts a temperature from Kelvin to Fahrenheit.
     *
     * @param kelvin The temperature in Kelvin.
     * @return The temperature in Fahrenheit as a formatted string.
     */
    private String convertKelvinToFahrenheit(double kelvin) {
        return String.format("%.1f", (kelvin - 273.15) * 9 / 5 + 32) + " F°";
    }

    /**
     * Converts a temperature from Kelvin to Celsius.
     *
     * @param kelvin The temperature in Kelvin.
     * @return The temperature in Celsius as a formatted string.
     */
    private String convertKelvinToCelsius(double kelvin) {
        return String.format("%.1f", kelvin - 273.15) + " °C";
    }

    /**
     * Converts a speed from meters per second to feet per second.
     *
     * @param value The speed in meters per second.
     * @return The speed in feet per second as a formatted string.
     */
    private String meterPerSecondToFeetPerSecond(Double value) {
        return String.format("%.1f", value * 3.28084) + " ft/s";
    }

    /**
     * Cleans up resources when the fragment's view is destroyed.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
