package de.gruppe.e.klingklang.viewmodel;

import android.app.Activity;
import android.util.Log;

import androidx.fragment.app.FragmentManager;

import java.util.Map;

import de.gruppe.e.klingklang.R;
import de.gruppe.e.klingklang.model.ButtonData;
import de.gruppe.e.klingklang.model.FacadeData;
import de.gruppe.e.klingklang.model.FassadeModel;
import de.gruppe.e.klingklang.model.Recorder;
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
    private final Activity activity;
    public FacadeViewModel(ControlButtonsOverlayView controlButtonsOverlayView,
                           FassadeModel model,
                           SynthService synthService,
                           FragmentManager associatedManager,
                           Activity activity){
        fassadenModel = model;
        actualFassade = model.getInitialFassade();
        this.synthService = synthService;
        this.associatedManager = associatedManager;
        this.overlayView = controlButtonsOverlayView;
        this.activity = activity;
        setButtonListener();
        controlButtonsOverlayView.setViewModel(this);
    }


    public void changeFassade ( ){
        actualFassade = fassadenModel.getNextFacade();
        actualFassade.setOrientation();
        actualFassade.setContentView();
        this.overlayView.setListeners();
        setButtonListener();
        actualFassade.setInEditMode(false);
        overlayView.getEditButton().setImageResource( R.drawable.edit_mode );
    }

    private void setButtonListener() {
        Log.d(LOG_TAG, "Adding buttonlisteners to facade-buttons for facade: " + actualFassade);
        Log.d(LOG_TAG, "Iterating over " + actualFassade.getButtons().size() + " buttons.");
        for (Map.Entry<Integer, ButtonData> entry : actualFassade.getButtons().entrySet()) {
            Log.d(LOG_TAG, "Adding listener to button " + entry.getKey());
            activity.findViewById(entry.getKey()).setOnClickListener(view -> {
                Log.d(LOG_TAG, "Touchevent fired for: " + entry.getValue());
                if (actualFassade.getInEditMode()) {
                    SoundMenu smenu = new SoundMenu(entry.getValue(), associatedManager, synthService);
                    smenu.show(associatedManager, FRAGMENT_TAG);
                } else {
                    Log.d(LOG_TAG, "Playing sound: " + entry.getValue().getSoundfontPath());
                    synthService.play(entry.getValue());
                }
            });
        }
    }

    public void toggleInEditMode() {
        actualFassade.toggleInEditMode();
    }

    public boolean getInEditMode() {
        return actualFassade.getInEditMode();
    }
}
