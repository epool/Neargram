package com.nearsoft.neargram;

import android.app.Application;

import com.nearsoft.neargram.di.components.ApplicationComponent;
import com.nearsoft.neargram.di.components.DaggerApplicationComponent;
import com.nearsoft.neargram.di.modules.ApplicationModule;
import com.nearsoft.neargram.di.modules.RetrofitModule;

/**
 * Neargram base application.
 * Created by epool on 11/23/15.
 */
public class NeargramApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .retrofitModule(new RetrofitModule("https://api.instagram.com/v1/"))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
