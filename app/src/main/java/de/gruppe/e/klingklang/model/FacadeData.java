package de.gruppe.e.klingklang.model;

import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

public class FacadeData {
    private final Map<Button, ButtonData> buttonDataList;
    private Boolean inEditMode = false;
    private final NamedLocation namedLocation;

    public FacadeData(Map<Button, ButtonData> buttonDataList, NamedLocation namedLocation) {
        this.buttonDataList = buttonDataList;
        this.namedLocation = namedLocation;
    }

    public void toggleInEditMode() {
        inEditMode = !inEditMode;
    }

    public Boolean getInEditMode() {
        return inEditMode;
    }

    public Map<Button, ButtonData> getButtonDataList() {
        return new HashMap<>(buttonDataList);
    }

    public NamedLocation getNamedLocation() {
        return namedLocation;
    }
}
