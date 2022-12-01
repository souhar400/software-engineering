package de.gruppe.e.klingklang.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import de.gruppe.e.klingklang.viewmodel.MainActivity;

public class FassadeModel {
    private HashMap<Integer, FacadeData> fassaden ;

    public FassadeModel(MainActivity activity){
        fassaden = new HashMap<>();
        fassaden.put(0, FirstFacade.getInstance(activity));
        fassaden.put(1, SecondFacade.getInstance(activity));
        linkFacades(); 
    }

    private void linkFacades() {
        for(int i=0; i< fassaden.size()-1 ; i++) {
            fassaden.get(i).setNextFassade(fassaden.get(i+1));
        }
        fassaden.get(fassaden.size()-1).setNextFassade(fassaden.get(0));
    }

    public FacadeData getInitialFassade() {
        return fassaden.get(0);
    }


}
