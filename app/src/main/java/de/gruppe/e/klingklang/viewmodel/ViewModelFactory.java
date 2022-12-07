package de.gruppe.e.klingklang.viewmodel;

import android.content.pm.ActivityInfo;

import java.util.HashMap;
import java.util.Map;

import de.gruppe.e.klingklang.R;
import de.gruppe.e.klingklang.model.ButtonData;
import de.gruppe.e.klingklang.model.FacadeData;
import de.gruppe.e.klingklang.model.NamedLocation;

public class ViewModelFactory {
    public ViewModelFactory() {
    }

    public FacadeData createFacadeOne() {
        NamedLocation location = new NamedLocation(41.141, -8.614, 200,
                "R. de Mouzinho da Silveira 42",
                "Portugal, Porto");
        return new FacadeData(R.layout.activity_main,
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE, initialiseFacadeOneButtons(), location);
    }

    public FacadeData createFacadeTwo() {
        NamedLocation location = new NamedLocation(41.141, -8.614, 200,
                "R. de Mouzinho da Silveira 42",
                "Portugal, Porto");
        return new FacadeData(R.layout.fassade_2,
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT, initialiseFacadeTwoButtons(), location);
    }

    public FacadeData createFacadeThree() {
        NamedLocation location = new NamedLocation(41.141, -8.614, 200,
                "R. de Mouzinho da Silveira 42",
                "Portugal, Porto");
        return new FacadeData(R.layout.fassade_3,
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT, initialiseFacadeThreeButtons(), location);
    }

    private Map<Integer, ButtonData> initialiseFacadeOneButtons() {
        HashMap<Integer, ButtonData> map = new HashMap<>();
        map.put(R.id.button1,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(10)
                        .withVelocity(127).withPreset(0).withToggle(false).create());
        map.put(R.id.button2,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(1).withToggle(false).create());
        map.put(R.id.button3,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(10)
                        .withVelocity(127).withPreset(2).withToggle(true).create());
        map.put(R.id.button4,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(3).withToggle(true).create());
        map.put(R.id.button5,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(5).withToggle(true).create());
        map.put(R.id.button6,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(5).withToggle(true).create());
        map.put(R.id.button7,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(6).withToggle(true).create());
        map.put(R.id.button8,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(7).withToggle(true).create());
        map.put(R.id.button9,
                new ButtonData.ButtonDataBuilder().withMidiPath("Piano - 1 - Lydisch.mid")
                        .withSoundfontPath("Piano.sf2").withToggle(true).create());
        map.put(R.id.button10,
                new ButtonData.ButtonDataBuilder().withMidiPath("Piano - 2 - Ionisch.mid")
                        .withSoundfontPath("Piano.sf2").withToggle(true).create());
        return map;
    }

    public Map<Integer, ButtonData> initialiseFacadeTwoButtons() {
        HashMap<Integer, ButtonData> map = new HashMap<>();
        map.put(R.id.button1,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(10)
                        .withVelocity(127).withPreset(0).withToggle(false).create());
        map.put(R.id.button2,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(1).withToggle(false).create());
        map.put(R.id.button3,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(10)
                        .withVelocity(127).withPreset(2).withToggle(true).create());
        map.put(R.id.button4,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(3).withToggle(true).create());
        map.put(R.id.button5,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(5).withToggle(true).create());
        map.put(R.id.button6,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(5).withToggle(true).create());
        map.put(R.id.button7,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(6).withToggle(true).create());
        map.put(R.id.button8,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(7).withToggle(true).create());
        map.put(R.id.button9,
                new ButtonData.ButtonDataBuilder().withMidiPath("Piano - 1 - Lydisch.mid")
                        .withSoundfontPath("Piano.sf2").withToggle(true).create());
        map.put(R.id.button10,
                new ButtonData.ButtonDataBuilder().withMidiPath("Piano - 2 - Ionisch.mid")
                        .withSoundfontPath("Piano.sf2").withToggle(true).create());
        return map;
    }

    public Map<Integer, ButtonData> initialiseFacadeThreeButtons() {
        HashMap<Integer, ButtonData> map = new HashMap<>();
        map.put(R.id.button1,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(10)
                        .withVelocity(127).withPreset(0).withToggle(false).create());
        map.put(R.id.button2,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(1).withToggle(false).create());
        map.put(R.id.button3,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(10)
                        .withVelocity(127).withPreset(2).withToggle(true).create());
        map.put(R.id.button4,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(3).withToggle(true).create());
        map.put(R.id.button5,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(5).withToggle(true).create());
        map.put(R.id.button6,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(5).withToggle(true).create());
        map.put(R.id.button7,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(6).withToggle(true).create());
        map.put(R.id.button8,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(7).withToggle(true).create());
        map.put(R.id.button9,
                new ButtonData.ButtonDataBuilder().withMidiPath("Piano - 1 - Lydisch.mid")
                        .withSoundfontPath("Piano.sf2").withToggle(true).create());
        return map;
    }
}
