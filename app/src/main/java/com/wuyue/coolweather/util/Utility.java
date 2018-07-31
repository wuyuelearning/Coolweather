package com.wuyue.coolweather.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.wuyue.coolweather.db.City;
import com.wuyue.coolweather.db.County;
import com.wuyue.coolweather.db.Province;
import com.wuyue.coolweather.gson.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wuyue on 2018/7/28.
 */

public class Utility {

    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String WEATHER_ID = "weather_id";

    /**
     * 解析和处理服务器返回的省级数据
     */
    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(object.getString(NAME));
                    province.setProvinceCode(object.getInt(ID));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的市级数据
     */

    public static boolean handleCityResponse(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    City city = new City();
                    city.setCityName(object.getString(NAME));
                    city.setCityCode(object.getInt(ID));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的县级数据
     */

    public static boolean handleCountyResponse(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray array = new JSONArray(response);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(object.getString(NAME));
                    county.setWeatherId(object.getString(WEATHER_ID));
                    Log.d("weather","weatherId3:"+object.getString(WEATHER_ID));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    /**
     * 将返回的Json 数据解析成Weather 实体类
     */
    public static Weather handleWeatherResponse(String response){
        try{
            JSONObject jsonObject =new JSONObject(response);
            JSONArray jsonArray =jsonObject.getJSONArray("HeWeather");
            String weatherContent =jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,Weather.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
