package de.gruppe.e.klingklang.model;

import java.util.Observable;

// Temporary Storage Class for the Volume of a single Button
public class ButtonData {
    private int volume = 127;
    private int displayedVolume = 100;
    private final String soundfontPath;
    private final int channel;
    private final int key;
    private final int velocity;
    private final int preset;
    private final boolean toggle;
    private boolean visibility = true;

    public ButtonData(String soundfontPath, int channel, int key, int velocity, int preset, boolean toggle) {
        this.soundfontPath = soundfontPath;
        this.channel = channel;
        this.key = key;
        this.velocity = velocity;
        this.preset = preset;
        this.toggle = toggle;

    }

    public void setVolume(int volume) {
        displayedVolume = volume;
        this.volume = Math.round(1.27f * volume);
        setChannelVolume(channel, volume);
    }

    public float getVolume() {
        return displayedVolume;
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

    public void toggleVisibility() {
        visibility = !visibility;
    }

    public boolean getVisibility(){
        return visibility;
    }

    private native void setChannelVolume(int channel, int volume);
}
