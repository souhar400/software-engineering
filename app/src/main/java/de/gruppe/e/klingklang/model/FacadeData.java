package de.gruppe.e.klingklang.model;

import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

public abstract class FacadeData {
    private Boolean inEditMode = false;
    public void toggleInEditMode() {
        inEditMode = !inEditMode;
    }
    public Boolean getInEditMode()
    {
        return inEditMode;
    }
    public void setInEditMode(boolean inEditMode)  { this.inEditMode=inEditMode; }

    public abstract  Map<Button, ButtonData> getButtons();
    public abstract void initialisebuttons();
    public abstract void setContentView();
    public abstract void setOrientation();
    public abstract FacadeData getNextFassade();
    public abstract void setNextFassade(FacadeData facadeData);
}
