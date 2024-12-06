package com.example.skycast;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.skycast.databinding.FragmentWeatherBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * WeatherFragment is responsible for displaying the current weather details for a selected city.
 * It observes the weather data from the GetWeather ViewModel and updates the UI dynamically.
 */
public class WeatherFragment extends Fragment {
    private FragmentWeatherBinding binding;
    private String city;
    private String tempRangeString;
    private GradientDrawable gradientDrawable;


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


        // Load animations
        Animation growShrinkAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.shrink_grow_weather);

        // Apply the animation to the weather icon
        binding.weatherIconImage.startAnimation(growShrinkAnimation);


        // Obtain the city name from arguments and set it in the UI
        city = WeatherFragmentArgs.fromBundle(requireArguments()).getCity();
        binding.cityName.setText(city);

        TextView currentTemp = binding.currentTemp;
        TextView windSpeed = binding.windSpeed;
        TextView feelsLike = binding.feelsLike;
        TextView tempRange = binding.TempRange;
        Button metricConvertor = binding.button;
        ImageView weatherImage = binding.weatherIconImage;

        TextView humidityPercent = binding.humidityPercent;

        TextView sunriseText = binding.sunriseText;
        TextView sunsetText = binding.sunsetText;



        // Initialize the GetWeather ViewModel
        GetWeather getWeather = new ViewModelProvider(requireActivity()).get(GetWeather.class);
        getWeather.getData(city); // Fetch weather data for the selected city

        // Toggle between metric and imperial units when the button is clicked
        metricConvertor.setOnClickListener(v -> {
            boolean isMetric = getWeather.getIsMetric().getValue() != null && !getWeather.getIsMetric().getValue();
            getWeather.setIsMetric(isMetric);
        });

        // Observe changes to the unit system and update the UI accordingly
        getWeather.getIsMetric().observe(getViewLifecycleOwner(), isMetric -> {
            if (isMetric != null) {
                metricConvertor.setText(isMetric ? "CONVERT TO IMPERIAL" : "CONVERT TO METRIC");
                updateWeatherUI(getWeather, isMetric, currentTemp, tempRange, feelsLike, windSpeed, sunriseText, sunsetText);
            }
        });

        // Observe specific weather data changes and update the UI dynamically
        observeWeatherData(getWeather, currentTemp, tempRange, feelsLike, windSpeed, weatherImage, sunriseText, sunsetText, humidityPercent);

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
                                 TextView feelsLike, TextView windSpeed, TextView sunriseText, TextView sunsetText) {
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

        // Time conversion for sunrise and sunset
        getWeather.getSunrise().observe(getViewLifecycleOwner(), sunrise -> {
            if (sunrise != null) {
                sunriseText.setText(getString(R.string.sunrise, formatTime(sunrise, isMetric)));
            }
        });

        getWeather.getSunset().observe(getViewLifecycleOwner(), sunset -> {
            if (sunset != null) {
                sunsetText.setText(getString(R.string.sunset, formatTime(sunset, isMetric)));
            }
        });
    }

    private String formatTime(long unixTime, boolean isMetric) {
        // Convert UNIX timestamp to milliseconds
        Date date = new Date(unixTime * 1000);

        // Define format based on metric system
        SimpleDateFormat sdf = isMetric
                ? new SimpleDateFormat("HH:mm", Locale.getDefault()) // 24-hour format
                : new SimpleDateFormat("hh:mm a", Locale.getDefault()); // 12-hour format

        sdf.setTimeZone(TimeZone.getDefault()); // Use device's local timezone
        return sdf.format(date);
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
                                    ImageView weatherImage, TextView sunriseText, TextView sunsetText, TextView humidityTextView) {
        getWeather.getCurrTemp().observe(getViewLifecycleOwner(), temp -> {
            if (temp != null) {
                currentTemp.setText(getWeather.getIsMetric().getValue() ?
                        convertKelvinToCelsius(temp) : convertKelvinToFahrenheit(temp));
            }
        });

        getWeather.getLowTemp().observe(getViewLifecycleOwner(), lowTemp -> {
            if (lowTemp != null) {
                tempRangeString = getWeather.getIsMetric().getValue() ?
                        convertKelvinToCelsius(lowTemp) : convertKelvinToFahrenheit(lowTemp);
            }
        });

        getWeather.getHighTemp().observe(getViewLifecycleOwner(), highTemp -> {
            if (highTemp != null) {
                tempRangeString += "- " + (getWeather.getIsMetric().getValue() ?
                        convertKelvinToCelsius(highTemp) : convertKelvinToFahrenheit(highTemp));
                tempRange.setText(tempRangeString);
            }
        });

        getWeather.getFeels_like().observe(getViewLifecycleOwner(), feels -> {
            if (feels != null) {
                feelsLike.setText(getWeather.getIsMetric().getValue() ?
                        convertKelvinToCelsius(feels) : convertKelvinToFahrenheit(feels));
            }
        });

        getWeather.getSpeed().observe(getViewLifecycleOwner(), speed -> {
            if (speed != null) {
                windSpeed.setText(getWeather.getIsMetric().getValue() ?
                        speed + " m/s" : meterPerSecondToFeetPerSecond(speed));
            }
        });

        // Observe sunrise and sunset values and update the respective TextViews
        getWeather.getSunrise().observe(getViewLifecycleOwner(), sunrise -> {
            if (sunrise != null) {
                sunriseText.setText(getString(R.string.sunrise, convertTime(sunrise)));
            }
        });

        getWeather.getSunset().observe(getViewLifecycleOwner(), sunset -> {
            if (sunset != null) {
                sunsetText.setText(getString(R.string.sunset, convertTime(sunset)));
            }
        });

        getWeather.getHumidity().observe(getViewLifecycleOwner(), humidity -> {
            if (humidity != null) {
                String humidityText = humidity + "%";
                humidityTextView.setText(humidityText);
            }
        });


        getWeather.getMain().observe(getViewLifecycleOwner(), main -> {
            if (main != null) {
                int[] colors;
                switch (main) {
                    case "Rain":
                        weatherImage.setImageResource(R.drawable.rain);
                        colors = new int[]{
                                Color.parseColor("#6E8FB4"), // Dark blue
                                Color.parseColor("#8CA9CC"), // Medium blue
                                Color.parseColor("#A3C0D9")  // Light blue
                        };
                        break;
                    case "Snow":
                        weatherImage.setImageResource(R.drawable.snow);
                        colors = new int[]{
                                Color.parseColor("#E0EFFF"), // Very light blue
                                Color.parseColor("#B3D9FF"), // Light blue
                                Color.parseColor("#80C1FF")  // Sky blue
                        };
                        break;
                    case "Clouds":
                        weatherImage.setImageResource(R.drawable.perfectclouds);
                        colors = new int[]{
                                Color.parseColor("#B0BEC5"), // Gray
                                Color.parseColor("#CFD8DC"), // Light gray
                                Color.parseColor("#ECEFF1")  // Very light gray
                        };
                        break;
                    case "Thunder":
                        weatherImage.setImageResource(R.drawable.thunder_cloud);
                        colors = new int[]{
                                Color.parseColor("#3E2723"), // Dark brown
                                Color.parseColor("#5D4037"), // Medium brown
                                Color.parseColor("#8D6E63")  // Light brown
                        };
                        break;
                    default:
                        weatherImage.setImageResource(R.drawable.cloudandsun);
                        colors = new int[]{
                                Color.parseColor("#FFE082"), // Light yellow
                                Color.parseColor("#FFCC80"), // Peach
                                Color.parseColor("#FFAB91")  // Light orange
                        };
                        break;
                }
                // Start the dynamic background animation
                // Pass colors to animateBackground

                // Set the gradient background with the selected colors
                GradientDrawable gradientDrawable = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM, colors
                );
                binding.getRoot().setBackground(gradientDrawable);
            }
        });

    }





    /**
     * Adjustments made to animation
     * 1. Intermediate Values Cast to int: The results of calculations with fraction (a float) are
     *   explicitly cast to int before applying bitwise operations.
     *
     * 2. Valid Use of <<: The corrected code ensures only integers are involved in the bitwise left-shift operations.
     * */



    private String convertTime(long unixTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getDefault()); // Use the device's local timezone
        return sdf.format(new Date(unixTime * 1000)); // Convert seconds to milliseconds
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
