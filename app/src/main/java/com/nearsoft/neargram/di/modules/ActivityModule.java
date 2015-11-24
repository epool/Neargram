package com.nearsoft.neargram.di.modules;

import com.nearsoft.neargram.di.scopes.PerActivity;
import com.nearsoft.neargram.view.activities.BaseActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger 2 activity module.
 * Created by epool on 11/23/15.
 */
@Module
public class ActivityModule {
    private BaseActivity baseActivity;

    public ActivityModule(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @PerActivity
    @Provides
    public BaseActivity providesBaseActivity() {
        return baseActivity;
    }
}
