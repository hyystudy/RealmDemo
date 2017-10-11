package com.jccy.realmdemo.realm;

import android.content.Context;

import com.jccy.realmdemo.bean.Student;
import com.jccy.realmdemo.eventbus.EventBusManager;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by heyangyang on 2017/10/10.
 */

public class RealmUtils {

    private static Realm realm;
    private static RealmUtils mInstance;

    public static void initRealmInstance(Context context){
        Realm.init(context);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("hyy.realm")
                .schemaVersion(Migration.REALM_VERSOIN)
                .migration(new Migration())
                .build();
        realm = Realm.getInstance(realmConfiguration);
    }


    public static synchronized RealmUtils getInstance(){
        if (mInstance == null){
            mInstance = new RealmUtils();
        }
        return mInstance;
    }

    /**
     * 插入或者更新数据库数据
     * @param data 插入或者是更新的数据Bean
     */
    public void insertData(final RealmObject data){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(data);
            }
        });
    }


    /**
     * delete student from realm by name(Primary Key)
     * @param name Student的primaryKey Name
     */
    public void deleteStudentById(String name){
        Student student = realm.where(Student.class).equalTo("name", name).findFirst();
        realm.beginTransaction();
        if (student != null){
            student.deleteFromRealm();
        }
        realm.commitTransaction();
    }


    /**
     * @return all students in realm DB
     */
    public List<Student> queryAllStudent(){
        RealmResults<Student> students = realm.where(Student.class).findAll();
        return realm.copyFromRealm(students);
    }

    /**
     * delete delete realm DB all data
     */
    public void deleteAllData(){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.deleteAll();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

                //delete realm DB all data success interface
                EventBusManager.getEventBus().post(new EventBusManager.OnDeleteAllDataSuccessEvent());
            }
        });
    }


}
