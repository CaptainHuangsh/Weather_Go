package com.example.owen.weathergo.modules.dao;

/**
 * Created by owenh on 2016/5/17.
 * 一个weatherBean bean类
 */

public class WeatherBean {

    //"aqi"
    private int aqi;
    private int pm10;
    private int pm25;
    private String qlty;

    //"basic"
    private String city, cnty, id;//city,country,cityId
    private double lat, lon;//
    //"update"
    private String loc, utc;

    //"daily_forecast"
    //"astro"
    private String[] sr, ss;
    //"cond"
    private int[] code_d, code_n;
    private String[] txt_d, txt_n;
    private String[] date;
    private int[] hum, pop, pres;
    private String[] pcpn;
    //"tmp"
    private int[] max, min;
    private int[] vis;
    private int now_max;

    public int getNow_max() {
        return now_max;
    }

    public void setNow_max(int now_max) {
        this.now_max = now_max;
    }

    public int getNow_min() {
        return now_min;
    }

    public void setNow_min(int now_min) {
        this.now_min = now_min;
    }

    private int now_min;
    //"wind"
    private int[] deg, apd;
    private String[] dir, sc;
    //"now"
    //"cond"
    private int code;
    private String txt;
    private int now_fl, now_hum, now_pcpn, now_pres, now_tmp, now_vis;
    //"wind"
    private int now_deg;
    private String now_dir, now_sc, now_spd;
    private String status;
    //"suggestion"
    private String comf_brf, comf_txt;
    private String cw_brf, cw_txt;
    private String drsg_brf, drsg_txt;
    private String flu_brf, flu_txt;
    private String sport_brf, sport_txt;
    private String trav_brf, trav_txt;
    private String uv_brf, uv_txt;

    public String getMain_weather_img() {
        return main_weather_img;
    }

    public void setMain_weather_img(String main_weather_img) {
        this.main_weather_img = main_weather_img;
    }

    private String main_weather_img;


    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public int getPm10() {
        return pm10;
    }

    public void setPm10(int pm10) {
        this.pm10 = pm10;
    }

    public int getPm25() {
        return pm25;
    }

    public void setPm25(int pm25) {
        this.pm25 = pm25;
    }

    public String getQlty() {
        return qlty;
    }

    public void setQlty(String qlty) {
        this.qlty = qlty;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCnty() {
        return cnty;
    }

    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getUtc() {
        return utc;
    }

    public void setUtc(String utc) {
        this.utc = utc;
    }

    public String[] getSr() {
        return sr;
    }

    public void setSr(String[] sr) {
        this.sr = sr;
    }

    public String[] getSs() {
        return ss;
    }

    public void setSs(String[] ss) {
        this.ss = ss;
    }

    public int[] getCode_d() {
        return code_d;
    }

    public void setCode_d(int[] code_d) {
        this.code_d = code_d;
    }

    public int[] getCode_n() {
        return code_n;
    }

    public void setCode_n(int[] code_n) {
        this.code_n = code_n;
    }

    public String[] getTxt_d() {
        return txt_d;
    }

    public void setTxt_d(String[] txt_d) {
        this.txt_d = txt_d;
    }

    public String[] getTxt_n() {
        return txt_n;
    }

    public void setTxt_n(String[] txt_n) {
        this.txt_n = txt_n;
    }

    public String[] getDate() {
        return date;
    }

    public void setDate(String[] date) {
        this.date = date;
    }

    public int[] getHum() {
        return hum;
    }

    public void setHum(int[] hum) {
        this.hum = hum;
    }

    public int[] getPop() {
        return pop;
    }

    public void setPop(int[] pop) {
        this.pop = pop;
    }

    public int[] getPres() {
        return pres;
    }

    public void setPres(int[] pres) {
        this.pres = pres;
    }

    public String[] getPcpn() {
        return pcpn;
    }

    public void setPcpn(String[] pcpn) {
        this.pcpn = pcpn;
    }

    public int[] getMax() {
        return max;
    }

    public void setMax(int[] max) {
        this.max = max;
    }

    public int[] getMin() {
        return min;
    }

    public void setMin(int[] min) {
        this.min = min;
    }

    public int[] getVis() {
        return vis;
    }

    public void setVis(int[] vis) {
        this.vis = vis;
    }

    public int[] getDeg() {
        return deg;
    }

    public void setDeg(int[] deg) {
        this.deg = deg;
    }

    public int[] getApd() {
        return apd;
    }

    public void setApd(int[] apd) {
        this.apd = apd;
    }

    public String[] getDir() {
        return dir;
    }

    public void setDir(String[] dir) {
        this.dir = dir;
    }

    public String[] getSc() {
        return sc;
    }

    public void setSc(String[] sc) {
        this.sc = sc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public int getNow_fl() {
        return now_fl;
    }

    public void setNow_fl(int now_fl) {
        this.now_fl = now_fl;
    }

    public int getNow_hum() {
        return now_hum;
    }

    public void setNow_hum(int now_hum) {
        this.now_hum = now_hum;
    }

    public int getNow_pcpn() {
        return now_pcpn;
    }

    public void setNow_pcpn(int now_pcpn) {
        this.now_pcpn = now_pcpn;
    }

    public int getNow_pres() {
        return now_pres;
    }

    public void setNow_pres(int now_pres) {
        this.now_pres = now_pres;
    }

    public int getNow_tmp() {
        return now_tmp;
    }

    public void setNow_tmp(int now_tmp) {
        this.now_tmp = now_tmp;
    }

    public int getNow_vis() {
        return now_vis;
    }

    public void setNow_vis(int now_vis) {
        this.now_vis = now_vis;
    }

    public int getNow_deg() {
        return now_deg;
    }

    public void setNow_deg(int now_deg) {
        this.now_deg = now_deg;
    }

    public String getNow_dir() {
        return now_dir;
    }

    public void setNow_dir(String now_dir) {
        this.now_dir = now_dir;
    }

    public String getNow_sc() {
        return now_sc;
    }

    public void setNow_sc(String now_sc) {
        this.now_sc = now_sc;
    }

    public String getNow_spd() {
        return now_spd;
    }

    public void setNow_spd(String now_spd) {
        this.now_spd = now_spd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComf_brf() {
        return comf_brf;
    }

    public void setComf_brf(String comf_brf) {
        this.comf_brf = comf_brf;
    }

    public String getComf_txt() {
        return comf_txt;
    }

    public void setComf_txt(String comf_txt) {
        this.comf_txt = comf_txt;
    }

    public String getCw_brf() {
        return cw_brf;
    }

    public void setCw_brf(String cw_brf) {
        this.cw_brf = cw_brf;
    }

    public String getCw_txt() {
        return cw_txt;
    }

    public void setCw_txt(String cw_txt) {
        this.cw_txt = cw_txt;
    }

    public String getDrsg_brf() {
        return drsg_brf;
    }

    public void setDrsg_brf(String drsg_brf) {
        this.drsg_brf = drsg_brf;
    }

    public String getDrsg_txt() {
        return drsg_txt;
    }

    public void setDrsg_txt(String drsg_txt) {
        this.drsg_txt = drsg_txt;
    }

    public String getFlu_brf() {
        return flu_brf;
    }

    public void setFlu_brf(String flu_brf) {
        this.flu_brf = flu_brf;
    }

    public String getFlu_txt() {
        return flu_txt;
    }

    public void setFlu_txt(String flu_txt) {
        this.flu_txt = flu_txt;
    }

    public String getSport_brf() {
        return sport_brf;
    }

    public void setSport_brf(String sport_brf) {
        this.sport_brf = sport_brf;
    }

    public String getSport_txt() {
        return sport_txt;
    }

    public void setSport_txt(String sport_txt) {
        this.sport_txt = sport_txt;
    }

    public String getTrav_brf() {
        return trav_brf;
    }

    public void setTrav_brf(String trav_brf) {
        this.trav_brf = trav_brf;
    }

    public String getTrav_txt() {
        return trav_txt;
    }

    public void setTrav_txt(String trav_txt) {
        this.trav_txt = trav_txt;
    }

    public String getUv_brf() {
        return uv_brf;
    }

    public void setUv_brf(String uv_brf) {
        this.uv_brf = uv_brf;
    }

    public String getUv_txt() {
        return uv_txt;
    }

    public void setUv_txt(String uv_txt) {
        this.uv_txt = uv_txt;
    }
}



