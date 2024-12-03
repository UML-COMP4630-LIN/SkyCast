package com.example.skycast;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * HelpFragment is responsible for displaying the help or support section of the app.
 * It inflates the corresponding layout for the help fragment.
 */
public class HelpFragment extends Fragment {

    /**
     * Called to create and return the view hierarchy for the fragment.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          The parent ViewGroup that the fragment's UI will be attached to.
     * @param savedInstanceState A Bundle containing the saved state of the fragment, if any.
     * @return The root view of the help fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false);
    }
}
