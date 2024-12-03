package com.example.skycast;

// CityAdapter.java
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * CityAdapter is responsible for displaying a list of cities in a RecyclerView.
 * It manages the interaction between the city list and the CityViewModel.
 */
public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private final List<City> cityList;
    private final CityViewModel cityViewModel;

    /**
     * Constructor for CityAdapter.
     *
     * @param cityList      The list of cities to display.
     * @param cityViewModel The ViewModel to update the selected cities.
     */
    public CityAdapter(List<City> cityList, CityViewModel cityViewModel) {
        this.cityList = cityList;
        this.cityViewModel = cityViewModel;
        updateSelectedCitiesInViewModel();
    }

    /**
     * Creates and returns a ViewHolder for a city item.
     *
     * @param parent   The parent ViewGroup into which the new view will be added.
     * @param viewType The view type of the new view.
     * @return A CityViewHolder instance.
     */
    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);
        return new CityViewHolder(view);
    }

    /**
     * Binds data to the ViewHolder for a specific position.
     *
     * @param holder   The CityViewHolder to bind data to.
     * @param position The position of the item in the dataset.
     */
    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        City city = cityList.get(position);
        holder.cityName.setText(city.getName());
        holder.cityCheckBox.setChecked(city.isChecked());

        holder.cityCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            city.setChecked(isChecked);
            updateSelectedCitiesInViewModel();
        });
    }

    /**
     * Returns the total number of items in the dataset.
     *
     * @return The size of the city list.
     */
    @Override
    public int getItemCount() {
        return cityList.size();
    }

    /**
     * Updates the selected cities in the CityViewModel.
     */
    private void updateSelectedCitiesInViewModel() {
        List<City> selectedCities = new ArrayList<>();
        for (City city : cityList) {
            if (city.isChecked()) {
                selectedCities.add(city);
            }
        }

        cityViewModel.setSelectedCities(selectedCities);
    }

    /**
     * ViewHolder class for city items in the RecyclerView.
     */
    public static class CityViewHolder extends RecyclerView.ViewHolder {
        TextView cityName;
        CheckBox cityCheckBox;

        /**
         * Constructor for CityViewHolder.
         *
         * @param itemView The view of the city item.
         */
        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.cityName);
            cityCheckBox = itemView.findViewById(R.id.citySelect);
        }
    }
}
