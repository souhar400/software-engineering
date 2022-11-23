package de.gruppe.e.klingklang.viewmodel;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.IOException;
import java.util.Map;

import de.gruppe.e.klingklang.R;
import de.gruppe.e.klingklang.model.ButtonData;
import de.gruppe.e.klingklang.model.FacadeData;
import de.gruppe.e.klingklang.model.OldViewFacadeData;
import de.gruppe.e.klingklang.services.SynthService;
import de.gruppe.e.klingklang.view.MainMenu;
import de.gruppe.e.klingklang.view.SoundMenu;

public class OldViewFacadeViewModel extends FacadeViewModel{
    private final FacadeData Model;
    private final SynthService SynthService;
    private final MainActivity Activity;
    private final MainMenu mainMenu;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private ImageButton editButton;
    private ImageButton menuButton;

    public OldViewFacadeViewModel(MainActivity Activity, SynthService SynthService, MainMenu mainMenu) {
        Model = new OldViewFacadeData();
        this.Activity = Activity;
        this.SynthService = SynthService;
        this.mainMenu = mainMenu;
    }

    public void initialiseButtons() {
        Model.buttonDataList.put((Button) Activity.findViewById(R.id.top_left1), new ButtonData("klingklang.sf2",5,62,127,5, false));
        Model.buttonDataList.put((Button) Activity.findViewById(R.id.top_left2), new ButtonData("klingklang.sf2",0,10,127,0, false));
        Model.buttonDataList.put((Button) Activity.findViewById(R.id.bottom_left1), new ButtonData("klingklang.sf2",6,62,127,6, false));
        Model.buttonDataList.put((Button) Activity.findViewById(R.id.bottom_left2), new ButtonData("klingklang.sf2",1,62,127,1, false));
        Model.buttonDataList.put((Button) Activity.findViewById(R.id.top_middle1), new ButtonData("klingklang.sf2",8,62,127,8, false));
        Model.buttonDataList.put((Button) Activity.findViewById(R.id.top_middle2), new ButtonData("klingklang.sf2",8,80,127,8, false));
        Model.buttonDataList.put((Button) Activity.findViewById(R.id.bottom_middle1), new ButtonData("klingklang.sf2",10,62,127,10, false));
        Model.buttonDataList.put((Button) Activity.findViewById(R.id.bottom_middle2), new ButtonData("klingklang.sf2",2,10,127,2, false));
        Model.buttonDataList.put((Button) Activity.findViewById(R.id.top_right1), new ButtonData("klingklang.sf2",4,62,127,4, true));
        Model.buttonDataList.put((Button) Activity.findViewById(R.id.top_right2), new ButtonData("klingklang.sf2",3,62,127,3, true));
        Model.buttonDataList.put((Button) Activity.findViewById(R.id.bottom_right1), new ButtonData("klingklang.sf2",7,62,127,7, true));
        Model.buttonDataList.put((Button) Activity.findViewById(R.id.bottom_right2), new ButtonData("klingklang.sf2",11,90,127,11, true));

        setButtonListener();

        editButton = Activity.findViewById(R.id.edit_button);
        editButton.setOnClickListener(view -> {
            Model.toggleInEditMode();
            editButton.setImageResource(Model.getInEditMode() ? R.drawable.play_mode : R.drawable.edit_mode);
        });

        menuButton = Activity.findViewById(R.id.setting_button);
        menuButton.setOnClickListener(view -> {
            mainMenu.show(Activity.getSupportFragmentManager(), "");
        });
    }

    private void setButtonListener() {
        for (Map.Entry<Button, ButtonData> entry : Model.buttonDataList.entrySet())
            entry.getKey().setOnClickListener(view -> {
                if (Model.getInEditMode()) {
                    SoundMenu smenu = new SoundMenu(entry.getValue());
                    smenu.show(Activity.getSupportFragmentManager(), "");
                } else {
                    try {
                        String tempSoundfontPath = SynthService.copyAssetToTmpFile(entry.getValue().getSoundfontPath());
                        SynthService.playFluidSynthSound(tempSoundfontPath, entry.getValue().getChannel(), entry.getValue().getKey(), entry.getValue().getVelocity(), entry.getValue().getPreset(), entry.getValue().isToggle());
                    } catch (IOException e) {
                        Log.e(LOG_TAG, "Failed to play synthesizer sound");
                        throw new RuntimeException(e);
                    }
                }
            });
    }
    /*
    public void editMode(){
        Model.toggleInEditMode();
        ImageButton ib = m.findViewById(R.id.edit_button);
        ib.setImageResource(Model.getInEditMode() ? R.drawable.play_mode : R.drawable.edit_mode);
    }*/
    /*
    public void openMenu() {
        mainMenu.show(m.getSupportFragmentManager(), "");
    }*/
}
