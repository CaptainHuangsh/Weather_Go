package com.example.owen.weathergo.modules.domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by owen on 2017/4/8.
 */

public class Weather implements Serializable{
    @SerializedName("aqi")
    private AqiEntity aqi;


    @SerializedName("basic")
    private BasicEntity basic;


    @SerializedName("now")
    private NowEntity now;


    @SerializedName("status")
    private String status;


    @SerializedName("suggestion")
    private SuggestionEntity suggestion;


    @SerializedName("daily_forecast")
    private List<DailyForecastEntity> dailyForecast;


    public AqiEntity getAqi() {
        return aqi;
    }

    public void setAqi(AqiEntity aqi) {
        this.aqi = aqi;
    }

    public BasicEntity getBasic() {
        return basic;
    }

    public void setBasic(BasicEntity basic) {
        this.basic = basic;
    }

    public NowEntity getNow() {
        return now;
    }

    public void setNow(NowEntity now) {
        this.now = now;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SuggestionEntity getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(SuggestionEntity suggestion) {
        this.suggestion = suggestion;
    }

    public List<DailyForecastEntity> getDailyForecast() {
        return dailyForecast;
    }

    public void setDailyForecast(List<DailyForecastEntity> dailyForecast) {
        this.dailyForecast = dailyForecast;
    }


    @SerializedName("hourly_forecast")
    private List<HourlyForecastEntity> hourlyForecast;

    public List<HourlyForecastEntity> getHourlyForecast() {
        return hourlyForecast;
    }

    public void setHourlyForecast(List<HourlyForecastEntity> hourlyForecast) {
        this.hourlyForecast = hourlyForecast;
    }

    public static class AqiEntity implements Serializable {

        public CityEntity getCity() {
            return city;
        }

        public void setCity(CityEntity city) {
            this.city = city;
        }

        @SerializedName("city")

        private CityEntity city;

        public static class CityEntity implements Serializable {
            @SerializedName("aqi")
            private String aqi;
            @SerializedName("co")
            private String co;
            @SerializedName("no2")
            private String no2;
            @SerializedName("o3")
            private String o3;
            @SerializedName("pm10")
            private String pm10;
            @SerializedName("pm25")
            private String pm25;
            @SerializedName("qlty")
            private String qlty;

            public String getAqi() {
                return aqi;
            }

            public void setAqi(String aqi) {
                this.aqi = aqi;
            }

            public String getCo() {
                return co;
            }

            public void setCo(String co) {
                this.co = co;
            }

            public String getNo2() {
                return no2;
            }

            public void setNo2(String no2) {
                this.no2 = no2;
            }

            public String getO3() {
                return o3;
            }

            public void setO3(String o3) {
                this.o3 = o3;
            }

            public String getPm10() {
                return pm10;
            }

            public void setPm10(String pm10) {
                this.pm10 = pm10;
            }

            public String getPm25() {
                return pm25;
            }

            public void setPm25(String pm25) {
                this.pm25 = pm25;
            }

            public String getQlty() {
                return qlty;
            }

            public void setQlty(String qlty) {
                this.qlty = qlty;
            }

            public String getSo2() {
                return so2;
            }

            public void setSo2(String so2) {
                this.so2 = so2;
            }

            @SerializedName("so2")
            private String so2;
        }
    }

    public static class BasicEntity implements Serializable {
        @SerializedName("city")
        private String city;
        @SerializedName("cnty")
        private String cnty;
        @SerializedName("id")
        private String id;
        @SerializedName("lat")
        private String lat;
        @SerializedName("lon")
        private String lon;
        /**
         * loc : 2016-02-18 21:04
         * utc : 2016-02-18 13:04
         */

        @SerializedName("update")
        private UpdateEntity update;

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

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public UpdateEntity getUpdate() {
            return update;
        }

        public void setUpdate(UpdateEntity update) {
            this.update = update;
        }

        public static class UpdateEntity implements Serializable {
            @SerializedName("loc")
            private String loc;

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

            @SerializedName("utc")

            private String utc;
        }
    }

    public static class NowEntity implements Serializable {
        /**
         * code : 101
         * txt : 多云
         */

        @SerializedName("cond")
        private CondEntity cond;
        @SerializedName("fl")
        private String fl;
        @SerializedName("hum")
        private String hum;
        @SerializedName("pcpn")
        private String pcpn;
        @SerializedName("pres")
        private String pres;
        @SerializedName("tmp")
        private String tmp;
        @SerializedName("vis")
        private String vis;

        public CondEntity getCond() {
            return cond;
        }

        public void setCond(CondEntity cond) {
            this.cond = cond;
        }

        public String getFl() {
            return fl;
        }

        public void setFl(String fl) {
            this.fl = fl;
        }

        public String getHum() {
            return hum;
        }

        public void setHum(String hum) {
            this.hum = hum;
        }

        public String getPcpn() {
            return pcpn;
        }

        public void setPcpn(String pcpn) {
            this.pcpn = pcpn;
        }

        public String getPres() {
            return pres;
        }

        public void setPres(String pres) {
            this.pres = pres;
        }

        public String getTmp() {
            return tmp;
        }

        public void setTmp(String tmp) {
            this.tmp = tmp;
        }

        public String getVis() {
            return vis;
        }

        public void setVis(String vis) {
            this.vis = vis;
        }

        public WindEntity getWind() {
            return wind;
        }

        public void setWind(WindEntity wind) {
            this.wind = wind;
        }

        /**
         * deg : 20
         * dir : 西北风
         * sc : 4-5
         * spd : 17
         */

        @SerializedName("wind")
        private WindEntity wind;

        public static class CondEntity implements Serializable {
            @SerializedName("code")
            private String code;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }

            @SerializedName("txt")
            private String txt;
        }

        public static class WindEntity implements Serializable {
            @SerializedName("deg")
            private String deg;
            @SerializedName("dir")
            private String dir;
            @SerializedName("sc")
            private String sc;

            public String getDeg() {
                return deg;
            }

            public void setDeg(String deg) {
                this.deg = deg;
            }

            public String getDir() {
                return dir;
            }

            public void setDir(String dir) {
                this.dir = dir;
            }

            public String getSc() {
                return sc;
            }

            public void setSc(String sc) {
                this.sc = sc;
            }

            public String getSpd() {
                return spd;
            }

            public void setSpd(String spd) {
                this.spd = spd;
            }

            @SerializedName("spd")

            private String spd;
        }
    }

    public static class SuggestionEntity implements Serializable {
        /**
         * brf : 较舒适
         * txt : 白天天气阴沉，会感到有点儿凉，但大部分人完全可以接受。
         */

        @SerializedName("comf")
        private ComfEntity comf;
        /**
         * brf : 较适宜
         * txt : 较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。
         */

        @SerializedName("cw")
        private CwEntity cw;
        /**
         * brf : 较冷
         * txt : 建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。
         */

        @SerializedName("drsg")
        private DrsgEntity drsg;
        /**
         * brf : 较易发
         * txt : 昼夜温差较大，较易发生感冒，请适当增减衣服。体质较弱的朋友请注意防护。
         */

        @SerializedName("flu")
        private FluEntity flu;
        /**
         * brf : 较适宜
         * txt : 阴天，较适宜进行各种户内外运动。
         */

        @SerializedName("sport")
        private SportEntity sport;
        /**
         * brf : 适宜
         * txt : 天气较好，温度适宜，总体来说还是好天气哦，这样的天气适宜旅游，您可以尽情地享受大自然的风光。
         */

        @SerializedName("trav")
        private TravEntity trav;

        public ComfEntity getComf() {
            return comf;
        }

        public void setComf(ComfEntity comf) {
            this.comf = comf;
        }

        public CwEntity getCw() {
            return cw;
        }

        public void setCw(CwEntity cw) {
            this.cw = cw;
        }

        public DrsgEntity getDrsg() {
            return drsg;
        }

        public void setDrsg(DrsgEntity drsg) {
            this.drsg = drsg;
        }

        public FluEntity getFlu() {
            return flu;
        }

        public void setFlu(FluEntity flu) {
            this.flu = flu;
        }

        public SportEntity getSport() {
            return sport;
        }

        public void setSport(SportEntity sport) {
            this.sport = sport;
        }

        public TravEntity getTrav() {
            return trav;
        }

        public void setTrav(TravEntity trav) {
            this.trav = trav;
        }

        public UvEntity getUv() {
            return uv;
        }

        public void setUv(UvEntity uv) {
            this.uv = uv;
        }

        /**
         * brf : 最弱
         * txt : 属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。
         */

        @SerializedName("uv")
        private UvEntity uv;

        public static class ComfEntity implements Serializable {
            @SerializedName("brf")
            private String brf;

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }

            @SerializedName("txt")
            private String txt;
        }

        public static class CwEntity implements Serializable {
            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }

            @SerializedName("brf")
            private String brf;
            @SerializedName("txt")
            private String txt;
        }

        public static class DrsgEntity implements Serializable {
            @SerializedName("brf")
            private String brf;
            @SerializedName("txt")
            private String txt;

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public static class FluEntity implements Serializable {
            @SerializedName("brf")
            private String brf;
            @SerializedName("txt")
            private String txt;

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public static class SportEntity implements Serializable {
            @SerializedName("brf")
            private String brf;
            @SerializedName("txt")
            private String txt;

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public static class TravEntity implements Serializable {
            @SerializedName("brf")
            private String brf;

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }

            @SerializedName("txt")
            private String txt;
        }

        public static class UvEntity implements Serializable {
            @SerializedName("brf")
            private String brf;
            @SerializedName("txt")
            private String txt;

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }
    }

    public static class DailyForecastEntity implements Serializable {
        /**
         * sr : 07:30
         * ss : 18:44
         */

        @SerializedName("astro")
        private AstroEntity astro;
        /**
         * code_d : 100
         * code_n : 104
         * txt_d : 晴
         * txt_n : 阴
         */

        @SerializedName("cond")
        private CondEntity cond;
        @SerializedName("date")
        private String date;
        @SerializedName("hum")
        private String hum;
        @SerializedName("pcpn")
        private String pcpn;
        @SerializedName("pop")
        private String pop;
        @SerializedName("pres")
        private String pres;
        /**
         * max : 19
         * min : 7
         */

        @SerializedName("tmp")
        private TmpEntity tmp;
        @SerializedName("vis")
        private String vis;
        /**
         * deg : 54
         * dir : 无持续风向
         * sc : 微风
         * spd : 6
         */

        @SerializedName("wind")
        private WindEntity wind;

        public AstroEntity getAstro() {
            return astro;
        }

        public void setAstro(AstroEntity astro) {
            this.astro = astro;
        }

        public CondEntity getCond() {
            return cond;
        }

        public void setCond(CondEntity cond) {
            this.cond = cond;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getHum() {
            return hum;
        }

        public void setHum(String hum) {
            this.hum = hum;
        }

        public String getPcpn() {
            return pcpn;
        }

        public void setPcpn(String pcpn) {
            this.pcpn = pcpn;
        }

        public String getPop() {
            return pop;
        }

        public void setPop(String pop) {
            this.pop = pop;
        }

        public String getPres() {
            return pres;
        }

        public void setPres(String pres) {
            this.pres = pres;
        }

        public TmpEntity getTmp() {
            return tmp;
        }

        public void setTmp(TmpEntity tmp) {
            this.tmp = tmp;
        }

        public String getVis() {
            return vis;
        }

        public void setVis(String vis) {
            this.vis = vis;
        }

        public WindEntity getWind() {
            return wind;
        }

        public void setWind(WindEntity wind) {
            this.wind = wind;
        }

        public static class AstroEntity implements Serializable {
            @SerializedName("sr")
            private String sr;

            public String getSr() {
                return sr;
            }

            public void setSr(String sr) {
                this.sr = sr;
            }

            public String getSs() {
                return ss;
            }

            public void setSs(String ss) {
                this.ss = ss;
            }

            @SerializedName("ss")

            private String ss;
        }

        public static class CondEntity implements Serializable {
            @SerializedName("code_d")
            private String codeD;
            @SerializedName("code_n")
            private String codeN;
            @SerializedName("txt_d")
            private String txtD;
            @SerializedName("txt_n")
            private String txtN;

            public String getCodeD() {
                return codeD;
            }

            public void setCodeD(String codeD) {
                this.codeD = codeD;
            }

            public String getCodeN() {
                return codeN;
            }

            public void setCodeN(String codeN) {
                this.codeN = codeN;
            }

            public String getTxtD() {
                return txtD;
            }

            public void setTxtD(String txtD) {
                this.txtD = txtD;
            }

            public String getTxtN() {
                return txtN;
            }

            public void setTxtN(String txtN) {
                this.txtN = txtN;
            }
        }

        public static class TmpEntity implements Serializable {
            @SerializedName("max")
            private String max;
            @SerializedName("min")
            private String min;

            public String getMax() {
                return max;
            }

            public void setMax(String max) {
                this.max = max;
            }

            public String getMin() {
                return min;
            }

            public void setMin(String min) {
                this.min = min;
            }
        }

        public static class WindEntity implements Serializable {
            @SerializedName("deg")
            private String deg;
            @SerializedName("dir")
            private String dir;
            @SerializedName("sc")
            private String sc;
            @SerializedName("spd")
            private String spd;

            public String getDeg() {
                return deg;
            }

            public void setDeg(String deg) {
                this.deg = deg;
            }

            public String getDir() {
                return dir;
            }

            public void setDir(String dir) {
                this.dir = dir;
            }

            public String getSc() {
                return sc;
            }

            public void setSc(String sc) {
                this.sc = sc;
            }

            public String getSpd() {
                return spd;
            }

            public void setSpd(String spd) {
                this.spd = spd;
            }
        }
    }

    public static class HourlyForecastEntity implements Serializable {
        @SerializedName("date")
        private String date;
        @SerializedName("hum")
        private String hum;
        @SerializedName("pop")
        private String pop;
        @SerializedName("pres")
        private String pres;
        @SerializedName("tmp")
        private String tmp;
        /**
         * deg : 13
         * dir : 东北风
         * sc : 微风
         * spd : 16
         */

        @SerializedName("wind")
        private WindEntity wind;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getHum() {
            return hum;
        }

        public void setHum(String hum) {
            this.hum = hum;
        }

        public String getPop() {
            return pop;
        }

        public void setPop(String pop) {
            this.pop = pop;
        }

        public String getPres() {
            return pres;
        }

        public void setPres(String pres) {
            this.pres = pres;
        }

        public String getTmp() {
            return tmp;
        }

        public void setTmp(String tmp) {
            this.tmp = tmp;
        }

        public WindEntity getWind() {
            return wind;
        }

        public void setWind(WindEntity wind) {
            this.wind = wind;
        }

        public static class WindEntity implements Serializable {
            @SerializedName("deg")
            private String deg;
            @SerializedName("dir")
            private String dir;
            @SerializedName("sc")
            private String sc;
            @SerializedName("spd")
            private String spd;


            public String getDeg() {
                return deg;
            }

            public void setDeg(String deg) {
                this.deg = deg;
            }

            public String getDir() {
                return dir;
            }

            public void setDir(String dir) {
                this.dir = dir;
            }

            public String getSc() {
                return sc;
            }

            public void setSc(String sc) {
                this.sc = sc;
            }

            public String getSpd() {
                return spd;
            }

            public void setSpd(String spd) {
                this.spd = spd;
            }
        }
    }

}
