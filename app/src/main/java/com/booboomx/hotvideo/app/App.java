package com.booboomx.hotvideo.app;

import android.app.Activity;
import android.app.Application;

import com.booboomx.hotvideo.db.RealmHelper;

import java.util.HashSet;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.rx.RealmObservableFactory;

/**
 * Created by booboomx on 17/2/28.
 */

public class App extends Application {

    private static App instance;
    private Set<Activity>mActivities;


    public static App getInstance(){
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;


        initRealm();


    }

    private void initRealm() {

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(RealmHelper.DB_NAME)
                .schemaVersion(1)
                .rxFactory(new RealmObservableFactory())
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);


    }


    public void registerActivities(Activity activity){
        if(mActivities==null){
            mActivities=new HashSet<>();

        }
        mActivities.add(activity);
    }


    public void unRegisterActivity(Activity activity){
        if (activity != null) {
            mActivities.remove(activity);
        }
    }

    public void exitApp(){
        if(mActivities!=null){
            synchronized (mActivities){
                for (Activity act:mActivities) {

                    if(act!=null&& !act.isFinishing()){
                        act.finish();

                    }

                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
