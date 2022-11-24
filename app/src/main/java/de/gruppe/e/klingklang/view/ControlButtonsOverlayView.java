package de.gruppe.e.klingklang.view;

import android.widget.ImageButton;

import de.gruppe.e.klingklang.R;
import de.gruppe.e.klingklang.viewmodel.FacadeViewModel;

public class ControlButtonsOverlayView {
    private static final String CONTROL_BUTTON_TAG = "control_button_overlay";
    private ImageButton editButton;
    private ImageButton menuButton;
    private FacadeViewModel viewModel;
    public ControlButtonsOverlayView(ImageButton editButton,
                                     ImageButton menuButton,
                                     MainMenu mainMenu) {
        this.editButton = editButton;
        this.menuButton = menuButton;
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
