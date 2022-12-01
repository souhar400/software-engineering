package de.gruppe.e.klingklang.model;

import java.util.HashMap;
import java.util.Random;

import de.gruppe.e.klingklang.viewmodel.MainActivity;

public class FassadeModel {

    //TODO initialise die Fassaden hier : verketten 
    private static int actualFasssadenNbr = 0;
    private HashMap<Integer, FacadeData> fassaden ;

    public FassadeModel(MainActivity activity){
        fassaden = new HashMap<>();
        fassaden.put(0, FirstFacade.getInstance(activity));
        fassaden.put(1, SecondFacade.getInstance(activity));
    }

    public FacadeData getInitialFassade() {
        Random rdm = new Random();
        return fassaden.get( rdm.nextInt(2)) ;
    }

}
