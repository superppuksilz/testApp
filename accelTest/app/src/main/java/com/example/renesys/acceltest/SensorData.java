package com.example.renesys.acceltest;

/**
 * Created by RENESYS on 2016-05-08.
 */


public class SensorData {
    final static int VECTOR_SIZE = 2;

    int[] currrentVector = {0, 0, 0, 0, 0, 0};
    int[][] standardVector = {
            {0, 6, 7, 0, 0, 0},
            //{0, 6, 7, 400, 0, 0},
            {5, 0, 6, 0, 0, 0},
            //{5, 0, 6, 1000, 0, 0}
    };

    public SensorData(){

    }


    public void setAccX(int accX) {
        this.currrentVector[0] = accX;
    }

    public void setAccY(int accY) {
        this.currrentVector[1] = accY;
    }

    public void setAccZ(int accZ) {
        this.currrentVector[2] = accZ;
    }

    public void setGyroX(int gyroX) {
        this.currrentVector[3] = gyroX;
    }

    public void setGyroY(int gyroY) {
        this.currrentVector[4] = gyroY;
    }

    public void setGyroZ(int gyroZ) {
        this.currrentVector[5] = gyroZ;
    }

    private int detectAccX(){
        int stat = 0;
        if(currrentVector[0] < -15 || currrentVector[0] > 15){
            stat = 1;
        }
        if(currrentVector[0] > 8000 || currrentVector[0] < -8000){
            stat = 3;
        }
        return stat;
    }

    private int detectAccY(){
        int stat = 0;
        if(currrentVector[1] < -15 || currrentVector[1] > 15){
            stat = 1;
        }
        if(currrentVector[1] > 5000 || currrentVector[1] < -5000){
            stat = 3;
        }
        return stat;
    }

    private int detectAccZ(){
        int stat = 0;
        if(currrentVector[2] < -15 || currrentVector[2] > 15){
            stat = 1;
        }
        if(currrentVector[2] > 3500 || currrentVector[2] < -3500){
            stat = 3;
        }
        return stat;
    }

    private int detectGyroX(){
        return currrentVector[3] >= -700  && currrentVector[3] <= 700 ? 0 : 1;
    }

    private int detectGyroY(){
        return currrentVector[4] >= -800 && currrentVector[4] <= 800 ? 0 : 1;
    }

    private int detectGyroZ(){
        return currrentVector[5] >= -1000 && currrentVector[5] <= 1000 ? 0 : 1;
    }

    public double cosineSim(int[] stdVector){
        double res = 0;
        double qi = 0, q = 0, i = 0;

        for(int it = 0; it < 6; it++){
            qi += Math.abs(currrentVector[it]) * Math.abs(stdVector[it]);
            q += currrentVector[it] * currrentVector[it];
            i += stdVector[it] * stdVector[it];
        }
        res = qi / (Math.sqrt(q) * Math.sqrt(i));
        return res;
    }

    public boolean checkEmergency(){
        int status = detectAccX() + detectAccY() + detectAccZ()
                + detectGyroX() +detectGyroY() +detectGyroZ();
        return status >= 4;
    }


    public boolean checkEmergency2(){
        boolean check = false;
        for(int i = 0; i < VECTOR_SIZE; i++) {
            double d = cosineSim(standardVector[i]);
            if (d < 5.0e-4){
                check = true;
                break;
            }
        }
        return check;
    }

    public String getAllData(){
        String str = "";
        for(int i = 0; i < 5; i++){
            str += currrentVector[i] + " ";
        }
        str += (currrentVector[5] + "\n");
        return str;
    }


}
