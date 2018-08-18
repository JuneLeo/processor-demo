package com.example.songpengfei.myapplication.abtest;

import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventCallback;
import android.hardware.SensorManager;
import android.util.Log;

import com.example.songpengfei.myapplication.abtest.config.ABTestConfig;
import com.example.songpengfei.myapplication.abtest.db.AbTestDBService;
import com.example.songpengfei.myapplication.abtest.model.ABTestModel;
import com.example.songpengfei.myapplication.abtest.reflect.ABTestHandler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static android.content.Context.SENSOR_SERVICE;

public class ABTest {

    private Context context;

    private static ABTest INSTENCE = new ABTest();
    private SensorManager mSensorManager;

    public static ABTest getDefault() {
        return INSTENCE;
    }

    public void init(Context context) {
        if (context == null) {
            throw new NullPointerException("context don‘t null");
        }
        this.context = context.getApplicationContext();
        long l = System.currentTimeMillis();
        ABTestHandler.proxy(); //代理云控
        List<ABTestModel> models = new ArrayList<>();
        //获取本地java数据
        ABTestConfig.initABTestModel(models);
        List<ABTestModel> dbModel = AbTestDBService.getDefault().queryAll(context);
        //过滤数据
        if (dbModel!=null) {
            Iterator<ABTestModel> iterator = models.iterator();
            while (iterator.hasNext()) {
                ABTestModel next = iterator.next();
                if (dbModel.contains(next)) {
                    iterator.remove();
                }
            }
        }
        //将数据存储到db
        AbTestDBService.getDefault().insertAll(models, context);
        Log.d("ABTest", "spend time : " + (System.currentTimeMillis() - l));

    }

    /**
     * 获取全部数据
     * @return
     */
    public List<ABTestModel> getABTestModels(String page) {
        return AbTestDBService.getDefault().queryAllByPage(page, context);
    }

    public String getStringValue(String action, String key) {
        ABTestModel abTestModel = AbTestDBService.getDefault().queryByKeyAndAction(key, action, context);
        if (abTestModel != null) {
            return abTestModel.value;
        }
        return null;
    }

    /**
     * 写入数据
     * @param action
     * @param key
     * @param value
     * @return
     */
    public boolean writeValue(String action, String key, String value) {
        AbTestDBService.getDefault().update(key, action, value, context);
        return true;
    }

//    public void regist(Context context){
//        //获取 SensorManager 负责管理传感器
//        mSensorManager = ((SensorManager) context.getSystemService(SENSOR_SERVICE));
//        if (mSensorManager != null) {
//            //获取加速度传感器
//            Sensor mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//            if (mAccelerometerSensor != null) {
//                mSensorManager.registerListener(new SensorEventCallback() {
//                    @Override
//                    public void onSensorChanged(SensorEvent event) {
//                        super.onSensorChanged(event);
//                    }
//                }, mAccelerometerSensor, SensorManager.SENSOR_DELAY_UI);
//            }
//        }
//
//        Application application;
//        application.registerActivityLifecycleCallbacks();
//    }
//
//    public void unregist(Context context){
//
//    }

}
