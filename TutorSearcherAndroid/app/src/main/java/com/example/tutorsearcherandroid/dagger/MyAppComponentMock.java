package com.example.tutorsearcherandroid.dagger;

import com.example.tutorsearcherandroid.SignupActivity;
import com.example.tutorsearcherandroid.TutorSearcherApp;
import com.example.tutorsearcherandroid.UpdateProfile;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(modules = {
        MyAppModuleMock.class,
        AndroidInjectionModule.class
})
public interface MyAppComponentMock extends MyAppComponent {
    void inject(UpdateProfile activity);
    void inject(SignupActivity activity);

    @Component.Builder
    interface Builder {
        MyAppComponentMock build();

        @BindsInstance
        Builder myAppModuleMock(MyAppModuleMock app);
    }

}