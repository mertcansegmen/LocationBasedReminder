package com.mertcansegmen.locationbasedreminder.ui.places;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mertcansegmen.locationbasedreminder.R;
import com.mertcansegmen.locationbasedreminder.model.Place;
import com.mertcansegmen.locationbasedreminder.ui.addeditplace.AddEditPlaceFragment;
import com.mertcansegmen.locationbasedreminder.util.Animator;
import com.mertcansegmen.locationbasedreminder.util.PlaceChip;

import java.util.List;

public class PlacesFragment extends Fragment {

    private FloatingActionButton addPlaceButton;
    private ChipGroup chipGroup;

    private PlacesFragmentViewModel viewModel;

    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_places, container, false);

        addPlaceButton = view.findViewById(R.id.btn_add_place);
        chipGroup = view.findViewById(R.id.chip_group);

        viewModel = ViewModelProviders.of(this).get(PlacesFragmentViewModel.class);

        Animator.animateFloatingActionButton(addPlaceButton);

        viewModel.getAllPlaces().observe(this, new Observer<List<Place>>() {
            @Override
            public void onChanged(List<Place> places) {
                chipGroup.removeAllViews();
                for(final Place place : places) {
                    PlaceChip chip = new PlaceChip(view.getContext());
                    chip.setPlace(place);
                    chip.setText(place.getName());
                    chip.setChipIcon(getResources().getDrawable(R.drawable.ic_places));
                    chipGroup.addView(chip);
                    chip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            navigateForEdit(place);
                        }
                    });
                }
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        addPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_placesFragment_to_addEditPlaceFragment);
            }
        });
    }

    private void navigateForEdit(Place place) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AddEditPlaceFragment.PLACE_BUNDLE_KEY, place);
        navController.navigate(R.id.action_placesFragment_to_addEditPlaceFragment, bundle);
    }
}