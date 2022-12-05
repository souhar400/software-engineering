package de.gruppe.e.klingklang.viewmodel;

import de.gruppe.e.klingklang.model.NamedLocation;

public interface ViewModel {

    void toggleInEditMode();
    boolean getInEditMode();
    NamedLocation getNamedLocation();
    void changeFassade();
}
