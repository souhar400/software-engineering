package de.gruppe.e.klingklang.view;

import android.widget.ImageButton;

import de.gruppe.e.klingklang.R;
import de.gruppe.e.klingklang.viewmodel.FacadeViewModel;
import de.gruppe.e.klingklang.viewmodel.MainActivity;

public class ControlButtonsOverlayView {
    private static final String CONTROL_BUTTON_TAG = "control_button_overlay";
    private ImageButton editButton;
    private ImageButton menuButton;
    private ImageButton changeFassadeButton;
    private MainActivity activity;
    private MainMenu mainMenu;
    private FacadeViewModel viewModel;



    public ControlButtonsOverlayView(MainActivity activity, MainMenu mainMenu) {
        this.activity = activity;
        this.mainMenu = mainMenu;
    }

    public void setViewModel(FacadeViewModel viewModel) {
        this.viewModel = viewModel;
        setListeners();
    }

    public void setListeners(){

        this.editButton=  activity.findViewById(R.id.edit_button);
        this.menuButton =activity.findViewById(R.id.setting_button);
        this.changeFassadeButton = activity.findViewById(R.id.change_fassade);

        this.editButton.setOnClickListener(view -> {
            viewModel.toggleInEditMode();
            editButton.setImageResource(viewModel.getInEditMode() ? R.drawable.play_mode : R.drawable.edit_mode);
        });
        this.changeFassadeButton.setOnClickListener(view -> {
            viewModel.changeFassade();
        });
        this.menuButton.setOnClickListener(view -> {
            this.mainMenu.show(this.mainMenu.getAssociatedFragmentManager(), CONTROL_BUTTON_TAG);
        });
    }
}
