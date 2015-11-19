package com.lostad.app.demo.util;


import android.widget.TextView;

import com.lostad.app.base.util.Validator;

import java.util.HashMap;
import java.util.Map;

public class BusyUtil {


    public static String getCardTypeCode(String code){

        String name = getCardeTypeMap().get(code);
        if(Validator.isBlank(name)){
            name = "未知类型";
        }

        return name;
    }

    public static Map<String,String> getCardeTypeMap(){
        Map m = new HashMap();
        m.put("0","身份证");
        m.put("1","护  照");
        m.put("2","台胞证");
        m.put("3","港澳通行证");

        return  m;
    }

    public static void setUISex(TextView et_sex, String xb) {
        et_sex.setHint(xb);
        if("0".equals(xb)){
            et_sex.setText("男");
        }else if("1".equals(xb)){
            et_sex.setText("女");
        }else{
            et_sex.setText("未设置");
        }

    }
    //'代办票务出行:1001_DBPW, 自主出行:1002_ZZCX',
    public static String getTravelType(String code) {
        if("1001_DBPW".equals(code)){
            return "代办票务";
        }else if("1002_ZZCX".equals(code)){
            return "自主出行";
        }

        return "未知";
    }


    /**
     * 选择套餐:1001_XZTC,录入信息:1002_LRXX, 选择出行:1003_XZCX,
     * 订单已提交:1004_YTJ,订单已支付:1005_YZF,
     * 订单已完成:1006_YWC,申请退款1007_SQTK,已退款:1008_YTK,订单已取消:1009_YQX
     */
    public static String getOrderStatus(String code) {

        if("1001_XZTC".equals(code)){
            return "选择套餐";
        }else if("1002_LRXX".equals(code)){
            return "录入信息";
        }else if("1003_XZCX".equals(code)){
            return "选择出行";
        }else if("1004_YTJ".equals(code)){
            return "订单已提交";
        }else if("1005_YZF".equals(code)){
            return "订单已支付";
        }else if("1006_YWC".equals(code)){
            return "订单已完成";
        }else if("1007_SQTK".equals(code)){
            return "申请退款";
        }else if("1008_YTK".equals(code)){
            return "申请退款";
        }else if("1009_YQX".equals(code)){
            return "订单已取消";
        }

        return "未知状态";
    }
}