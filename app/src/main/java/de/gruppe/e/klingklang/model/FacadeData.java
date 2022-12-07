package de.gruppe.e.klingklang.model;

import java.util.Map;

public class FacadeData {
    private Boolean inEditMode = false;
    private final NamedLocation namedLocation;
    public void setInEditMode(boolean inEditMode)  { this.inEditMode=inEditMode; }
    private Map<Integer, ButtonData> buttonDataMap;
    private int orientation;
    private int facadeId;
    public FacadeData(int facadeId,
                      int orientation,
                      Map<Integer, ButtonData> buttonDataMap,
                      NamedLocation namedLocation) {
        this.buttonDataMap = buttonDataMap;
        this.orientation = orientation;
        this.facadeId = facadeId;
        this.namedLocation = namedLocation;
    }

    public Map<Integer, ButtonData> getButtons(){
        return buttonDataMap;
    }

    public int getFacadeId() {
        return facadeId;
    }

    public int getOrientation() {
        return orientation;
    }

    public void toggleInEditMode() {
        inEditMode = !inEditMode;
    }

    public Boolean getInEditMode() {
        return inEditMode;
    }

    public NamedLocation getNamedLocation() {
        return namedLocation;
    }
}
