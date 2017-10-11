package com.jccy.realmdemo.realm;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

/***************************** NOTE: *********************************************
 * The API for migration is currently using internal lower level classes that will
 * be replaced by a new API very soon! Until then you will have to explore and use
 * below example as inspiration.
 *********************************************************************************
 */

/**
 * Created by heyangyang on 2017/10/10.
 */

public class Migration implements RealmMigration {

    /**
     * 与上一版本比较版本号自增1
     */
    public static int REALM_VERSOIN = 1;

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

    }
}
