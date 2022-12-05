package de.gruppe.e.klingklang.model;

import java.util.ArrayList;

import de.gruppe.e.klingklang.viewmodel.MainActivity;
import de.gruppe.e.klingklang.viewmodel.ViewModelFactory;

public class FassadeModel {
    private ArrayList<FacadeData> fassaden ;
    private int currentFacadeIndex;
    public FassadeModel(MainActivity activity){
        currentFacadeIndex = 0;
        fassaden = new ArrayList<>();
        ViewModelFactory factory = new ViewModelFactory(activity);
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
}
