package com.example.owen.weathergo;

/**
 * Created by owenh on 2016/5/17.
 * 一个weatherBean bean类
 */

public class Hsh_WeatherBean {

        private String country;
        private double temp_min;
        private double temp_max;
        private double temp;
        private int wind_speed;
        public double getTemp_min(){
            return temp_min;
        }
        public double getTemp(){
            return temp;
        }
        public double getTemp_max(){
            return temp_max;
        }
        public int getWind_speed(){
            return wind_speed;
        }
        public String getCountry() {
            return country;
        }
        public void setTemp(double temp){
            this.temp = temp;
        }
        public void setTemp_min(double temp_min) {
            this.temp_min = temp_min;
        }
        public void setTemp_max(double temp_max) {
            this.temp_max = temp_max;
        }
        public void setWind_speed(int wind_speed) {
            this.wind_speed = wind_speed;
        }
        public void setCountry(String country) {
            this.country = country;
        }


    }



