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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.gruppe.e.klingklang.R;
import de.gruppe.e.klingklang.model.ButtonData;
import de.gruppe.e.klingklang.services.SynthService;
import de.gruppe.e.klingklang.viewmodel.MainActivity;

public class FileSelectionMenu extends BottomSheetDialogFragment{

    private View view;
    private ButtonData buttonData;
    private SynthService synthService;
    private final Map<String, String> fileNames = new HashMap<>();

    private Button[] buttons;
    public FileSelectionMenu(ButtonData buttonData, SynthService synthService) {
        this.buttonData = buttonData;
        this.synthService = synthService;

        fileNames.put("Melodie.mid", "Saxophone.sf2");
        fileNames.put("Bass.mid", "Bass.sf2");
        fileNames.put("Beat.mid", "Drum.sf2");
        fileNames.put("Piano - 1 - Lydisch.mid", "Piano.sf2");
        fileNames.put("Piano - 2 - Ionisch.mid", "Piano.sf2");
        fileNames.put("Piano - 3 - Mixolydisch", "Piano.sf2");
        fileNames.put("Piano - 4 - Dorisch.mid", "Piano.sf2");
        fileNames.put("Piano - 5 - Äolisch.mid", "Piano.sf2");
        fileNames.put("Piano - 6 - Phrygisch.mid", "Piano.sf2");
        fileNames.put("Piano - 7 - Lokrisch", "Piano.sf2");

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
        view = inflater.inflate(R.layout.file_selection_menu, container, false);
        createSelectionMenu();
        return view;
    }

    private void createSelectionMenu() {
        TextView title = view.findViewById(R.id.title);
        title.setText("Files");

        setButtons();

        ImageButton close = view.findViewById(R.id.returnButton);
        close.setOnClickListener(view -> dismiss());
    }

    @SuppressLint("RtlHardcoded")
    private void setButtons() {
        LinearLayout linearLayout = view.findViewById(R.id.LinearLayout);
        buttons = new Button[fileNames.size()];
        int id = 0;

        for (Map.Entry<String, String> entry : fileNames.entrySet()) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.weight = 1.0f;
            params.gravity = Gravity.LEFT;

            params.setMargins(0, 30, 0, 0);
            Button button = new Button(view.getContext());
            button.setId(id);
            button.setMaxHeight(50);
            button.setLayoutParams(params);

            button.setText(entry.getKey());
            button.setBackgroundColor(Color.LTGRAY);
            if(entry.getKey().equals(buttonData.getMidiPath())){
                button.setBackgroundColor(getResources().getColor(R.color.teal_200));
            }

            buttons[id] = button;
            button.setOnClickListener(view -> {
                buttonData.setMidiPath(button.getText().toString());
                buttonData.setSoundfontPath(fileNames.get(button.getText().toString()));
                synthService.register(buttonData);
                for (Button value : buttons) {
                    value.setBackgroundColor(Color.LTGRAY);
                }
                button.setBackgroundColor(getResources().getColor(R.color.teal_200));
            });

            linearLayout.addView(button);
            id++;
        }

    }


}
