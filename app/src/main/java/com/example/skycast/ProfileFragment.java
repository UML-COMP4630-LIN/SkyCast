package com.example.skycast;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.skycast.databinding.FragmentProfileBinding;

/**
 * ProfileFragment is responsible for displaying and managing the user's profile
 * information within the app.
 */
public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    /**
     * Called to create and return the view hierarchy for the fragment.
     *
     * @param inflater           The LayoutInflater object used to inflate views in the fragment.
     * @param container          The parent ViewGroup that the fragment's UI will be attached to.
     * @param savedInstanceState A Bundle containing the saved state of the fragment, if any.
     * @return The root view of the profile fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize ViewBinding
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Inflate the layout for this fragment
        return view;
    }
}
