package com.example.tutorsearcherandroid.dagger;

import com.example.tutorsearcherandroid.Client;
import com.example.tutorsearcherandroid.ClientTest;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class MyAppModuleMock {

    @Provides
    @Singleton
    Client providesClient() {
        return new ClientTest();
    }

}