package com.sun.app.sun.util;

import android.text.TextUtils;

import com.sun.app.sun.model.City;
import com.sun.app.sun.model.County;
import com.sun.app.sun.model.Province;
import com.sun.app.sun.model.SunDB;

/**
 * Created by Administrator on 2016/6/10.
 */
public class Utility {
    //解析和处理服务器返回的省级数据
    public synchronized static boolean handleProvinceResponse(SunDB sunDB,String response){
        if(!TextUtils.isEmpty(response)){//用工具类去判定空，不会产生NullPointerException
            String[] allProvinces = response.split(",");
            if(allProvinces != null && allProvinces.length > 0){
                for(String p:allProvinces){
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(array[0]);
                    province.setProvinceName(array[1]);
                    //将解析出来的数据存储到Province表
                    sunDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }
    //解析和处理服务器返回的市级数据
    public synchronized static boolean handleCitiesResponse(SunDB sunDB,String response,int provinceId){
        if(!TextUtils.isEmpty(response)){
            String[] allcities = response.split(",");
            if(allcities != null && allcities.length > 0){
                for( String c : allcities){
                    String[] array = c.split("\\|");
                    City city = new City();
                    city.setCityCode(array[0]);
                    city.setCityName(array[1]);
                    city.setProvinceId(provinceId);
                    //将解析出来的数据存储到City表
                    sunDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }
    //解析和处理服务器返回的县级数据
    public synchronized static boolean handleCountyResponse(SunDB sunDB,String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            String[] allCounties = response.split(",");
            if(allCounties != null && allCounties.length > 0){
                for(String c : allCounties){
                    String[] array = c.split("\\|");
                    County county = new County();
                    county.setCountyCode(array[0]);
                    county.setCountyName(array[1]);
                    county.setCityId(cityId);
                    //将解析出来的数据存储到County表中
                    sunDB.saveCountry(county);
                }
                return true;
            }
        }
        return false;
    }
}
