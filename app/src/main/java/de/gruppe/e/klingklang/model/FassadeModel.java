package de.gruppe.e.klingklang.model;

import java.util.ArrayList;

import de.gruppe.e.klingklang.viewmodel.ViewModelFactory;

public class FassadeModel {
    private static FassadeModel INSTANCE;
    private ArrayList<FacadeData> fassaden ;
    private int currentFacadeIndex;
    private FassadeModel(){
        currentFacadeIndex = 0;
        fassaden = new ArrayList<>();
        ViewModelFactory factory = new ViewModelFactory();
        fassaden.add(factory.createFacadeOne());
        fassaden.add(factory.createFacadeTwo());
        fassaden.add(factory.createFacadeThree());
    }
    public FacadeData getNextFacade() {
        currentFacadeIndex = (currentFacadeIndex + 1) % 3;
        return fassaden.get(currentFacadeIndex);
    }

    public FacadeData getCurrentFacade() {
        return fassaden.get(currentFacadeIndex);
    }

    public static FassadeModel getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new FassadeModel();
        }
        return INSTANCE;
    }
}
