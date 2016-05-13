package com.example.renesys.weatherinfo;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by RENESYS on 2016-05-13.
 */
public class Weather {

    String line = "a";

    public Weather(){

    }

    public void setURL() {
        try {
            System.out.println("asdf");
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?lon=126.97&lat=37.56&APPID=b98538e3e4195e5390a18fbb31a2d57e");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            InputStream in = new BufferedInputStream(conn.getInputStream());
            JSONObject json = new JSONObject(getStringFromInputStream(in));

            JSONObject j = json.getJSONArray("list").getJSONObject(4);
            String line = j.getJSONArray("weather").getJSONObject(0).getString("main");
            System.out.println(line);

            //line = json.getJSONObject("list").toString();



            //float temper = jo.getJSONObject("main").getInt("temp");
            //String weather = jo.getJSONObject("weather").getString("main");



        } catch (MalformedURLException e) {
            System.out.println("URL ERROR");
            e.printStackTrace();
        }catch(IOException e){
            System.out.println("IO ERROR");
            e.printStackTrace();
        }catch(Exception e){
            System.out.println("ERROR");
            e.printStackTrace();
        }
    }

    public String getLine(){
        return line;
    }

    private static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
