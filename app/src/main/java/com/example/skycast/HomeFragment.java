package com.example.skycast;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.skycast.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * HomeFragment is responsible for displaying the home screen of the app.
 * It includes animations, dynamic backgrounds, and navigation to the weather fragment.
 * It also observes the selected cities from the shared ViewModel and updates the UI accordingly.
 */
public class HomeFragment extends Fragment {
    private String currentImage = "cloudandsun"; // Track the current image state
    private GradientDrawable gradientDrawable;

    private FragmentHomeBinding binding;

    /**
     * Called to initialize the fragment's UI and set up animations, background, and data.
     *
     * @param inflater           The LayoutInflater used to inflate views in the fragment.
     * @param container          The container where the fragment's UI will be placed.
     * @param savedInstanceState A Bundle containing the saved state of the fragment, if any.
     * @return The root view of the fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //Rain Animation
        ImageView rainAnimationView = binding.rainAnimationView;
        AnimationDrawable rainAnimation = (AnimationDrawable) rainAnimationView.getDrawable();
        rainAnimation.start();

        // Load the gradient background
        gradientDrawable = (GradientDrawable) ContextCompat.getDrawable(requireContext(), R.drawable.gradient_background);

        // Load animations
        Animation growShrinkAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.shrink_grow);
        Animation fadeOut = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out);
        Animation fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in);

        // Apply initial animation
        binding.weatherIconImage.startAnimation(growShrinkAnimation);
        binding.weatherIconImage.setTag("cloudandsun");

        // Step 1: Chain animations
        growShrinkAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                // Start fade-out based on the current image state
                if (currentImage.equals("cloudandsun") || currentImage.equals("thunder")) {
                    binding.weatherIconImage.startAnimation(fadeOut);
                } else if (currentImage.equals("cloudtransition")) {
                    binding.lightningbolt.startAnimation(fadeOut);
                    binding.lightningcloud.startAnimation(fadeOut);
                } else if (currentImage.equals("rain")) {
                    binding.rainAnimationView.startAnimation(fadeOut);
                    binding.lightningcloud.startAnimation(fadeOut);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                // Handle state transitions
                handleStateTransition();

                // Start the fade-in animation and shrink animations
                if (currentImage.equals("cloudandsun") || currentImage.equals("thunder")) {
                    binding.weatherIconImage.setVisibility(View.VISIBLE);
                    binding.rainAnimationView.setVisibility(View.INVISIBLE);
                    binding.lightningcloud.setVisibility(View.INVISIBLE);
                    binding.lightningbolt.setVisibility(View.INVISIBLE);
                    binding.weatherIconImage.startAnimation(fadeIn);
                    binding.weatherIconImage.startAnimation(growShrinkAnimation);
                } else if (currentImage.equals("cloudtransition")) {
                    binding.weatherIconImage.setVisibility(View.INVISIBLE);
                    binding.rainAnimationView.setVisibility(View.INVISIBLE);
                    binding.lightningcloud.setVisibility(View.VISIBLE);
                    binding.lightningbolt.setVisibility(View.VISIBLE);
                    binding.lightningcloud.startAnimation(fadeIn);
                    binding.lightningbolt.startAnimation(fadeIn);
                    binding.lightningcloud.startAnimation(growShrinkAnimation);
                    binding.lightningbolt.startAnimation(growShrinkAnimation);
                }else if (currentImage.equals("rain")) {
                    binding.weatherIconImage.setVisibility(View.INVISIBLE);
                    binding.rainAnimationView.setVisibility(View.VISIBLE);
                    binding.lightningcloud.setVisibility(View.VISIBLE);
                    binding.lightningbolt.setVisibility(View.INVISIBLE);
                    binding.lightningcloud.startAnimation(fadeIn);
                    binding.rainAnimationView.startAnimation(fadeIn);
                    binding.lightningcloud.startAnimation(growShrinkAnimation);
                    binding.rainAnimationView.startAnimation(growShrinkAnimation);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        // Dynamic background animation
        animateBackground();

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

        // Set up the weather button click listener to navigate to the WeatherFragment
        Button weatherButton = binding.generateweatherbutton;
        Spinner citiesSpinner = binding.selectCitySpinner;

        weatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to WeatherFragment if a city is selected
                if (citiesSpinner.getAdapter().getCount() != 0) {
                    String selectedCity = citiesSpinner.getSelectedItem().toString();
                    HomeFragmentDirections.ActionHomeFragmentToWeatherFragment action = HomeFragmentDirections.actionHomeFragmentToWeatherFragment(selectedCity);
                    Navigation.findNavController(v).navigate(action);
                } else {
                    // Show error message if no cities are selected
                    Toast.makeText(requireContext(), "Add Cities To your List", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    /**
     * Handles state transitions for the weather icon animations.
     */
    private void handleStateTransition() {
        switch (currentImage) {
            case "cloudandsun":
                binding.weatherIconImage.setImageResource(R.drawable.perfectclouds);
                Log.d("StateTransition", "Transitioning to cloudtransition");
                currentImage = "cloudtransition";
                break;

            case "cloudtransition":
                Log.d("StateTransition", "Transitioning to thunder");
                currentImage = "thunder";
                break;

            case "thunder":
                binding.weatherIconImage.setImageResource(R.drawable.cloudandsun);
                Log.d("StateTransition", "Transitioning to rain");
                currentImage = "rain";
                break;
            case "rain":
                binding.weatherIconImage.setImageResource(R.drawable.cloudandsun);
                Log.d("StateTransition", "Transitioning to cloudandsun");
                currentImage = "cloudandsun";
        }
    }

    /**
     * Animates the background gradient colors.
     */
    private void animateBackground() {
        GradientDrawable gradientDrawable = (GradientDrawable) binding.getRoot().getBackground();

        int[] colors = {
                Color.parseColor("#B3E0D6"), // Light blue
                Color.parseColor("#FFA07A"), // Light orange
                Color.parseColor("#FFD700"), // Golden yellow
                Color.parseColor("#87CEEB")  // Sky blue
        };

        ValueAnimator colorAnimator = ValueAnimator.ofFloat(0, colors.length - 1);
        colorAnimator.setDuration(10000); // 5 seconds for the full cycle
        colorAnimator.setRepeatMode(ValueAnimator.REVERSE);
        colorAnimator.setRepeatCount(ValueAnimator.INFINITE);

        colorAnimator.addUpdateListener(animation -> {
            float animatedValue = (float) animation.getAnimatedValue();
            int startIndex = (int) Math.floor(animatedValue);
            int endIndex = (int) Math.ceil(animatedValue);
            float fraction = animatedValue - startIndex;

            int startColor = colors[startIndex];
            int endColor = colors[endIndex];
            int interpolatedColor = interpolateColor(startColor, endColor, fraction);

            gradientDrawable.setColors(new int[]{Color.WHITE, interpolatedColor});
        });

        colorAnimator.start();
    }

    /**
     * Interpolates between two colors based on a fraction.
     *
     * @param startColor The starting color.
     * @param endColor   The ending color.
     * @param fraction   The fraction to interpolate.
     * @return The interpolated color.
     */
    private int interpolateColor(int startColor, int endColor, float fraction) {
        int startAlpha = (startColor >> 24) & 0xff;
        int startRed = (startColor >> 16) & 0xff;
        int startGreen = (startColor >> 8) & 0xff;
        int startBlue = startColor & 0xff;

        int endAlpha = (endColor >> 24) & 0xff;
        int endRed = (endColor >> 16) & 0xff;
        int endGreen = (endColor >> 8) & 0xff;
        int endBlue = endColor & 0xff;

        int interpolatedAlpha = (int) (startAlpha + fraction * (endAlpha - startAlpha));
        int interpolatedRed = (int) (startRed + fraction * (endRed - startRed));
        int interpolatedGreen = (int) (startGreen + fraction * (endGreen - startGreen));
        int interpolatedBlue = (int) (startBlue + fraction * (endBlue - startBlue));

        return (interpolatedAlpha << 24) | (interpolatedRed << 16) | (interpolatedGreen << 8) | interpolatedBlue;
    }

    /**
     * Cleans up resources when the fragment's view is destroyed.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null
        ;
    }
}
