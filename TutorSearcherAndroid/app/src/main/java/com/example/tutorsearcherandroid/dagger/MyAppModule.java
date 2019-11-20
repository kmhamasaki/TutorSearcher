package com.example.tutorsearcherandroid.dagger;

import com.example.tutorsearcherandroid.Client;
import com.example.tutorsearcherandroid.UpdateProfile;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
@Module
public abstract class MyAppModule {

/*
    @Provides
    @Singleton
    Client providesClient() {
        return new Client();
    }
*/
@ContributesAndroidInjector
abstract UpdateProfile contributeActivityInjector();

}