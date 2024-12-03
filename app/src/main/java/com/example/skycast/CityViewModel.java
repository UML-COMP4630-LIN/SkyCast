package com.example.skycast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * CityViewModel serves as the shared ViewModel for managing the state of selected cities.
 * It holds a list of selected cities and provides live updates to observers.
 */
public class CityViewModel extends ViewModel {
    private final MutableLiveData<List<City>> selectedCities = new MutableLiveData<>(new ArrayList<>());

    /**
     * Returns a LiveData object that holds the list of selected cities.
     *
     * @return LiveData object containing the selected cities.
     */
    public LiveData<List<City>> getSelectedCities() {
        return selectedCities;
    }

    /**
     * Updates the list of selected cities.
     *
     * @param cities The new list of selected cities to be set.
     */
    public void setSelectedCities(List<City> cities) {
        selectedCities.setValue(cities);
    }
}
