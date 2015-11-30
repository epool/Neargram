package com.nearsoft.neargram.di.modules;

import android.content.Context;

import com.nearsoft.neargram.NeargramApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger 2 application module.
 * Created by epool on 11/23/15.
 */
@Module
public class ApplicationModule {
    private NeargramApplication neargramApplication;

    public ApplicationModule(NeargramApplication neargramApplication) {
        this.neargramApplication = neargramApplication;
    }

    @Singleton
    @Provides
    public Context provideApplicationContext() {
        return neargramApplication.getApplicationContext();
    }
}
