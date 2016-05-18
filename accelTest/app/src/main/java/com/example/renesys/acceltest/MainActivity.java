package com.example.renesys.acceltest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorData sensor = new SensorData();

    private SensorManager SensorMan;
    private Sensor Gyro;
    private Sensor Acc;

    private Button btn1;
    private TextView txt;

    private boolean stat = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorMan = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        Gyro = SensorMan.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Acc = SensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        btn1 = (Button)findViewById(R.id.button1);
        txt = (TextView)findViewById(R.id.textView);
        txt.setMovementMethod(new ScrollingMovementMethod());


        testStart();
        checkEmergency();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stat = !stat;
            }
        });
    }


    public void onAccuracyChanged(Sensor s, int accuracy){

    }

    public void onSensorChanged(SensorEvent se){
        Sensor s = se.sensor;
        if(s.getType() == Sensor.TYPE_GYROSCOPE){
            sensor.setGyroX(Math.round(se.values[0] * 1000));
            sensor.setGyroY(Math.round(se.values[1] * 1000));
            sensor.setGyroZ(Math.round(se.values[2] * 1000));
        }
        if(s.getType() == Sensor.TYPE_ACCELEROMETER){
            sensor.setAccX((int)se.values[0]);
            sensor.setAccY((int)se.values[1]);
            sensor.setAccZ((int)se.values[2]);
        }
    }

    protected void onResume(){
        super.onResume();
        SensorMan.registerListener(this, Gyro, SensorManager.SENSOR_DELAY_FASTEST);
        SensorMan.registerListener(this, Acc, SensorManager.SENSOR_DELAY_FASTEST);
    }

    protected void onPause(){
        super.onPause();
        //SensorMan.unregisterListener(this);
    }

    private TimerTask second;
    private final Handler handler = new Handler();
    int timer_sec;
    int count;


    public void checkEmergency(){
        second = new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (sensor.checkEmergency2()) {
                            final AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                            dlg.setTitle("Emergency");
                            dlg.setMessage("Emergency Detected");
                            dlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            dlg.show();
                        }
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(second, 0, 400);
    }


    public void testStart() {
        timer_sec = 0;
        count = 0;
        final String fileName = "output.txt";

        second = new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Log.i("Test", "Timer start");
                        if(stat) {
                            String res = sensor.getAllData();
                            txt.setText(res);
                            writeFile(fileName, res);
                        }
                        Update();
                        timer_sec++;
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(second, 0, 400);
    }

    public void writeFile(String path, String str){
        try{

            File f = new File(getExternalFilesDir(null), path);
            FileWriter fw = new FileWriter(f, true);
            fw.write(str);
            fw.close();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    protected void Update() {
        Runnable updater = new Runnable() {
            public void run() {

            }
        };
        handler.post(updater);
    }

}
