package de.gruppe.e.klingklang;

// Temporary Storage Class for the Volume of a single Button
public class ButtonData {
    private int volume = 100;
    private final int channel;
    private final String sound;

    public ButtonData(int channel, String sound) {
        this.channel = channel;
        this.sound = sound;
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

    public String getSound() {
        return sound;
    }

    public int getChannel() {
        return channel;
    }

    private native void setChannelVolume(int channel, int volume);
}
