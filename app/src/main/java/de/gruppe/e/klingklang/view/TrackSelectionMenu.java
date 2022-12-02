package de.gruppe.e.klingklang.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;
import java.util.List;

import de.gruppe.e.klingklang.R;
import de.gruppe.e.klingklang.viewmodel.MainActivity;

public class TrackSelectionMenu extends BottomSheetDialogFragment {

    private View view;

    public TrackSelectionMenu() {
        super();
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
        view = inflater.inflate(R.layout.recordings_menu, container, false);
        createSoundMenu();
        return view;
    }

    private void createSoundMenu() {
        EditText title = view.findViewById(R.id.title);
        title.setText("Aufnahmen");

        setButtons();

        ImageButton close = view.findViewById(R.id.returnButton);
        close.setOnClickListener(view -> dismiss());
    }

    @SuppressLint("RtlHardcoded")
    private void setButtons() {
        File[] tracks = MainActivity.recorder.getTracks();
        LinearLayout linearLayout = view.findViewById(R.id.LinearLayout);

        if (tracks.length == 0) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 30, 0, 0);
            Button button = new Button(view.getContext());
            button.setId(0);
            button.setLayoutParams(params);
            button.setText("Keine Aufnahmen vorhanden");
            button.setBackgroundColor(Color.LTGRAY);
            linearLayout.addView(button);
        }

        for (int i = 0; i < tracks.length; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1.0f;
            params.gravity = Gravity.LEFT;

            params.setMargins(0, 30, 0, 0);
            Button button = new Button(view.getContext());
            button.setId(i);
            button.setLayoutParams(params);


            String trackName = tracks[i].getName().substring(0, tracks[i].getName().indexOf("."));
            String trackLength = MainActivity.recorder.getTrackLength(tracks[i]);

            button.setText(String.format("%s\t\t\t-\t\t%s\t", trackName, trackLength));
            button.setBackgroundColor(Color.LTGRAY);

            int finalI = i;
            button.setOnClickListener(view -> {
                button.setBackgroundColor(Color.MAGENTA);
                MainActivity.recorder.playTrack(tracks[finalI]);
                button.setBackgroundColor(Color.LTGRAY);
            });

            linearLayout.addView(button);
        }

    }

}
