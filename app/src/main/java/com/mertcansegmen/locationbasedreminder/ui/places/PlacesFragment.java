package com.mertcansegmen.locationbasedreminder.ui.places;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mertcansegmen.locationbasedreminder.R;
import com.mertcansegmen.locationbasedreminder.model.Place;
import com.mertcansegmen.locationbasedreminder.ui.addeditplace.AddEditPlaceFragment;
import com.mertcansegmen.locationbasedreminder.util.Animator;
import com.mertcansegmen.locationbasedreminder.util.PlaceChip;

public class PlacesFragment extends Fragment {

    private LinearLayout emptyMessageLayout;
    private FloatingActionButton addPlaceButton;
    private ChipGroup chipGroup;

    private PlacesFragmentViewModel viewModel;

    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_places, container, false);
        setHasOptionsMenu(true);

        addPlaceButton = view.findViewById(R.id.btn_add_place);
        chipGroup = view.findViewById(R.id.chip_group);
        emptyMessageLayout = view.findViewById(R.id.empty_msg_layout);

        viewModel = ViewModelProviders.of(this).get(PlacesFragmentViewModel.class);

        Animator.animateFloatingActionButton(addPlaceButton);

        viewModel.getAllPlaces().observe(this, places -> {
            emptyMessageLayout.setVisibility(places.isEmpty() ? View.VISIBLE : View.GONE);

            chipGroup.removeAllViews();
            for(final Place place : places) {
                PlaceChip chip = new PlaceChip(view.getContext());
                chip.setPlace(place);
                chip.setText(place.getName());
                chip.setChipIcon(getResources().getDrawable(R.drawable.ic_places));
                chipGroup.addView(chip);
                chip.setOnClickListener(v -> navigateForEdit(place));
            }
        });

        addPlaceButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_placesFragment_to_addEditPlaceFragment);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
    }

    private void navigateForEdit(Place place) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AddEditPlaceFragment.BUNDLE_KEY_PLACE, place);
        navController.navigate(R.id.action_placesFragment_to_addEditPlaceFragment, bundle);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.places_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_places:
                new MaterialAlertDialogBuilder(requireContext())
                        .setMessage(getString(R.string.question_delete_all_places))
                        .setPositiveButton(getText(R.string.ok), (dialog, which) -> {
                            viewModel.deleteAll();
                        })
                        .setNegativeButton(getString(R.string.cancel), null)
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
