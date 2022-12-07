package de.gruppe.e.klingklang.viewmodel;

import android.content.pm.ActivityInfo;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

import de.gruppe.e.klingklang.R;
import de.gruppe.e.klingklang.model.ButtonData;
import de.gruppe.e.klingklang.model.FacadeData;

public class ViewModelFactory {
    private final MainActivity mainActivity;

    public ViewModelFactory(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public FacadeData createFacadeOne() {
        return new FacadeData(mainActivity, R.layout.activity_main,
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE, initialiseFacadeOneButtons());
    }

    public FacadeData createFacadeTwo() {
        return new FacadeData(mainActivity, R.layout.fassade_2,
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT, initialiseFacadeTwoButtons());
    }

    public FacadeData createFacadeThree() {
        return new FacadeData(mainActivity, R.layout.fassade_3,
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT, initialiseFacadeThreeButtons());
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

    private Map<Integer, ButtonData> initialiseFacadeOneButtons() {
        HashMap<Integer, ButtonData> map = new HashMap<>();
        map.put(R.id.button1,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(10)
                        .withVelocity(127).withPreset(0).withLoop(false).create());
        map.put(R.id.button2,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(1).withLoop(false).create());
        map.put(R.id.button3,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(10)
                        .withVelocity(127).withPreset(2).withLoop(true).create());
        map.put(R.id.button4,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(3).withLoop(true).create());
        map.put(R.id.button5,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(5).withLoop(true).create());
        map.put(R.id.button6,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(5).withLoop(true).create());
        map.put(R.id.button7,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(6).withLoop(true).create());
        map.put(R.id.button8,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(7).withLoop(true).create());
        map.put(R.id.button9,
                new ButtonData.ButtonDataBuilder().withMidiPath("Piano - 1 - Lydisch.mid")
                        .withSoundfontPath("Piano.sf2").withLoop(true).create());
        map.put(R.id.button10,
                new ButtonData.ButtonDataBuilder().withMidiPath("Piano - 2 - Ionisch.mid")
                        .withSoundfontPath("Piano.sf2").withLoop(true).create());
        return map;
    }

    public Map<Integer, ButtonData> initialiseFacadeTwoButtons() {
        HashMap<Integer, ButtonData> map = new HashMap<>();
        map.put(R.id.button1,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(10)
                        .withVelocity(127).withPreset(0).withLoop(false).create());
        map.put(R.id.button2,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(1).withLoop(false).create());
        map.put(R.id.button3,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(10)
                        .withVelocity(127).withPreset(2).withLoop(true).create());
        map.put(R.id.button4,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(3).withLoop(true).create());
        map.put(R.id.button5,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(5).withLoop(true).create());
        map.put(R.id.button6,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(5).withLoop(true).create());
        map.put(R.id.button7,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(6).withLoop(true).create());
        map.put(R.id.button8,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(7).withLoop(true).create());
        map.put(R.id.button9,
                new ButtonData.ButtonDataBuilder().withMidiPath("Piano - 1 - Lydisch.mid")
                        .withSoundfontPath("Piano.sf2").withLoop(true).create());
        map.put(R.id.button10,
                new ButtonData.ButtonDataBuilder().withMidiPath("Piano - 2 - Ionisch.mid")
                        .withSoundfontPath("Piano.sf2").withLoop(true).create());
        return map;
    }

    public Map<Integer, ButtonData> initialiseFacadeThreeButtons() {
        HashMap<Integer, ButtonData> map = new HashMap<>();
        map.put(R.id.button1,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(10)
                        .withVelocity(127).withPreset(0).withLoop(false).create());
        map.put(R.id.button2,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(1).withLoop(false).create());
        map.put(R.id.button3,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(10)
                        .withVelocity(127).withPreset(2).withLoop(true).create());
        map.put(R.id.button4,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(3).withLoop(true).create());
        map.put(R.id.button5,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(5).withLoop(true).create());
        map.put(R.id.button6,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(5).withLoop(true).create());
        map.put(R.id.button7,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(6).withLoop(true).create());
        map.put(R.id.button8,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(7).withLoop(true).create());
        map.put(R.id.button9,
                new ButtonData.ButtonDataBuilder().withMidiPath("Piano - 1 - Lydisch.mid")
                        .withSoundfontPath("Piano.sf2").withLoop(true).create());
        return map;
    }
}
