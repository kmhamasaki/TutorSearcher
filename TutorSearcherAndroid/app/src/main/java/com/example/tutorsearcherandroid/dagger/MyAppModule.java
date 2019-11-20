package com.example.tutorsearcherandroid.dagger;

import com.example.tutorsearcherandroid.Client;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MyAppModule {

    @Provides
    @Singleton
    Client providesClient() {
        return new Client();
    }


}