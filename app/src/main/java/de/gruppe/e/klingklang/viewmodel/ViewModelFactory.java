package de.gruppe.e.klingklang.viewmodel;

import android.widget.Button;

import androidx.fragment.app.FragmentManager;

import java.util.HashMap;

import de.gruppe.e.klingklang.R;
import de.gruppe.e.klingklang.model.ButtonData;
import de.gruppe.e.klingklang.model.FacadeData;
import de.gruppe.e.klingklang.services.SynthService;
import de.gruppe.e.klingklang.view.ControlButtonsOverlayView;

public class ViewModelFactory {
    private final MainActivity mainActivity;

    public ViewModelFactory(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public ViewModel createOldViewModel(ControlButtonsOverlayView controlButtonsOverlayView,
                                        SynthService synthService,
                                        FragmentManager manager) {
        HashMap<Button, ButtonData> oldViewButtons = initialiseOldViewButtons();
        return new FacadeViewModel(controlButtonsOverlayView,
                new FacadeData(),
                synthService,
                manager,
                oldViewButtons);
    }

    private HashMap<Button, ButtonData> initialiseOldViewButtons() {
        HashMap<Button, ButtonData> map = new HashMap<>();

        map.put((Button) mainActivity.findViewById(R.id.top_left1),
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(10)
                        .withVelocity(127).withPreset(0).withLoop(false).create());
        map.put((Button) mainActivity.findViewById(R.id.top_left2),
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(1).withLoop(false).create());
        map.put((Button) mainActivity.findViewById(R.id.bottom_left1),
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(10)
                        .withVelocity(127).withPreset(2).withLoop(true).create());
        map.put((Button) mainActivity.findViewById(R.id.bottom_left2),
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(3).withLoop(true).create());
        map.put((Button) mainActivity.findViewById(R.id.top_middle1),
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(5).withLoop(true).create());
        map.put((Button) mainActivity.findViewById(R.id.top_middle2),
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(5).withLoop(true).create());
        map.put((Button) mainActivity.findViewById(R.id.bottom_middle1),
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(6).withLoop(true).create());
        map.put((Button) mainActivity.findViewById(R.id.bottom_middle2),
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(7).withLoop(true).create());
        map.put((Button) mainActivity.findViewById(R.id.top_right1),
                new ButtonData.ButtonDataBuilder().withMidiPath("Piano - 1 - Lydisch.mid")
                        .withSoundfontPath("Piano.sf2").withLoop(true).create());
        map.put((Button) mainActivity.findViewById(R.id.top_right2),
                new ButtonData.ButtonDataBuilder().withMidiPath("Bass.mid")
                        .withSoundfontPath("Bass.sf2").withLoop(true).create());
        map.put((Button) mainActivity.findViewById(R.id.bottom_right1),
                new ButtonData.ButtonDataBuilder().withMidiPath("Beat.mid")
                        .withSoundfontPath("Drum.sf2").withLoop(true).create());
        map.put((Button) mainActivity.findViewById(R.id.bottom_right2),
                new ButtonData.ButtonDataBuilder().withMidiPath("Melodie.mid")
                        .withSoundfontPath("Saxophone.sf2").withLoop(true).create());

        return map;
    }
}
