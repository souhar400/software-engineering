package de.gruppe.e.klingklang.viewmodel;

import android.view.View;
import android.widget.Button;

import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Map;

import de.gruppe.e.klingklang.model.ButtonData;
import de.gruppe.e.klingklang.model.FacadeData;
import de.gruppe.e.klingklang.model.FassadeModel;
import de.gruppe.e.klingklang.model.NamedLocation;

public class FacadeViewModel extends ViewModel {
    private FassadeModel fassadenModel = FassadeModel.getInstance();
    private FacadeData actualFassade;
    private final String LOG_TAG = getClass().getSimpleName();

    public void toggleInEditMode() {
        fassadenModel.getCurrentFacade().toggleInEditMode();
    }

    public boolean getInEditMode() {
        return fassadenModel.getCurrentFacade().getInEditMode();
    }

    public NamedLocation getNamedLocation() {
        return fassadenModel.getCurrentFacade().getNamedLocation();
    }

    public FacadeData getActualFassade() {
        return fassadenModel.getCurrentFacade();
    }

    public FacadeData getNextFacade() {
        return fassadenModel.getNextFacade();
    }

    public List<NamedLocation> getAllLocations() {
        return fassadenModel.getAllLocations();
    }

    /**
     * Buttons, die im ButtonMenu den Haken beim Switch "Hide Button" haben werden unsichtbar gemacht
     * @param sound_buttons alle Buttons, die musik abspielen können
     */
    public void makeButtonsInvisible(View[] sound_buttons, View[] hall_buttons) {
        for (Map.Entry<Integer, ButtonData> entry : getActualFassade().getButtons().entrySet()) {

            if (entry.getValue().getVisible()) {
                int number = entry.getKey();
                for (int i = 0; i < sound_buttons.length; i++) {
                    if (sound_buttons[i].getId() == number) {
                        sound_buttons[i].setVisibility(View.GONE);
                        hall_buttons[i].setVisibility(View.GONE);
                        break;
                    }
                }
            }

        }


    }
    public void makeButtonsVisible(View[] sound_buttons, View[] hall_buttons) {
        //actualFassade.getButtons().entrySet();
        for (int i = 0; i < getActualFassade().getButtons().entrySet().size(); i++) {
            sound_buttons[i].setVisibility(View.VISIBLE);
            hall_buttons[i].setVisibility(View.VISIBLE);
        }
    }
    public void resetAllButtonVisibilities() {
        for (Map.Entry<Integer, ButtonData> entry : getActualFassade().getButtons().entrySet()){
            if (entry.getValue().getVisible()) {
                entry.getValue().setVisibility();
            }
        }
    }
}


