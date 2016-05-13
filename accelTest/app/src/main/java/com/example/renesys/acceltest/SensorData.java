package com.example.renesys.acceltest;

/**
 * Created by RENESYS on 2016-05-08.
 */
public class SensorData {
    int accX, accY, accZ;
    int gyroX, gyroY, gyroZ;

    public SensorData(){
        accX = 0;
        accY = 0;
        accZ = 0;
        gyroX = 0;
        gyroY = 0;
        gyroZ = 0;
    }

    public int getAccX() {
        return accX;
    }

    public void setAccX(int accX) {
        this.accX = accX;
    }

    public int getAccY() {
        return accY;
    }

    public void setAccY(int accY) {
        this.accY = accY;
    }

    public int getAccZ() {
        return accZ;
    }

    public void setAccZ(int accZ) {
        this.accZ = accZ;
    }

    public int getGyroX() {
        return gyroX;
    }

    public void setGyroX(int gyroX) {
        this.gyroX = gyroX;
    }

    public int getGyroY() {
        return gyroY;
    }

    public void setGyroY(int gyroY) {
        this.gyroY = gyroY;
    }

    public int getGyroZ() {
        return gyroZ;
    }

    public void setGyroZ(int gyroZ) {
        this.gyroZ = gyroZ;
    }

    private int detectAccX(){
        int stat = 0;
        if(accX < -15 || accX > 15){
            stat = 1;
        }
        if(accX > 8000 || accX < -8000){
            stat = 3;
        }
        return stat;
    }

    private int detectAccY(){
        int stat = 0;
        if(accY < -10 || accY > 10){
            stat = 1;
        }
        if(accY > 5000 || accY < -5000){
            stat = 3;
        }
        return stat;
    }

    private int detectAccZ(){
        int stat = 0;
        if(accZ < -10 || accZ > 10){
            stat = 1;
        }
        if(accZ > 3500 || accZ < -3500){
            stat = 3;
        }
        return stat;
    }

    private int detectGyroX(){
        return gyroX >= -700  && gyroX <= 700 ? 0 : 1;
    }

    private int detectGyroY(){
        return gyroY >= -800 && gyroY <= 800 ? 0 : 1;
    }

    private int detectGyroZ(){
        return gyroZ >= -1000 && gyroZ <= 1000 ? 0 : 1;
    }

    public boolean checkEmergency(){
        int status = detectAccX() + detectAccY() + detectAccZ()
                + detectGyroX() +detectGyroY() +detectGyroZ();
        System.out.println(gyroX + " " + gyroY + " " + gyroZ +
                " " + accX + " " + accY + " " + accZ);
        return status >= 4;
    }

    public String getAllData(){
        return gyroX + " " + gyroY + " " + gyroZ +
                " " + accX + " " + accY + " " + accZ + "\n";
    }


}
