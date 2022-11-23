package de.gruppe.e.klingklang.model;

public class OldViewFacadeData extends FacadeData{
    private Boolean inEditMode = false;

    public void toggleInEditMode() {
        inEditMode = !inEditMode;
    }

    public Boolean getInEditMode() {
        return inEditMode;
    }
}
