package de.gruppe.e.klingklang.model;

import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class FacadeData {
    public final HashMap<Button, ButtonData> buttonDataList;

    public FacadeData() {
        buttonDataList = new HashMap<>();
    }

    public abstract void toggleInEditMode();

    public abstract Boolean getInEditMode();

}
