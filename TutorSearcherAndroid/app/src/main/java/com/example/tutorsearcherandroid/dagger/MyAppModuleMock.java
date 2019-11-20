package com.example.tutorsearcherandroid.dagger;

import com.example.tutorsearcherandroid.Client;
import com.example.tutorsearcherandroid.ClientTest;
import com.example.tutorsearcherandroid.UpdateProfile;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

import javax.inject.Singleton;

@Module
public abstract class MyAppModuleMock {

/*
    @Provides
    @Singleton
    Client providesClient() {
        return new ClientTest();
    }
*/
@ContributesAndroidInjector
abstract UpdateProfile contributeActivityInjector();
}