package com.example.songpengfei.myapplication.abtest.config;

import android.text.TextUtils;

import com.example.songpengfei.myapplication.abtest.model.ABTestModel;
import com.example.songpengfei.myapplication.abtest.ui.ABTestActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//注册入口
public class ABTestConfig {


    /**
     * 注册页面需要本地可以改变的云控数据
     */
    public static void initABTestModel(List<ABTestModel> a) {
        //注册
        regist(a, ABTestActivity.class.getName(), "key1-key1-key1key1-key1key1", "action", "key1", "0,1,2","2");

        //page,key,action,keyname,values,value(default)

    }

    private static void regist(List<ABTestModel> a, String page, String key, String action, String keyName, String values) {
        ABTestModel newModel = new ABTestModel(page, action, key, keyName, values);
        a.add(newModel);
    }

    private static void regist(List<ABTestModel> a, String page, String key, String action, String keyName, String values, String value) {
        ABTestModel newModel = new ABTestModel(page, action, key, keyName, values, value);
        a.add(newModel);
    }
}
