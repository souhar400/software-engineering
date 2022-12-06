package de.gruppe.e.klingklang.viewmodel;

import androidx.lifecycle.ViewModel;

import de.gruppe.e.klingklang.model.FacadeData;
import de.gruppe.e.klingklang.model.FassadeModel;
import de.gruppe.e.klingklang.model.NamedLocation;

public class FacadeViewModel extends ViewModel {
    private FassadeModel fassadenModel;
    private FacadeData actualFassade;
    private final String LOG_TAG = getClass().getSimpleName();

    public void changeFassade (){
        actualFassade = fassadenModel.getNextFacade();
        actualFassade.setOrientation();
        actualFassade.setContentView();
        actualFassade.setInEditMode(false);
    }

    public void toggleInEditMode() {
        fassadenModel.getCurrentFacade().toggleInEditMode();
    }

    public boolean getInEditMode() {
        return fassadenModel.getCurrentFacade().getInEditMode();
    }

    public NamedLocation getNamedLocation() {
        return fassadenModel.getCurrentFacade().getNamedLocation();
    }

    public FacadeData getActualFassade() {
        return fassadenModel.getCurrentFacade();
    }

    public void setModel(FassadeModel fassadenModel) {
        this.fassadenModel = fassadenModel;
    }
}
