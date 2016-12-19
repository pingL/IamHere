package com.pingl.demo.iamhere;

import android.app.Application;

/**
 * Created by pingL on 2016/12/2,下午3:08.
 */

public class MyApplication extends Application {

    private static MyApplication myApplication;



    public MyApplication() {

    }

    public static synchronized MyApplication getInstance() {
        return  myApplication;
    }
}
