package de.gruppe.e.klingklang;

import android.app.Activity;
import android.app.Dialog;
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

public class soundMenu extends BottomSheetDialogFragment {
    private View v;
    private VolumeData vd;

    public soundMenu(VolumeData vd) {
        super();
        this.vd = vd;
    }

    @NonNull
    @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialogInterface -> {
            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
            setupFullHeight(bottomSheetDialog);
        });
        return  dialog;
    }


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

    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.sound_menu, container, false);

        createSoundMenu();
        return v;
    }


    private void createSoundMenu() {
        EditText title = v.findViewById(R.id.title);
        title.setText("Instrument A Tonleiter");

        Slider slider1 = v.findViewById(R.id.slider1);
        slider1.setValue(50);
        Slider slider2 = v.findViewById(R.id.slider2);
        slider2.setValue(50);
        Slider slider3 = v.findViewById(R.id.slider3);
        slider3.setValue(50);

        TextView slider1Name = v.findViewById(R.id.slider1Name);
        slider1Name.setText("slider1");
        TextView slider2Name = v.findViewById(R.id.slider2Name);
        slider2Name.setText("slider2");
        TextView slider3Name = v.findViewById(R.id.slider3Name);
        slider3Name.setText("slider3");

        TextView slider1Value = v.findViewById(R.id.slider1Value);
        slider1Value.setText("50");
        TextView slider2Value = v.findViewById(R.id.slider2Value);
        slider2Value.setText("50");
        TextView slider3Value = v.findViewById(R.id.slider3Value);
        slider3Value.setText("50");

        addSliderListener(slider1, slider1Value);
        addSliderListener(slider2, slider2Value);
        addSliderListener(slider3, slider3Value);

        TextView volumeName = v.findViewById(R.id.volumeName);
        volumeName.setText("Lautstärke");
        Slider volumeSlider = v.findViewById(R.id.volumeSlider);
        volumeSlider.setValue(vd.getVolume()*100);
        TextView volumeValue = v.findViewById(R.id.volumeValue);
        volumeValue.setText(vd.getString());

        addSliderListenerForVolume(volumeSlider);
        addSliderListener(volumeSlider, volumeValue);

        ImageButton close = v.findViewById(R.id.returnButton);
        close.setOnClickListener(view -> dismiss());
    }

    private void addSliderListener(Slider s, TextView v) {
        s.addOnChangeListener((slider, value, fromUser) -> {
            int slide_value = Math.round(value);
            v.setText(String.format(Locale.ENGLISH, "%d",slide_value));
        });
    }

    private void addSliderListenerForVolume(Slider s) {
        s.addOnChangeListener((slider, value, fromUser) -> {
            float slide_value = value / 100;
            vd.setVolume(slide_value);
        });
    }
}
