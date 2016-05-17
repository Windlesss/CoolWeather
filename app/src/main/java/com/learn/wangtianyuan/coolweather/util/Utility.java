package com.learn.wangtianyuan.coolweather.util;

import android.text.TextUtils;
import android.util.Log;

import com.learn.wangtianyuan.coolweather.db.CoolWeatherDB;
import com.learn.wangtianyuan.coolweather.model.City;
import com.learn.wangtianyuan.coolweather.model.County;
import com.learn.wangtianyuan.coolweather.model.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wangtianyuan on 16/5/16.
 */
public class Utility {

    /*
    * 解析和处理省级数据
    */
    public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB,
                                                               String response) {
        Log.d("text", "handleProvincesResponse...........len:" + response.length());
        if (!TextUtils.isEmpty(response)) {
            StringBuilder provinceAdded = new StringBuilder();
            try {
                JSONObject jsonObject = new JSONObject(response);
                String resultCode = jsonObject.getString("resultcode");
                if (!"200".equals(resultCode)) {
                    Log.e("test", "json结果返回失败" + resultCode);
                    return false;
                }
                JSONArray jsonArray = (JSONArray) jsonObject.get("result");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject resultObject = jsonArray.getJSONObject(i);
                    String provinceName = resultObject.getString("province");
                    if (provinceAdded.indexOf(provinceName) >= 0)
                        continue;
                    Province province = new Province();
                    province.setProvinceName(provinceName);
                    province.setProvinceCode(provinceName);
                    //save to DB
                    coolWeatherDB.saveProvince(province);
                    provinceAdded.append(provinceName);
                    Log.d("test", "[handleProvincesResponse]添加省份：" + provinceName);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }

        return false;
    }

    /*
    * 解析和处理市级数据
    */
    public static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB,
                                               String response,
                                               int provinceId,
                                               String provinceName) {
        Log.d("text", "handleCitiesResponse...........provinceName:" + provinceName);
        if (!TextUtils.isEmpty(response)) {
            StringBuilder cityAdded = new StringBuilder();
            try {
                JSONObject jsonObject = new JSONObject(response);
                String resultCode = jsonObject.getString("resultcode");
                if (!"200".equals(resultCode)) {
                    Log.e("test", "json结果返回失败" + resultCode);
                    return false;
                }
                JSONArray jsonArray = (JSONArray) jsonObject.get("result");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject resultObject = jsonArray.getJSONObject(i);
                    String name = resultObject.getString("province");
                    if (!name.equals(provinceName)) {
                        continue;
                    }
                    String cityName = resultObject.getString("city");
                    if (cityAdded.indexOf(cityName) >= 0)
                        continue;

                    City city = new City();
                    city.setCityName(cityName);
                    city.setCityCode(cityName);
                    city.setProvinceId(provinceId);
                    //save to DB
                    coolWeatherDB.saveCity(city);
                    cityAdded.append(cityName);
                    Log.d("test", "[handleProvincesResponse]添加城市：" + cityName);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    /*
    * 解析和处理县区级数据
    */
    public static boolean handleContiesResponse(CoolWeatherDB coolWeatherDB,
                                                String response,
                                                int cityId,
                                                String cityName) {
        Log.d("text", "handleContiesResponse...........");
        if (!TextUtils.isEmpty(response)) {
            StringBuilder countyAdded = new StringBuilder();
            try {
                JSONObject jsonObject = new JSONObject(response);
                String resultCode = jsonObject.getString("resultcode");
                if (!"200".equals(resultCode)) {
                    Log.e("test", "json结果返回失败" + resultCode);
                    return false;
                }
                JSONArray jsonArray = (JSONArray) jsonObject.get("result");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject resultObject = jsonArray.getJSONObject(i);
                    String name = resultObject.getString("city");
                    if (!name.equals(cityName)) {
                        continue;
                    }
                    String countyName = resultObject.getString("district");
                    if (countyAdded.indexOf(countyName) >= 0)
                        continue;
                    County county = new County();
                    county.setCountyName(countyName);
                    county.setCountyCode(countyName);
                    county.setCityId(cityId);
                    //save to DB
                    coolWeatherDB.saveCounty(county);
                    countyAdded.append(countyName);
                    Log.d("test", "[handleProvincesResponse]添加县区：" + countyName);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

}
