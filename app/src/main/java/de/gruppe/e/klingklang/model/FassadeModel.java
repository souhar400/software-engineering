package de.gruppe.e.klingklang.model;

import java.util.ArrayList;

import de.gruppe.e.klingklang.viewmodel.MainActivity;
import de.gruppe.e.klingklang.viewmodel.ViewModelFactory;

public class FassadeModel {
    private ArrayList<FacadeData> fassaden ;
    private int counter;
    public FassadeModel(MainActivity activity){
        counter = 0;
        fassaden = new ArrayList<>();
        ViewModelFactory factory = new ViewModelFactory(activity);
        fassaden.add(factory.createFacadeOne());
        fassaden.add(factory.createFacadeTwo());
        fassaden.add(factory.createFacadeThree());
    }
    public FacadeData getNextFacade() {
        counter = (counter + 1) % 3;
        return fassaden.get(counter);
    }
    public FacadeData getInitialFassade() {
        return fassaden.get(0);
    }


}
