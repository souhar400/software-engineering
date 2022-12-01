package de.gruppe.e.klingklang.model;


import android.content.pm.ActivityInfo;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

import de.gruppe.e.klingklang.R;
import de.gruppe.e.klingklang.viewmodel.MainActivity;

public class FirstFacade extends FacadeData {
    private final HashMap<Button, ButtonData> buttonDataList = new HashMap<>();
    private MainActivity context;
    private FacadeData nextFassade=null;
    private static FirstFacade instance;

    public static FacadeData getInstance(MainActivity activity) {
        if (instance == null)
            instance = new FirstFacade(activity);
        return instance;
    }

    private FirstFacade(MainActivity activity) {
        context = activity;
        initialisebuttons();
    }

    @Override
    public Map<Button, ButtonData> getButtons(){
        return buttonDataList;
    }


    public void initialisebuttons() {
        buttonDataList.clear();
        buttonDataList.put((Button) context.findViewById(R.id.top_left1), new ButtonData("klingklang.sf2", 5, 62, 127, 5, false));
        buttonDataList.put((Button) context.findViewById(R.id.top_left2), new ButtonData("klingklang.sf2", 0, 10, 127, 0, false));
        buttonDataList.put((Button) context.findViewById(R.id.bottom_left1), new ButtonData("klingklang.sf2", 6, 62, 127, 6, false));
        buttonDataList.put((Button) context.findViewById(R.id.bottom_left2), new ButtonData("klingklang.sf2", 1, 62, 127, 1, false));
        buttonDataList.put((Button) context.findViewById(R.id.top_middle1), new ButtonData("klingklang.sf2", 8, 62, 127, 8, false));
        buttonDataList.put((Button) context.findViewById(R.id.top_middle2), new ButtonData("klingklang.sf2", 8, 80, 127, 8, false));
        buttonDataList.put((Button) context.findViewById(R.id.bottom_middle1), new ButtonData("klingklang.sf2", 10, 62, 127, 10, false));
        buttonDataList.put((Button) context.findViewById(R.id.bottom_middle2), new ButtonData("klingklang.sf2", 2, 10, 127, 2, false));
        buttonDataList.put((Button) context.findViewById(R.id.top_right1), new ButtonData("klingklang.sf2", 4, 62, 127, 4, true));
        buttonDataList.put((Button) context.findViewById(R.id.top_right2), new ButtonData("klingklang.sf2", 3, 62, 127, 3, true));
        buttonDataList.put((Button) context.findViewById(R.id.bottom_right1), new ButtonData("klingklang.sf2", 7, 62, 127, 7, true));
        buttonDataList.put((Button) context.findViewById(R.id.bottom_right2), new ButtonData("klingklang.sf2", 11, 90, 127, 11, true));
    }


    public void setContentView() {
        context.setContentView(context.getLayoutInflater().inflate(R.layout.activity_main, null));
    }

    public void setOrientation() {
        context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }



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
