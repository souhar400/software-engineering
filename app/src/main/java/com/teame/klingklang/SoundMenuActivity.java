package com.teame.klingklang;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.Slider;

public class SoundMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sound_menu);

        TextView sound = findViewById(R.id.Sound);
        sound.setText("50");

        Slider slider = findViewById(R.id.soundSlider);
        slider.setValue(50);

        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                Integer slide_value = Math.round(value);
                sound.setText(slide_value.toString());
            }
        });

    }
}
