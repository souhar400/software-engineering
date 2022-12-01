package de.gruppe.e.klingklang.model;


import android.content.pm.ActivityInfo;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

import de.gruppe.e.klingklang.R;
import de.gruppe.e.klingklang.viewmodel.MainActivity;

public class ThirdFacade extends FacadeData {
    private final HashMap<Button, ButtonData> buttonDataList = new HashMap<>();
    private MainActivity context;
    private FacadeData nextFassade=null;
    private static ThirdFacade instance;

    public static FacadeData getInstance(MainActivity activity) {
        if(instance== null){
            instance = new ThirdFacade(activity);
        }
        return instance;
    }


    private ThirdFacade(MainActivity activity) {
        context = activity;
        initialisebuttons();
    }

    @Override
    public Map<Button, ButtonData> getButtons(){
        return  buttonDataList;
    }


    @Override
    public void initialisebuttons() {
        buttonDataList.clear();
        buttonDataList.put((Button) context.findViewById(R.id.neu_test_button), new ButtonData("klingklang.sf2",12,62,127,12, false));
    }

    @Override
    public void setContentView() {
        context.setContentView( context.getLayoutInflater().inflate(R.layout.fassade_3,null));
    }

    @Override
    public void setOrientation() {
        context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public FacadeData getNextFassade() {
        return nextFassade;
    }


    @Override
    public void setNextFassade(FacadeData facadeData) {
        if (nextFassade == null){
            nextFassade = facadeData;
        }
    }
}
