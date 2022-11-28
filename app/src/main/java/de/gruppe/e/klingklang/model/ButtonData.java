package de.gruppe.e.klingklang.model;

import java.util.Observable;

// Temporary Storage Class for the Volume of a single Button
public class ButtonData {
    private int volume = 100;
    private final String soundfontPath;
    private final int channel;
    private final int key;
    private final int velocity;
    private final int preset;
    private final boolean toggle;

    public ButtonData(String soundfontPath, int channel, int key, int velocity, int preset, boolean toggle) {
        this.soundfontPath = soundfontPath;
        this.channel = channel;
        this.key = key;
        this.velocity = velocity;
        this.preset = preset;
        this.toggle = toggle;

    }

    public void setVolume(int v) {
        volume = v;
        setChannelVolume(channel, volume + 20);
    }

    public float getVolume() {
        return volume;
    }

    public String getString() {
        int ret = Math.round(volume);
        return Integer.toString(ret);
    }

    public String getSoundfontPath() {
        return soundfontPath;
    }

    public int getChannel() {
        return channel;
    }

    public int getKey() {
        return key;
    }

    public int getVelocity() {
        return velocity;
    }

    public int getPreset() {
        return preset;
    }

    public boolean isToggle() {
        return toggle;
    }

    private native void setChannelVolume(int channel, int volume);
}