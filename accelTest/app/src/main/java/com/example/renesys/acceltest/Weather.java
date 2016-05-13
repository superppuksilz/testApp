package com.example.renesys.acceltest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by RENESYS on 2016-05-13.
 */
public class Weather {

    JSONObject jo;

    public Weather(){

    }

    public void setURL() {
        try {
            System.out.println("Hello");
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?lon=126.97&lat=37.56" +
                    "&APPID=b98538e3e4195e5390a18fbb31a2d57e");
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = rd.readLine();
            System.out.println(line);


            jo = new JSONObject(line);



            //float temper = jo.getJSONObject("main").getInt("temp");
            //String weather = jo.getJSONObject("weather").getString("main");



        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch(Exception e){

        }
    }
}
