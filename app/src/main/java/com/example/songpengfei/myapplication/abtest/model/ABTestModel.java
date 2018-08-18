package com.example.songpengfei.myapplication.abtest.model;

import android.text.TextUtils;

public class ABTestModel {

    public final String page; //activity 类
    public final String action;
    public final String key;       // show
    public final String values;  // show
    public String value;     // show  default

    public String keyName;


    public ABTestModel(String page, String action, String key, String keyName, String values) {
        checkModel(page, action, key, value);
        this.page = page;
        this.action = action;
        this.key = key;
        this.keyName = keyName;
        this.values = values;
        this.value = values.substring(0, values.indexOf(",") - 1);   //设置默认值为数据第一个

    }

    public ABTestModel(String page, String action, String key, String keyName, String values, String value) {
        this.page = page;
        this.action = action;
        this.key = key;
        this.keyName = keyName;
        this.values = values;
        this.value = value;
    }

    private void checkModel(String page, String action, String key, Object... values) {
        if (TextUtils.isEmpty(page)) {
            throw new IllegalArgumentException("page don't null or \"\"");
        } else if (TextUtils.isEmpty(action)) {
            throw new IllegalArgumentException("action don't null or \"\"");
        } else if (TextUtils.isEmpty(key)) {
            throw new IllegalArgumentException("key don't null or \"\"");
        } else if (values == null || values.length == 0) {
            throw new IllegalArgumentException("value don't null or values.length = 0");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof ABTestModel)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (((ABTestModel) obj).key.equals(key) && ((ABTestModel) obj).action.equals(action)) {
            return true;
        }

        return false;
    }
}
