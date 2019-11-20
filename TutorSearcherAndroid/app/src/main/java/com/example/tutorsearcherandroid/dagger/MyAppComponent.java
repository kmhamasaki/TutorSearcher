package com.example.tutorsearcherandroid.dagger;

import android.app.Activity;
import javax.inject.Singleton;
import dagger.Component;
import dagger.BindsInstance;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

import com.example.tutorsearcherandroid.MainActivity;
import com.example.tutorsearcherandroid.TutorSearcherApp;
import com.example.tutorsearcherandroid.UpdateProfile;

@Singleton
@Component(modules = {
        MyAppModule.class,
        AndroidInjectionModule.class
})
public interface MyAppComponent extends AndroidInjector<TutorSearcherApp> {
    void inject(UpdateProfile activity);

    @Component.Builder
    interface Builder {
        MyAppComponent build();

        @BindsInstance
        Builder myAppModule(MyAppModule app);
    }

}