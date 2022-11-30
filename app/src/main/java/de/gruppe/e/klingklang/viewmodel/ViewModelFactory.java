package de.gruppe.e.klingklang.viewmodel;

import android.widget.Button;

import androidx.fragment.app.FragmentManager;

import java.util.HashMap;

import de.gruppe.e.klingklang.R;
import de.gruppe.e.klingklang.model.ButtonData;
import de.gruppe.e.klingklang.model.FacadeData;
import de.gruppe.e.klingklang.services.SynthService;
import de.gruppe.e.klingklang.view.ControlButtonsOverlayView;

public class ViewModelFactory {
    private final MainActivity mainActivity;
    public ViewModelFactory(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public ViewModel createOldViewModel(ControlButtonsOverlayView controlButtonsOverlayView,
                                        SynthService synthService,
                                        FragmentManager manager) {
        HashMap<Button, ButtonData> oldViewButtons = initialiseOldViewButtons();
        return new FacadeViewModel(controlButtonsOverlayView,
                new FacadeData(),
                synthService,
                manager,
                oldViewButtons);
    }

    private HashMap<Button,ButtonData> initialiseOldViewButtons() {
        HashMap<Button,ButtonData> map = new HashMap<>();
        map.put((Button) mainActivity.findViewById(R.id.top_left1), new ButtonData("klingklang.sf2",5,62,127,5, false));
        map.put((Button) mainActivity.findViewById(R.id.top_left2), new ButtonData("klingklang.sf2",0,10,127,0, false));
        map.put((Button) mainActivity.findViewById(R.id.bottom_left1), new ButtonData("klingklang.sf2",6,62,127,6, false));
        map.put((Button) mainActivity.findViewById(R.id.bottom_left2), new ButtonData("klingklang.sf2",1,62,127,1, false));
        map.put((Button) mainActivity.findViewById(R.id.top_middle1), new ButtonData("klingklang.sf2",8,62,127,8, false));
        map.put((Button) mainActivity.findViewById(R.id.top_middle2), new ButtonData("klingklang.sf2",8,80,127,8, false));
        map.put((Button) mainActivity.findViewById(R.id.bottom_middle1), new ButtonData("klingklang.sf2",10,62,127,10, false));
        map.put((Button) mainActivity.findViewById(R.id.bottom_middle2), new ButtonData("klingklang.sf2",2,10,127,2, false));
        map.put((Button) mainActivity.findViewById(R.id.top_right1), new ButtonData("klingklang.sf2",4,62,127,4, true));
        map.put((Button) mainActivity.findViewById(R.id.top_right2), new ButtonData("klingklang.sf2",3,62,127,3, true));
        map.put((Button) mainActivity.findViewById(R.id.bottom_right1), new ButtonData("klingklang.sf2",7,62,127,7, true));
        map.put((Button) mainActivity.findViewById(R.id.bottom_right2), new ButtonData("klingklang.sf2",11,90,127,11, true));
        return map;
    }
}
