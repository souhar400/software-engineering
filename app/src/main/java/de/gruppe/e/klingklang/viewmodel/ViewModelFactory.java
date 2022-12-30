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
        NamedLocation location = new NamedLocation(52.14320021318558, 7.321871073980781, 200,
                "E Gebäude - Fachhochschule Münster/Steinfurt, Stegerwaldstraße 39, 48565 Steinfurt",
                "FH Münster E-Gebäude");
        return new FacadeData(R.layout.activity_main,
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE, initialiseFacadeOneButtons(), location);
    }

    public FacadeData createFacadeTwo() {
        NamedLocation location = new NamedLocation(51.960410172881076, 7.63458886626483, 200,
                "Iduna Hochhaus, Servatiipl. 9, 48143 Münster",
                "Iduna-Hochhaus Münster");
        return new FacadeData(R.layout.fassade_2,
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT, initialiseFacadeTwoButtons(), location);
    }

    public FacadeData createFacadeThree() {
        NamedLocation location = new NamedLocation(51.96165337208738, 7.6279894593240405, 200,
                "Prinzipalmarkt 10, 48143 Münster",
                "Historisches Rathaus Münster");
        return new FacadeData(R.layout.fassade_3,
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT, initialiseFacadeThreeButtons(), location);
    }

    private Map<Integer, ButtonData> initialiseFacadeOneButtons() {
        HashMap<Integer, ButtonData> map = new HashMap<>();
        map.put(R.id.button1_sound,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(10)
                        .withVelocity(127).withPreset(0).withLoop(false).withReverbButton(R.id.button1_hall).create());
        map.put(R.id.button2_sound,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(1).withLoop(false).withReverbButton(R.id.button2_hall).create());
        map.put(R.id.button3_sound,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(10)
                        .withVelocity(127).withPreset(2).withLoop(true).withReverbButton(R.id.button3_hall).create());
        map.put(R.id.button4_sound,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(3).withLoop(true).withReverbButton(R.id.button4_hall).create());
        map.put(R.id.button5_sound,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(5).withLoop(true).withReverbButton(R.id.button5_hall).create());
        map.put(R.id.button6_sound,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(5).withLoop(true).withReverbButton(R.id.button6_hall).create());
        map.put(R.id.button7_sound,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(6).withLoop(true).withReverbButton(R.id.button7_hall).create());
        map.put(R.id.button8_sound,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(7).withLoop(true).withReverbButton(R.id.button8_hall).create());
        map.put(R.id.button9_sound,
                new ButtonData.ButtonDataBuilder().withMidiPath("Piano - 1 - Lydisch.mid")
                        .withSoundfontPath("Piano.sf2").withLoop(true).withReverbButton(R.id.button9_hall).create());
        map.put(R.id.button10_sound,
                new ButtonData.ButtonDataBuilder().withMidiPath("Piano - 2 - Ionisch.mid")
                        .withSoundfontPath("Piano.sf2").withLoop(true).withReverbButton(R.id.button10_hall).create());
        return map;
    }

    public Map<Integer, ButtonData> initialiseFacadeTwoButtons() {
        HashMap<Integer, ButtonData> map = new HashMap<>();
        map.put(R.id.button1_sound,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(10)
                        .withVelocity(127).withPreset(0).withLoop(false).withReverbButton(R.id.button1_hall).create());
        map.put(R.id.button2_sound,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(1).withLoop(false).withReverbButton(R.id.button2_hall).create());
        map.put(R.id.button3_sound,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(10)
                        .withVelocity(127).withPreset(2).withLoop(true).withReverbButton(R.id.button3_hall).create());
        map.put(R.id.button4_sound,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(3).withLoop(true).withReverbButton(R.id.button4_hall).create());
        map.put(R.id.button5_sound,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(5).withLoop(true).withReverbButton(R.id.button5_hall).create());
        map.put(R.id.button6_sound,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(5).withLoop(true).withReverbButton(R.id.button6_hall).create());
        map.put(R.id.button7_sound,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(6).withLoop(true).withReverbButton(R.id.button7_hall).create());
        map.put(R.id.button8_sound,
                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
                        .withVelocity(127).withPreset(7).withLoop(true).withReverbButton(R.id.button8_hall).create());
        map.put(R.id.button9_sound,
                new ButtonData.ButtonDataBuilder().withMidiPath("Piano - 1 - Lydisch.mid")
                        .withSoundfontPath("Piano.sf2").withLoop(true).withReverbButton(R.id.button9_hall).create());
        map.put(R.id.button10_sound,
                new ButtonData.ButtonDataBuilder().withMidiPath("Piano - 2 - Ionisch.mid")
                        .withSoundfontPath("Piano.sf2").withLoop(true).withReverbButton(R.id.button10_hall).create());
        return map;
    }

    public Map<Integer, ButtonData> initialiseFacadeThreeButtons() {
        HashMap<Integer, ButtonData> map = new HashMap<>();
//        map.put(R.id.button1,
//                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(10)
//                        .withVelocity(127).withPreset(0).withLoop(false).create());
//        map.put(R.id.button2,
//                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
//                        .withVelocity(127).withPreset(1).withLoop(false).create());
//        map.put(R.id.button3,
//                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(10)
//                        .withVelocity(127).withPreset(2).withLoop(true).create());
//        map.put(R.id.button4,
//                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
//                        .withVelocity(127).withPreset(3).withLoop(true).create());
//        map.put(R.id.button5,
//                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
//                        .withVelocity(127).withPreset(5).withLoop(true).create());
//        map.put(R.id.button6,
//                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
//                        .withVelocity(127).withPreset(5).withLoop(true).create());
//        map.put(R.id.button7,
//                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
//                        .withVelocity(127).withPreset(6).withLoop(true).create());
//        map.put(R.id.button8,
//                new ButtonData.ButtonDataBuilder().withSoundfontPath("klingklang.sf2").withKey(62)
//                        .withVelocity(127).withPreset(7).withLoop(true).create());
//        map.put(R.id.button9,
//                new ButtonData.ButtonDataBuilder().withMidiPath("Piano - 1 - Lydisch.mid")
//                        .withSoundfontPath("Piano.sf2").withLoop(true).create());
        return map;
    }
}
