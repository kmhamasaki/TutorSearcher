package com.example.tutorsearcherandroid;

import android.app.Application;
import dagger.Component;

import com.example.tutorsearcherandroid.dagger.*;
import com.example.tutorsearcherandroid.dagger.DaggerMyAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.AndroidInjectionModule;

import android.app.Activity;
import javax.inject.Inject;

public class TutorSearcherApp extends Application implements HasActivityInjector {
    MyAppComponent myAppComponent;
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        myAppComponent = DaggerMyAppComponent.builder()
                .myAppModule(new MyAppModule())
                .build();
    }

    public void setMyAppComponent(MyAppComponent myAppComponent) {
        this.myAppComponent = myAppComponent;
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
