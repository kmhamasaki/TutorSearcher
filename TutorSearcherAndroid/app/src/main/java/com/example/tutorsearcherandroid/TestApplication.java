package com.example.tutorsearcherandroid;

import android.app.Activity;
import android.app.Application;

import com.example.tutorsearcherandroid.dagger.DaggerMyAppComponentMock;
import com.example.tutorsearcherandroid.dagger.MyAppComponentMock;
import com.example.tutorsearcherandroid.dagger.MyAppComponent;
import com.example.tutorsearcherandroid.dagger.MyAppModule;
import com.example.tutorsearcherandroid.dagger.MyAppModuleMock;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class TestApplication extends TutorSearcherApp implements HasActivityInjector {
    MyAppComponent myAppComponent;
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        myAppComponent = DaggerMyAppComponentMock.builder()
                .myAppModuleMock(new MyAppModuleMock())
                .build();
    }

    public void setMyAppComponent(MyAppComponentMock myAppComponent) {
        this.myAppComponent = myAppComponent;
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
