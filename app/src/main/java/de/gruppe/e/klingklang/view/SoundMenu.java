package de.gruppe.e.klingklang.view;

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
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.slider.Slider;

import de.gruppe.e.klingklang.R;
import de.gruppe.e.klingklang.model.ButtonData;
import de.gruppe.e.klingklang.viewmodel.MainActivity;

public class SoundMenu extends BottomSheetDialogFragment {
    private View view;
    private final ButtonData buttonData;
    private FileSelectionMenu fileSelectionMenu;
    private final FragmentManager associatedManager;

    public SoundMenu(ButtonData vd, FragmentManager associatedManager) {
        super();
        this.buttonData = vd;
        this.associatedManager = associatedManager;
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
        slider1Name.setText("Reverb");
        TextView slider2Name = view.findViewById(R.id.slider2Name);
        slider2Name.setText("Pitch-Up");
        TextView slider3Name = view.findViewById(R.id.slider3Name);
        slider3Name.setText("Pitch-Down");

        Slider volumeSlider = view.findViewById(R.id.volumeSlider);
        volumeSlider.setValue(buttonData.getVolume());

        addSliderListenerForVolume(volumeSlider);

        ImageButton close = view.findViewById(R.id.returnButton);
        close.setOnClickListener(view -> dismiss());

        SwitchCompat hide = view.findViewById(R.id.switchHideButton);
        hide.setChecked(buttonData.getVisibility());
        hide.setOnClickListener(e -> buttonData.setVisibility());

        SwitchCompat loop = view.findViewById(R.id.playLoopButton);
        loop.setChecked(buttonData.isToggle());
        loop.setOnClickListener(e -> buttonData.setToggle(!buttonData.isToggle()));

        TextView files = view.findViewById(R.id.files);
        files.setOnClickListener(e -> {
            fileSelectionMenu = new FileSelectionMenu(buttonData);
            fileSelectionMenu.show(associatedManager, null);
        });
    }

    private void addSliderListenerForVolume(Slider s) {
        s.addOnChangeListener((slider, value, fromUser) -> {
            int slide_value = Math.round(value);
            buttonData.setVolume(slide_value);
        });
    }

    public FragmentManager getAssociatedFragmentManager() {
        return associatedManager;
    }

}
