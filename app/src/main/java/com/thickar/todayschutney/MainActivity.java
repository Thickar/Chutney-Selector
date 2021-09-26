package com.thickar.todayschutney;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget;
import com.bluehomestudio.luckywheel.WheelItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private LuckyWheel luckyWheel;
    List<WheelItem> wheelItems;
    int randomChutney = 1;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generateWheelItems();
        luckyWheel = findViewById(R.id.lwv);
        luckyWheel.addWheelItems(wheelItems);
        luckyWheel.setTarget(1);

        luckyWheel.setLuckyWheelReachTheTarget(new OnLuckyWheelReachTheTarget() {
            @Override
            public void onReachTarget() {
                Toast.makeText(MainActivity.this, "சட்னி தேர்ந்தெடுக்கப்பட்டது", Toast.LENGTH_LONG).show();
                ConvertTextToSpeech();
            }
        });

        Button startButton  = findViewById(R.id.start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                randomChutney =  random.nextInt(wheelItems.size() - 1 + 1) + 1;
                luckyWheel.rotateWheelTo(randomChutney);
            }
        });

        tts=new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    String localCode = "ta-IN";
                    Locale locale = new Locale(localCode);
                    int result=tts.setLanguage(locale);
                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Toast.makeText(MainActivity.this, "This Language is not supported", Toast.LENGTH_LONG).show();
                    }
//                    else{
//                        ConvertTextToSpeech();
//                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "Initilization Failed!", Toast.LENGTH_LONG).show();
                }
            }
        });


        }

    private void ConvertTextToSpeech() {
           String text =  wheelItems.get(randomChutney-1).text;
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    void generateWheelItems(){
        wheelItems = new ArrayList<>();
        wheelItems.add(new WheelItem(Color.parseColor("#ADB88D"), BitmapFactory.decodeResource(getResources(), R.drawable.coconut),"தேங்காய் சட்னி"));

        wheelItems.add(new WheelItem(Color.parseColor("#C29C5E"), BitmapFactory.decodeResource(getResources(), R.drawable.puli),"புளி சட்னி"));

        wheelItems.add(new WheelItem(Color.parseColor("#FA0301"), BitmapFactory.decodeResource(getResources(), R.drawable.thakali),"தக்காளி சட்னி"));

        wheelItems.add(new WheelItem(Color.parseColor("#DFBD75"), BitmapFactory.decodeResource(getResources(), R.drawable.kuruma),"தக்காளி குரும"));

    }
}