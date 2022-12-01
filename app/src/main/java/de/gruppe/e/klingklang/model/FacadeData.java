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

    public abstract  Map<Button, ButtonData> getButtons();
    abstract void initialisebuttons();
    abstract void setContentView();
    abstract void setOrientation();
    abstract void initialiseNextFassade();
    abstract FacadeData getNextFassade();
}
