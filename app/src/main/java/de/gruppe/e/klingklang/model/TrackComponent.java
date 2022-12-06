package de.gruppe.e.klingklang.model;

public class TrackComponent {
    public long momentPlayed;
    public String soundfontPath;
    public String midiPath;
    public int buttonNumber;
    public int key;
    public int velocity;
    public int preset;
    public boolean isLoop;

    public TrackComponent(long momentPlayed, String midiPath, String soundfontPath, int buttonNumber, int key, int velocity, int preset, boolean isLoop) {
        this.momentPlayed = momentPlayed;
        this.midiPath = midiPath;
        this.soundfontPath = soundfontPath;
        this.buttonNumber = buttonNumber;
        this.key = key;
        this.velocity = velocity;
        this.preset = preset;
        this.isLoop = isLoop;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        if (obj.getClass() != this.getClass())
            return false;
        TrackComponent that = (TrackComponent) obj;
        return  this.momentPlayed == that.momentPlayed &&
                this.soundfontPath.equals(that.soundfontPath) &&
                this.midiPath.equals(that.midiPath) &&
                this.buttonNumber == that.buttonNumber &&
                this.key == that.key &&
                this.velocity == that.velocity &&
                this.preset == that.preset &&
                this.isLoop == that.isLoop;
    }
}
