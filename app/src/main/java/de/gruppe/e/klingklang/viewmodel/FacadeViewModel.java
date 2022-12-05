package de.gruppe.e.klingklang.viewmodel;

import android.util.Log;
import android.widget.Button;

import androidx.fragment.app.FragmentManager;

import java.io.IOException;
import java.util.Map;

import de.gruppe.e.klingklang.R;
import de.gruppe.e.klingklang.model.ButtonData;
import de.gruppe.e.klingklang.model.FacadeData;
import de.gruppe.e.klingklang.model.FassadeModel;
import de.gruppe.e.klingklang.services.SynthService;
import de.gruppe.e.klingklang.view.ControlButtonsOverlayView;
import de.gruppe.e.klingklang.view.SoundMenu;

public class FacadeViewModel implements ViewModel{
    private FassadeModel fassadenModel;
    private FacadeData actualFassade ;
    private final SynthService synthService;
    private final String LOG_TAG = getClass().getSimpleName();
    private final String FRAGMENT_TAG = "SOUNDMENU_FRAGMENT_TAG";
    private final FragmentManager associatedManager;
    private final ControlButtonsOverlayView overlayView;

    public FacadeViewModel(ControlButtonsOverlayView controlButtonsOverlayView,
                           FassadeModel model,
                           SynthService synthService,
                           FragmentManager associatedManager){
        fassadenModel = model;
        actualFassade = model.getInitialFassade();
        this.synthService = synthService;
        this.associatedManager = associatedManager;
        this.overlayView = controlButtonsOverlayView;
        setButtonListener();
        controlButtonsOverlayView.setViewModel(this);
    }


    public void changeFassade ( ){
        actualFassade = actualFassade.getNextFassade();
        actualFassade.setOrientation();
        actualFassade.setContentView();
        this.overlayView.setListeners();
        actualFassade.initialisebuttons();
        setButtonListener();
        actualFassade.setInEditMode(false);
        overlayView.getEditButton().setImageResource( R.drawable.edit_mode );
    }

    private void setButtonListener() {
        for (Map.Entry<Button, ButtonData> entry : actualFassade.getButtons().entrySet())
            entry.getKey().setOnClickListener(view -> {
                if (actualFassade.getInEditMode()) {
                    SoundMenu smenu = new SoundMenu(entry.getValue());
                    smenu.show(associatedManager, FRAGMENT_TAG);
                } else {
                    synthService.play(entry.getValue());
                }
            });
    }

    public void toggleInEditMode() {
        actualFassade.toggleInEditMode();
    }

    public boolean getInEditMode() {
        return actualFassade.getInEditMode();
    }
}
