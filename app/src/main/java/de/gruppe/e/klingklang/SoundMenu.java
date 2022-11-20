package de.gruppe.e.klingklang;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.slider.Slider;

import java.util.Locale;

public class SoundMenu extends BottomSheetDialogFragment {
    private View view;
    private final ButtonData buttonData;

    public SoundMenu(ButtonData vd) {
        super();
        this.buttonData = vd;
    }

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
        view = inflater.inflate(R.layout.sound_menu, container, false);

        createSoundMenu();
        return view;
    }

    private void createSoundMenu() {
        EditText title = view.findViewById(R.id.title);
        title.setText("Instrument A Tonleiter");

        Slider slider1 = view.findViewById(R.id.slider1);
        slider1.setValue(50);
        Slider slider2 = view.findViewById(R.id.slider2);
        slider2.setValue(50);
        Slider slider3 = view.findViewById(R.id.slider3);
        slider3.setValue(50);

        TextView slider1Name = view.findViewById(R.id.slider1Name);
        slider1Name.setText("slider1");
        TextView slider2Name = view.findViewById(R.id.slider2Name);
        slider2Name.setText("slider2");
        TextView slider3Name = view.findViewById(R.id.slider3Name);
        slider3Name.setText("slider3");

        TextView slider1Value = view.findViewById(R.id.slider1Value);
        slider1Value.setText("50");
        TextView slider2Value = view.findViewById(R.id.slider2Value);
        slider2Value.setText("50");
        TextView slider3Value = view.findViewById(R.id.slider3Value);
        slider3Value.setText("50");

        addSliderListener(slider1, slider1Value);
        addSliderListener(slider2, slider2Value);
        addSliderListener(slider3, slider3Value);

        TextView volumeName = view.findViewById(R.id.volumeName);
        volumeName.setText("Lautstärke");
        Slider volumeSlider = view.findViewById(R.id.volumeSlider);
        volumeSlider.setValue(buttonData.getVolume());
        TextView volumeValue = view.findViewById(R.id.volumeValue);
        volumeValue.setText(buttonData.getString());

        addSliderListenerForVolume(volumeSlider);
        addSliderListener(volumeSlider, volumeValue);

        ImageButton close = view.findViewById(R.id.returnButton);
        close.setOnClickListener(view -> dismiss());
    }

    private void addSliderListener(Slider s, TextView v) {
        s.addOnChangeListener((slider, value, fromUser) -> {
            v.setText(String.format(Locale.ENGLISH, "%d",Math.round(value)));
        });
    }

    private void addSliderListenerForVolume(Slider s) {
        s.addOnChangeListener((slider, value, fromUser) -> {
            int slide_value = Math.round(value);
            buttonData.setVolume(slide_value);
        });
    }
}
