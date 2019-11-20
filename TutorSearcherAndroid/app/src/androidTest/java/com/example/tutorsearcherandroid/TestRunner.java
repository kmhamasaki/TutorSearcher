package com.example.tutorsearcherandroid;

import androidx.test.runner.AndroidJUnitRunner;

import android.app.Application;
import android.content.Context;

public class TestRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) {
        try {
            return super.newApplication(cl, TestApplication.class.getName(), context);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
