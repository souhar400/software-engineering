package de.gruppe.e.klingklang;

// Temporary Storage Class for the Volume of a single Button
public class VolumeData {
    private int volume = 100;

    public void setVolume(int v) {
        volume = v;
        setChannelVolume(0, volume + 20);
    }

    public float getVolume() {
        return volume;
    }

    public String getString() {
        int ret = Math.round(volume);
        return Integer.toString(ret);
    }

    private native void setChannelVolume(int channel, int volume);
}
