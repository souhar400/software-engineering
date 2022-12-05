package de.gruppe.e.klingklang.view;

import android.widget.ImageButton;

import de.gruppe.e.klingklang.viewmodel.MainActivity;
import de.gruppe.e.klingklang.viewmodel.ViewModel;

public class ControlButtonsOverlayView {

    private ImageButton editButton;
    private ImageButton menuButton;
    private ImageButton changeFassadeButton;
    private MainActivity activity;
    private MainMenu mainMenu;
    private ViewModel viewModel;
    public ControlButtonsOverlayView(MainActivity activity, ViewModel viewModel) {
        this.activity = activity;
        this.viewModel = viewModel;
        //setListeners();
    }


}
