package com.example.skycast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class CityViewModel extends ViewModel {
    private final MutableLiveData<List<City>> selectedCities = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<City>> getSelectedCities() {
        return selectedCities;
    }

    public void setSelectedCities(List<City> cities) {
        selectedCities.setValue(cities);
    }


}
