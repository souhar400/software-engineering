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
    private boolean toggle;
    private boolean visibility;

    private ButtonData(ButtonDataBuilder buttonDataBuilder) {
        this.buttonNumber = buttonDataBuilder.buttonNumber;
        this.soundfontPath = buttonDataBuilder.soundfontPath;
        this.midiPath = buttonDataBuilder.midiPath;
        this.key = buttonDataBuilder.key;
        this.velocity = buttonDataBuilder.velocity;
        this.preset = buttonDataBuilder.preset;
        this.toggle = buttonDataBuilder.toggle;
    }

    private native void setChannelVolume(int buttonNumber, int volume);

    /* Getter and Setter */

    public int getVolume() {
        return displayedVolume;
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

    public boolean isToggle() {
        return toggle;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }

    public void setVisibility() {
        visibility = !visibility;
    }

    public boolean getVisibility() {
        return visibility;
    }


    public static class ButtonDataBuilder {
        private static int BUTTONS = 0;

        private final int buttonNumber;
        private String soundfontPath;
        private String midiPath;
        private int key;
        private int velocity;
        private int preset;
        private boolean toggle;

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

        public ButtonDataBuilder withToggle(boolean toggle) {
            this.toggle = toggle;
            return this;
        }

        public ButtonData create() {
            return new ButtonData(this);
        }
    }
}
