package de.gruppe.e.klingklang.model;


import android.content.pm.ActivityInfo;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

import de.gruppe.e.klingklang.R;
import de.gruppe.e.klingklang.viewmodel.MainActivity;

public class SecondFassade extends FacadeData {
    private final HashMap<Button, ButtonData> buttonDataList = new HashMap<>();
    private MainActivity context;
    private FacadeData nextFassade=null;
    private static SecondFassade instance;

    public static FacadeData getInstance(MainActivity activity) {
        if(instance== null){
            instance = new SecondFassade(activity);
        }
        return instance;
    }


    private SecondFassade(MainActivity activity) {
        context = activity;
        initialisebuttons();
    }

    @Override
    public Map<Button, ButtonData> getButtons(){
        return  buttonDataList;
    }


    @Override
    public void initialisebuttons() {
        buttonDataList.put((Button) context.findViewById(R.id.neu_test_button), new ButtonData("klingklang.sf2",12,62,127,12, false));
        //context.setButtonListener(buttons);
    }

    @Override
    public void setContentView() {
        context.setContentView( context.getLayoutInflater().inflate(R.layout.fassade_2,null));
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
    public void initialiseNextFassade() {
        if (nextFassade == null){
            nextFassade = SecondFassade.getInstance(context);
            nextFassade.initialiseNextFassade();
        }
    }
}
