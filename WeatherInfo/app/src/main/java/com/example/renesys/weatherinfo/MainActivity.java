package com.example.renesys.weatherinfo;

import android.content.Context;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
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
        final LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        new Thread(){
            public void run(){
                w.setURL(lm);

                Bundle bun = new Bundle();
                bun.putString("data", w.getLine());
                Message msg = han.obtainMessage();
                msg.setData(bun);
                han.sendMessage(msg);
            }
        }.start();

        txt = (TextView)findViewById(R.id.text);
    }

    Handler han = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bundle bun = msg.getData();
            String str = bun.getString("data");
            txt.setText(str);
        }
    };

}

