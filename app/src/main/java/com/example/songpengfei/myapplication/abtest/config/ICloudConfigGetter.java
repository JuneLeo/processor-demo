/*
 * This file is auto-generated.  DO NOT MODIFY.
 */
package com.example.songpengfei.myapplication.abtest.config;

import java.util.List;

public interface ICloudConfigGetter  {


    public String getData(int func_tion, String section) throws android.os.RemoteException;

    public List<String> getDatas(int func_tion, String section) throws android.os.RemoteException;

    public String getStringValue(int func_tion, String section, String key, String defValue) throws android.os.RemoteException;

    public int getIntValue(int func_tion, String section, String key, int defValue) throws android.os.RemoteException;

    public long getLongValue(int func_tion,String section, String key, long defValue) throws android.os.RemoteException;

    public boolean getBooleanValue(int func_tion,String section, String key, boolean defValue) throws android.os.RemoteException;

    public double getDoubleValue(int func_tion, String section, String key, double defValue) throws android.os.RemoteException;
}
