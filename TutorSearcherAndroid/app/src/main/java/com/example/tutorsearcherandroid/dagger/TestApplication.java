package com.example.tutorsearcherandroid.dagger;

import android.app.Activity;
import android.app.Application;

import com.example.tutorsearcherandroid.TutorSearcherApp;
import com.example.tutorsearcherandroid.dagger.DaggerMyAppComponent;
import com.example.tutorsearcherandroid.dagger.DaggerMyAppComponentMock;
import com.example.tutorsearcherandroid.dagger.MyAppComponentMock;
import com.example.tutorsearcherandroid.dagger.MyAppComponent;
import com.example.tutorsearcherandroid.dagger.MyAppModule;
import com.example.tutorsearcherandroid.dagger.MyAppModuleMock;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class TestApplication extends TutorSearcherApp implements HasActivityInjector {
    public MyAppComponentMock myAppComponent;
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
        myAppComponent.inject(this);
        return dispatchingAndroidInjector;
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        //myAppComponent = DaggerMyAppComponentMock.builder().myAppModuleMock(new MyAppModuleMock()).build();
        myAppComponent.inject(this);
        return myAppComponent;
    }
}
