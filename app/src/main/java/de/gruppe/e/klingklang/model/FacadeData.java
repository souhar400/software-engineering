package de.gruppe.e.klingklang.model;

import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

public class FacadeData {
    public Map<Button, ButtonData> buttonDataList;
    private Boolean inEditMode = false;

    public FacadeData() {
        buttonDataList = new HashMap<>();
    }

    public void toggleInEditMode() {
        inEditMode = !inEditMode;
    }

    public Boolean getInEditMode() {
        return inEditMode;
    }
}
