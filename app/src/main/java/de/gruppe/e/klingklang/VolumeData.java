package de.gruppe.e.klingklang;

// Temporary Storage Class for the Volume of a single Button
public class VolumeData {
    private float volume = 0.2f;

    public void setVolume(float v) {
        volume = v;
    }

    public float getVolume() {
        return volume;
    }

    public String getString() {
        int ret = Math.round(volume * 100);
        return Integer.toString(ret);
    }
}
