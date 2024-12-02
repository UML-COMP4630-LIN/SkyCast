package com.example.skycast;
// CityAdapter.java
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private final List<City> cityList;
    private final CityViewModel cityViewModel;

    public CityAdapter(List<City> cityList, CityViewModel cityViewModel) {
        this.cityList = cityList;
        this.cityViewModel = cityViewModel;
        updateSelectedCitiesInViewModel();
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false);
        return new CityViewHolder(view);
    }

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

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    private void updateSelectedCitiesInViewModel() {
        List<City> selectedCities = new ArrayList<>();
        for (City city : cityList) {
            if (city.isChecked()) {
                selectedCities.add(city);
            }
        }

        cityViewModel.setSelectedCities(selectedCities);
    }

    public static class CityViewHolder extends RecyclerView.ViewHolder {
        TextView cityName;
        CheckBox cityCheckBox;

        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            cityName = itemView.findViewById(R.id.cityName);
            cityCheckBox = itemView.findViewById(R.id.citySelect);
        }
    }
}