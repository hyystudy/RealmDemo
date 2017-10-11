package com.jccy.realmdemo;

import android.app.Application;

import com.jccy.realmdemo.realm.RealmUtils;

/**
 * Created by heyangyang on 2017/10/10.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        RealmUtils.initRealmInstance(this);
    }
}
