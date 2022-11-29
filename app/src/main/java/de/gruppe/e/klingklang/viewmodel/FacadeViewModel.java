package de.gruppe.e.klingklang.viewmodel;

import android.util.Log;
import android.widget.Button;

import androidx.fragment.app.FragmentManager;

import java.io.IOException;
import java.util.Map;

import de.gruppe.e.klingklang.model.ButtonData;
import de.gruppe.e.klingklang.model.FacadeData;
import de.gruppe.e.klingklang.model.Recorder;
import de.gruppe.e.klingklang.services.SynthService;
import de.gruppe.e.klingklang.view.ControlButtonsOverlayView;
import de.gruppe.e.klingklang.view.SoundMenu;

public class FacadeViewModel implements ViewModel{
    private final FacadeData model;
    private final SynthService synthService;
    private final String LOG_TAG = getClass().getSimpleName();
    private final String FRAGMENT_TAG = "SOUNDMENU_FRAGMENT_TAG";
    private final FragmentManager associatedManager;
    private final Recorder recorder;

    public FacadeViewModel(ControlButtonsOverlayView controlButtonsOverlayView,
                           FacadeData model,
                           SynthService synthService,
                           FragmentManager associatedManager,
                           Map<Button, ButtonData> facadeButtons,
                           Recorder recorder){
        this.model = model;
        this.synthService = synthService;
        this.associatedManager = associatedManager;
        this.model.buttonDataList = facadeButtons;
        this.recorder = recorder;
        setButtonListener();
        controlButtonsOverlayView.setViewModel(this);
    }

    private void setButtonListener() {
        for (Map.Entry<Button, ButtonData> entry : model.buttonDataList.entrySet())
            entry.getKey().setOnClickListener(view -> {
                if (model.getInEditMode()) {
                    SoundMenu smenu = new SoundMenu(entry.getValue());
                    smenu.show(associatedManager, FRAGMENT_TAG);
                } else {
                    try {
                        String tempSoundfontPath = synthService.copyAssetToTmpFile(entry.getValue().getSoundfontPath());
                        synthService.playFluidSynthSound(tempSoundfontPath, entry.getValue().getChannel(), entry.getValue().getKey(), entry.getValue().getVelocity(), entry.getValue().getPreset(), entry.getValue().isToggle());
                        recorder.addTrackComponent(tempSoundfontPath, entry.getValue().getChannel(), entry.getValue().getKey(), entry.getValue().getVelocity(), entry.getValue().getPreset(), entry.getValue().isToggle());
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "Failed to play synthesizer sound");
                        throw new RuntimeException(e);
                    }
                }
            });
    }

    public void toggleInEditMode() {
        model.toggleInEditMode();
    }

    public boolean getInEditMode() {
        return model.getInEditMode();
    }
}
