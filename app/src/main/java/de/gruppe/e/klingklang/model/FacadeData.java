package de.gruppe.e.klingklang.model;

import android.app.Activity;

import java.util.Map;

public class FacadeData {
    private Boolean inEditMode = false;
    public void toggleInEditMode() {
        inEditMode = !inEditMode;
    }
    public Boolean getInEditMode()
    {
        return inEditMode;
    }
    public void setInEditMode(boolean inEditMode)  { this.inEditMode=inEditMode; }
    private Map<Integer, ButtonData> buttonDataMap;
    private Activity context;
    private int orientation;
    private int facadeId;
    public FacadeData(Activity context, int facadeId, int orientation, Map<Integer, ButtonData> buttonDataMap) {
        this.context = context;
        this.buttonDataMap = buttonDataMap;
        this.orientation = orientation;
        this.facadeId = facadeId;
    }

    public Map<Integer, ButtonData> getButtons(){
        return buttonDataMap;
    }

    public void setContentView() {
        context.setContentView(context.getLayoutInflater().inflate(facadeId, null));
    }

    public void setOrientation() {
        context.setRequestedOrientation(orientation);
    }
}
