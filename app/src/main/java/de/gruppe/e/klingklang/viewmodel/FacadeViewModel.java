package de.gruppe.e.klingklang.viewmodel;

import de.gruppe.e.klingklang.model.FacadeData;

public abstract class FacadeViewModel {
    private FacadeData facadeData;

    public FacadeViewModel(){

    }

    public abstract void initialiseButtons();

    /*
    public FacadeViewModel(ViewModelType type) {
        switch (type) {
            case FHMSFacade: {
                facadeData = new FacadeData();
                break;
            }
            default: {
                facadeData = new FacadeData();
            }
        }
    }

    public enum ViewModelType {
        FHMSFacade
    }*/
}
