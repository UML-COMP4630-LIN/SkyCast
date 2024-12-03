package com.example.skycast;

/**
 * Represents a city with a name and a checked status, which indicates whether
 * the city is selected or not.
 */
public class City {
    private String name;
    private boolean isChecked;

    /**
     * Constructor to create a new City object.
     *
     * @param name The name of the city.
     */
    public City(String name) {
        this.name = name;
        this.isChecked = false;
    }

    /**
     * Gets the name of the city.
     *
     * @return The name of the city.
     */
    public String getName() {
        return name;
    }

    /**
     * Checks if the city is marked as selected.
     *
     * @return True if the city is selected, false otherwise.
     */
    public boolean isChecked() {
        return isChecked;
    }

    /**
     * Sets the checked status of the city.
     *
     * @param checked True to mark the city as selected, false to deselect it.
     */
    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
