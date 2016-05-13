package com.example.renesys.weatherinfo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Weather w = new Weather();
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = (TextView)findViewById(R.id.text);

        new Thread(){
            public void run(){
                w.setURL();

            }
        }.start();

        System.out.println("asdasd : " + w.getLine());
    }

    public void func(){

    }

    Handler handler = new Handler(){
        public void handleMessage(Weather w){
            txt.setText(w.getLine());
        }
    };

}
