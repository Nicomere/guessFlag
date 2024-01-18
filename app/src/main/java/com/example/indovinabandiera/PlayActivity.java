package com.example.indovinabandiera;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener{
    private Vector<Button> btnFlag;
    private Vector<String> flagName;
    private String correctFlag;
    private ImageView imgFlag;
    private TextView lbl_winCount;

    @SuppressLint("MissingInflatedId")
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        lbl_winCount = findViewById(R.id.lbl_winCount);
        btnFlag = new Vector<Button>(4, 1);
        flagName = setFlagName();
        btnFlag.add(findViewById(R.id.btn_1));
        btnFlag.add(findViewById(R.id.btn_2));
        btnFlag.add(findViewById(R.id.btn_3));
        btnFlag.add(findViewById(R.id.btn_4));
        imgFlag = findViewById(R.id.flag_image);
        btnFlag.elementAt(0).setOnClickListener(this);
        btnFlag.elementAt(1).setOnClickListener(this);
        btnFlag.elementAt(2).setOnClickListener(this);
        btnFlag.elementAt(3).setOnClickListener(this);
        startGame();
    }

    private void startGame(){
        Random random = new Random();
        correctFlag = flagName.elementAt(random.nextInt(flagName.size()-1));
        AssetManager am = getAssets();
        try {
            InputStream is = am.open("bandiere/" + correctFlag +".jpg");
            imgFlag.setImageBitmap(BitmapFactory.decodeStream(is));
        }catch (IOException e){e.printStackTrace();}
        String tmp[] = new String[4];
        tmp[0]=correctFlag;
        for(int i=1; i<4; i++){
            tmp[i]=flagName.elementAt(random.nextInt(flagName.size()-1));
        }
        List<String> strTmp = Arrays.asList(tmp);
        Collections.shuffle(strTmp);
        strTmp.toArray(tmp);
        for(int i=0; i<4; i++){
            btnFlag.elementAt(i).setText(tmp[i]);
        }
    }

    private Vector<String> setFlagName(){
        try {
            InputStream file = getAssets().open("bandiere.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));
            Vector<String> bandiere = new Vector<>();
            String line = reader.readLine();
            while (line != null) {
                bandiere.add(line);
                line = reader.readLine();
            }
            reader.close();
            file.close();

            return bandiere;
        }catch (IOException e){return null;}
    }

    @Override
    public void onClick(View view) {
        Button btn = findViewById(view.getId());
        if(correctFlag.equalsIgnoreCase((String) btn.getText())){
            Toast.makeText(PlayActivity.this, "Bravo! Hai vinto!", Toast.LENGTH_SHORT).show();
            int tmp = Integer.parseInt((String) lbl_winCount.getText());
            tmp++;
            lbl_winCount.setText(String.valueOf(tmp));
        }else{
            Toast.makeText(PlayActivity.this, "Mi spiace hai perso \nLa bandiera era: " + correctFlag, Toast.LENGTH_SHORT).show();
        }
        try {
            wait(1000);
        }catch (Exception e){e.printStackTrace();}
        this.startGame();
    }
}