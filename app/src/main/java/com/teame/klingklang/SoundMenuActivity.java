package com.teame.klingklang;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.slider.Slider;

import org.w3c.dom.Text;

public class SoundMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sound_menu);

        EditText title = findViewById(R.id.title);
        title.setText("Instrument A Tonleiter");

        Slider slider1 = findViewById(R.id.slider1);
        slider1.setValue(50);
        Slider slider2 = findViewById(R.id.slider2);
        slider2.setValue(50);
        Slider slider3 = findViewById(R.id.slider3);
        slider3.setValue(50);

        TextView slider1Name = findViewById(R.id.slider1Name);
        slider1Name.setText("slider1");
        TextView slider2Name = findViewById(R.id.slider2Name);
        slider2Name.setText("slider2");
        TextView slider3Name = findViewById(R.id.slider3Name);
        slider3Name.setText("slider3");

        TextView slider1Value = findViewById(R.id.slider1Value);
        slider1Value.setText("50");
        TextView slider2Value = findViewById(R.id.slider2Value);
        slider2Value.setText("50");
        TextView slider3Value = findViewById(R.id.slider3Value);
        slider3Value.setText("50");

        addSliderListener(slider1, slider1Value);
        addSliderListener(slider2, slider2Value);
        addSliderListener(slider3, slider3Value);

        TextView volumeName = findViewById(R.id.volumeName);
        volumeName.setText("Lautstärke");
        Slider volumeSlider = findViewById(R.id.volumeSlider);
        TextView volumeValue = findViewById(R.id.volumeValue);
        volumeValue.setText("0");

        addSliderListener(volumeSlider, volumeValue);

    }

    private void addSliderListener(Slider s, TextView v) {
        s.addOnChangeListener((slider, value, fromUser) -> {
            Integer slide_value = Math.round(value);
            v.setText(slide_value.toString());
        });
    }

    public void close(View view){
        this.finish();
    }
}

