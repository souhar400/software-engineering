package de.gruppe.e.klingklang.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.slider.Slider;

import java.util.ArrayList;

import de.gruppe.e.klingklang.R;
import de.gruppe.e.klingklang.model.NamedLocation;
import de.gruppe.e.klingklang.view.TrackSelectionMenus.TrackSelectionMenu;
import de.gruppe.e.klingklang.viewmodel.FacadeMapView;
import de.gruppe.e.klingklang.viewmodel.FacadeViewModel;
import de.gruppe.e.klingklang.viewmodel.MainActivity;


public class MainMenu extends BottomSheetDialogFragment {
    private FacadeViewModel viewModel;
    private View view;
    private float gain = 0.2f;
    private final String FRAGMENT_TAG = "TRACKSELECTIONMENU_FRAGMENT_TAG";

    /**
     * Creates the dialog and calls methods to display it in fullscreen
     * @param savedInstanceState
     * @return The created dialog
     */
    @NonNull
    @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
            setupFullHeight(bottomSheetDialog);
        });
        return  dialog;
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (getActivity() != null &&
                getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).hideNavigationAndSwipeUpBar();
        }
    }



    /**
     * Sets the Behaviour of the BottomSheetDialog so it expands over the whole screen
     * @param bottomSheetDialog Dialog, which behaviour needs to be set
     */
    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        assert bottomSheet != null;
        BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

        int windowHeight = getWindowHeight();
        if (layoutParams != null) {
            layoutParams.height = windowHeight;
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    /**
     * Gets the height of the window
     * @return Window height as int
     */
    private int getWindowHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /**
     * Creates the SoundsMenu view that is displayed in the bottomSheetDialog
     * @param inflater factory to inflate the layout
     * @param container
     * @param savedInstanceState
     * @return The layout as view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_menu, container, false);
        this.viewModel = new ViewModelProvider(getActivity()).get(FacadeViewModel.class);
        createMainMenu();
        return view;
    }

    private void createMainMenu() {
        ImageButton settingsButton = view.findViewById(R.id.Einstellungen);
        ImageButton recordingsButton = view.findViewById(R.id.Aufnahmen);
        ImageButton taktButton = view.findViewById(R.id.Takt);
        ImageButton exitButton = view.findViewById(R.id.exitButton);
        ImageButton mapButton = view.findViewById(R.id.mapButton);
        exitButton.setOnClickListener(view -> dismiss());
        mapButton.setOnClickListener(view -> startActivity(createMapViewIntent()));

        recordingsButton.setOnClickListener(view -> {
            TrackSelectionMenu trackSelectionMenu = new TrackSelectionMenu(getActivity().getSupportFragmentManager(), this);
            trackSelectionMenu.show(getActivity().getSupportFragmentManager(), FRAGMENT_TAG);
        });

        Slider volumeSlider = view.findViewById(R.id.volumeSlider2);
        volumeSlider.setValue(gain* 100);
        volumeSlider.addOnChangeListener((slider, value, fromUser) -> {
            gain = value / 100;
            adjustGain(gain);
        });
    }

    private Intent createMapViewIntent() {
        Intent intent = new Intent(getContext(), FacadeMapView.class);
        NamedLocation facadeLocation = viewModel.getNamedLocation();
        ArrayList<NamedLocation> locationList = new ArrayList<>(viewModel.getAllLocations());
        intent.putParcelableArrayListExtra(getString(R.string.location_arraylist_parcelid), locationList);
        intent.putExtra(getString(R.string.location_latitude), facadeLocation.getLatitude());
        intent.putExtra(getString(R.string.location_longitude), facadeLocation.getLongitude());
        return intent;
    }

    private native void adjustGain(float gain);
}
