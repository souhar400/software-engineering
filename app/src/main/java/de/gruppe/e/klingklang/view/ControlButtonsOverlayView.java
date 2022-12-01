package de.gruppe.e.klingklang.view;

import android.widget.ImageButton;

import de.gruppe.e.klingklang.R;
import de.gruppe.e.klingklang.viewmodel.FacadeViewModel;

public class ControlButtonsOverlayView {
    private static final String CONTROL_BUTTON_TAG = "control_button_overlay";
    private ImageButton editButton;
    private ImageButton menuButton;
    private ImageButton changeFassadeButton;
    private FacadeViewModel viewModel;



    public ControlButtonsOverlayView(ImageButton editButton,
                                     ImageButton menuButton, ImageButton changeFassadeButton,
                                     MainMenu mainMenu) {
        this.editButton = editButton;
        this.menuButton = menuButton;
        this.changeFassadeButton = changeFassadeButton;
        this.menuButton.setOnClickListener(view -> {
            mainMenu.show(mainMenu.getAssociatedFragmentManager(), CONTROL_BUTTON_TAG);
        });

        this.changeFassadeButton.setOnClickListener(view -> {
            viewModel.changeFassade();
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
