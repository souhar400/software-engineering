package de.gruppe.e.klingklang.view;

import android.widget.ImageButton;

import java.io.File;

import de.gruppe.e.klingklang.R;
import de.gruppe.e.klingklang.model.Recorder;
import de.gruppe.e.klingklang.viewmodel.FacadeViewModel;
import de.gruppe.e.klingklang.viewmodel.MainActivity;

public class ControlButtonsOverlayView {
    private static final String CONTROL_BUTTON_TAG = "control_button_overlay";
    private ImageButton editButton;
    private ImageButton menuButton;
    private ImageButton recordButton;
    private FacadeViewModel viewModel;

    public ControlButtonsOverlayView(ImageButton editButton,
                                     ImageButton menuButton,
                                     ImageButton recordButton,
                                     MainMenu mainMenu) {
        this.editButton = editButton;
        this.menuButton = menuButton;
        this.recordButton = recordButton;

        this.recordButton.setOnClickListener(view -> {
            if (Recorder.getInstance().isRecording()) {
                Recorder.getInstance().stopRecording();
                recordButton.setImageResource(R.drawable.start_recording);
            } else {
                recordButton.setImageResource(R.drawable.stop_recording);
                Recorder.getInstance().startRecording();
            }
        });

        this.menuButton.setOnClickListener(view -> {
            mainMenu.show(mainMenu.getAssociatedFragmentManager(), CONTROL_BUTTON_TAG);
        });
    }

    public void setViewModel(FacadeViewModel viewModel) {
        this.viewModel = viewModel;
        this.editButton.setOnClickListener(view -> {
            viewModel.toggleInEditMode();
            editButton.setImageResource(viewModel.getInEditMode() ? R.drawable.play_mode : R.drawable.edit_mode);
        });
    }
}
