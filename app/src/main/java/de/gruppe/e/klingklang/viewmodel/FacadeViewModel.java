package de.gruppe.e.klingklang.viewmodel;

import de.gruppe.e.klingklang.model.FacadeData;

public class FacadeViewModel {
    private final FacadeData facadeData;

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
    }
}
