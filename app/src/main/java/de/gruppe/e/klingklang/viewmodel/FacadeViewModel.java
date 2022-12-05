package de.gruppe.e.klingklang.viewmodel;

import android.widget.Button;

import androidx.fragment.app.FragmentManager;

import java.util.Map;

import de.gruppe.e.klingklang.model.ButtonData;
import de.gruppe.e.klingklang.model.FacadeData;
import de.gruppe.e.klingklang.model.NamedLocation;
import de.gruppe.e.klingklang.services.SynthService;
import de.gruppe.e.klingklang.view.ControlButtonsOverlayView;
import de.gruppe.e.klingklang.view.SoundMenu;

public class FacadeViewModel implements ViewModel{
    private final FacadeData model;
    private final SynthService synthService;
    private final String LOG_TAG = getClass().getSimpleName();
    private final String FRAGMENT_TAG = "SOUNDMENU_FRAGMENT_TAG";
    private final FragmentManager associatedManager;

    public FacadeViewModel(ControlButtonsOverlayView controlButtonsOverlayView,
                           FacadeData model,
                           SynthService synthService,
                           FragmentManager associatedManager){
        this.model = model;
        this.synthService = synthService;
        this.associatedManager = associatedManager;
        setButtonListener();
        controlButtonsOverlayView.setViewModel(this);
    }

    private void setButtonListener() {
        for (Map.Entry<Button, ButtonData> entry : model.getButtonDataList().entrySet())
            entry.getKey().setOnClickListener(view -> {
                if (model.getInEditMode()) {
                    SoundMenu smenu = new SoundMenu(entry.getValue());
                    smenu.show(associatedManager, FRAGMENT_TAG);
                } else {
                    synthService.play(entry.getValue());
                }
            });
    }

    public void toggleInEditMode() {
        model.toggleInEditMode();
    }

    public boolean getInEditMode() {
        return model.getInEditMode();
    }

    @Override
    public NamedLocation getNamedLocation() {
        return model.getNamedLocation();
    }
}
