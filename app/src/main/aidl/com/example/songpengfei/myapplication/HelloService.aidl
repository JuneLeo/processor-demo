// HelloService.aidl
package com.example.songpengfei.myapplication;

// Declare any non-default types here with import statements

interface HelloService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    String get(String key);
    boolean put(String key,String value);
}
