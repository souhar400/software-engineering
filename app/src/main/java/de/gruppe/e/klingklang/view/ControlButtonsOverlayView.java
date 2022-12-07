package de.gruppe.e.klingklang.view;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import de.gruppe.e.klingklang.R;
import de.gruppe.e.klingklang.viewmodel.FacadeViewModel;
import de.gruppe.e.klingklang.viewmodel.MainActivity;
import de.gruppe.e.klingklang.viewmodel.ViewModel;

public class ControlButtonsOverlayView {
    private static final String CONTROL_BUTTON_TAG = "control_button_overlay";

    private ImageButton editButton;
    private ImageButton menuButton;
    private ImageButton changeFassadeButton;
    private MainActivity activity;
    private MainMenu mainMenu;
    private FacadeViewModel viewModel;
    private Button[] buttons;

    public ControlButtonsOverlayView(MainActivity activity, MainMenu mainMenu) {
        this.activity = activity;
        this.mainMenu = mainMenu;
    }

    public void setViewModel(FacadeViewModel viewModel) {
        this.viewModel = viewModel;
        setListeners();
    }

    public ImageButton getEditButton() {
        return editButton;
    }


    public void setListeners(){
        this.editButton=  activity.findViewById(R.id.edit_button);
        this.menuButton =activity.findViewById(R.id.setting_button);
        this.changeFassadeButton = activity.findViewById(R.id.change_fassade);
        this.buttons = new Button[]{activity.findViewById(R.id.button1),
                                    activity.findViewById(R.id.button2),
                                    activity.findViewById(R.id.button3),
                                    activity.findViewById(R.id.button4),
                                    activity.findViewById(R.id.button5),
                                    activity.findViewById(R.id.button6),
                                    activity.findViewById(R.id.button7),
                                    activity.findViewById(R.id.button8),
                                    activity.findViewById(R.id.button9),
                                    activity.findViewById(R.id.button10)};

        this.editButton.setOnClickListener(view -> {
            viewModel.toggleInEditMode();
            editButton.setImageResource(viewModel.getInEditMode() ? R.drawable.play_mode : R.drawable.edit_mode);
            if (viewModel.getInEditMode()) {
                viewModel.makeButtonsVisible(buttons);
            }
            else {
                viewModel.makeButtonsInvisible(buttons);
            }
        });
        this.changeFassadeButton.setOnClickListener(view -> {
            viewModel.changeFassade();
            viewModel.makeButtonsInvisible(buttons);
        });
        this.menuButton.setOnClickListener(view -> {
            this.mainMenu.show(this.mainMenu.getAssociatedFragmentManager(), CONTROL_BUTTON_TAG);
        });
    }
}
