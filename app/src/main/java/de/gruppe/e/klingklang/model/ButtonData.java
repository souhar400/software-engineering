package de.gruppe.e.klingklang.model;

public class ButtonData {
    private int volume = 127;
    private int displayedVolume = 100;
    private final int buttonNumber;
    private String soundfontPath;
    private String midiPath;
    private int key;
    private int velocity;
    private int preset;
    private boolean isLoop;
    private boolean isVisible;
    private int reverbButtonId;
    private boolean isReverbActivated = false;
    private boolean linearCrossfade = false;
    private boolean nonLinearCrossfade = false;
    private boolean showFadeOptions = false;

    private ButtonData(ButtonDataBuilder buttonDataBuilder) {
        this.buttonNumber = buttonDataBuilder.buttonNumber;
        this.soundfontPath = buttonDataBuilder.soundfontPath;
        this.midiPath = buttonDataBuilder.midiPath;
        this.key = buttonDataBuilder.key;
        this.velocity = buttonDataBuilder.velocity;
        this.preset = buttonDataBuilder.preset;
        this.isLoop = buttonDataBuilder.isLoop;
        this.reverbButtonId = buttonDataBuilder.reverbButtonId;
    }

    private native void setChannelVolume(int buttonNumber, int volume);

    private native void setChannelVolumeDirect(int buttonNumber, int volume);

    /* Getter and Setter */

    public int getVolume() {
        return displayedVolume;
    }

    public void setDirectVolume(int volume) {
        setChannelVolumeDirect(buttonNumber, volume);
    }

    public void setVolume(int volume) {
        this.displayedVolume = volume;
        this.volume = Math.round(1.27f * volume);
        setChannelVolume(buttonNumber, this.volume);
    }

    public int getButtonNumber() {
        return buttonNumber;
    }

    public String getSoundfontPath() {
        return soundfontPath;
    }

    public void setSoundfontPath(String soundfontPath) {
        this.soundfontPath = soundfontPath;
    }

    public String getMidiPath() {
        return midiPath;
    }

    public void setMidiPath(String midiPath) {
        this.midiPath = midiPath;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getPreset() {
        return preset;
    }

    public void setPreset(int preset) {
        this.preset = preset;
    }

    public boolean isLoop() {
        return isLoop;
    }

    public void setLoop(boolean isLoop) {
        this.isLoop = isLoop;
    }

    public void setVisibility() {
        isVisible = !isVisible;
    }

    public boolean getVisible() {
        return isVisible;
    }

    public int getReverbButtonId() {
        return reverbButtonId;
    }

    public void setReverbButtonId(int reverbButtonId) {
        this.reverbButtonId = reverbButtonId;
    }

    public boolean isReverbActivated() {
        return isReverbActivated;
    }

    public void setReverbActivated(boolean reverbActivated) {
        isReverbActivated = reverbActivated;
    }

    public void setLinearCrossfade(boolean linearCrossfade){
        this.linearCrossfade = linearCrossfade;
    }
    public boolean getLinearCrossfade() {
        return linearCrossfade;
    }

    public void setNonLinearCrossfade(boolean nonLinearCrossfade){
        this.nonLinearCrossfade = nonLinearCrossfade;
    }
    public boolean getNonLinearCrossfade() {
        return nonLinearCrossfade;
    }

    public boolean getShowFadeOptions() {
        return showFadeOptions;
    }

    public void setShowFadeOptions(boolean fadeOptions) {
        this.showFadeOptions = fadeOptions;
    }

    public static class ButtonDataBuilder {
        private static int BUTTONS = 0;

        private final int buttonNumber;
        private String soundfontPath;
        private String midiPath;
        private int key;
        private int velocity;
        private int preset;
        private boolean isLoop;
        private int reverbButtonId;
        private int tracklength;

        public ButtonDataBuilder() {
            this.buttonNumber = BUTTONS;
            BUTTONS++;
        }

        public ButtonDataBuilder withMidiPath(String midiPath) {
            this.midiPath = midiPath;
            return this;
        }

        public ButtonDataBuilder withSoundfontPath(String soundfontPath) {
            this.soundfontPath = soundfontPath;
            return this;
        }

        public ButtonDataBuilder withKey(int key) {
            this.key = key;
            return this;
        }

        public ButtonDataBuilder withVelocity(int velocity) {
            this.velocity = velocity;
            return this;
        }

        public ButtonDataBuilder withPreset(int preset) {
            this.preset = preset;
            return this;
        }

        public ButtonDataBuilder withLoop(boolean isLoop) {
            this.isLoop = isLoop;
            return this;
        }

        public ButtonDataBuilder withReverbButton(int reverbButtonId) {
            this.reverbButtonId = reverbButtonId;
            return this;
        }

        public ButtonData create() {
            return new ButtonData(this);
        }
    }
}
