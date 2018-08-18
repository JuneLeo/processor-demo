package com.example.songpengfei.myapplication.abtest.reflect;

import com.example.songpengfei.myapplication.abtest.ABTest;
import com.example.songpengfei.myapplication.abtest.config.ICloudConfigGetter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ABTestHandler implements InvocationHandler {

    private Object original;

    public ABTestHandler(Object original) {
        this.original = original;
    }

    /**
     * 直接将Binder代理
     * 流程：
     *  获取云控字段 ->  查询本地数据库  ->  有，直接返回，没有，获取云控数据
     * 初始化后即可代理
     */
    public static void proxy() {
        try {
            Class clz = Class.forName("com.cleanmaster.base.ipc.ServiceManager");
            Method method = clz.getDeclaredMethod("getInstance");
            Object sm = method.invoke(null);
            Method mCloudConfig = clz.getDeclaredMethod("getService", String.class);
            Object original = mCloudConfig.invoke(sm, ICloudConfigGetter.class.getName());
            Field field = clz.getDeclaredField("mCloudConfigGetter");
            field.setAccessible(true);
            Object proxy = Proxy.newProxyInstance(ICloudConfigGetter.class.getClassLoader(), original.getClass().getInterfaces(),
                    new ABTestHandler(original));
            field.set(sm, proxy);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        switch (method.getName()) {
            case "getData":
                break;
            case "getDatas":
                break;
            case "getStringValue"://方法名
                //先取本地数据库中的字段，取不到则取云控数据
                String stringValue = ABTest.getDefault().getStringValue(String.valueOf(args[1]), String.valueOf(args[2]));
                if (stringValue != null) {
                    return stringValue;
                }
                break;
            case "getIntValue":
                break;
            case "getLongValue":
                break;
            case "getBooleanValue":
                break;
            case "getDoubleValue":
                break;
        }


        return method.invoke(original, args);
    }
}
