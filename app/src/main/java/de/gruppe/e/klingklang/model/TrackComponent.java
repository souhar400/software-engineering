package de.gruppe.e.klingklang.model;

public class TrackComponent {
    public long momentPlayed;
    public String soundfontPath;
    public int channel;
    public int key;
    public int velocity;
    public int preset;
    public boolean toggle;

    public TrackComponent(long momentPlayed, String soundfontPath, int channel, int key, int velocity, int preset, boolean toggle) {
        this.momentPlayed = momentPlayed;
        this.soundfontPath = soundfontPath;
        this.channel = channel;
        this.key = key;
        this.velocity = velocity;
        this.preset = preset;
        this.toggle = toggle;
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
                this.channel == that.channel &&
                this.key == that.key &&
                this.velocity == that.velocity &&
                this.preset == that.preset &&
                this.toggle == that.toggle;
    }

}
