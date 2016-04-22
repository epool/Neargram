package com.nearsoft.neargram.di.modules;

import android.content.Context;

import com.nearsoft.neargram.NeargramApplication;
import com.nearsoft.neargram.model.realm.migrations.Migration1;
import com.nearsoft.neargram.util.SyncUtil;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Dagger 2 application module.
 * Created by epool on 11/23/15.
 */
@Module
public class ApplicationModule {
    private NeargramApplication neargramApplication;

    public ApplicationModule(NeargramApplication neargramApplication) {
        this.neargramApplication = neargramApplication;

        RealmConfiguration config = new RealmConfiguration.Builder(this.neargramApplication.getApplicationContext())
                .name("neargram.realm")
                .schemaVersion(2)
                .migration(new Migration1())
                .build();
        Realm.setDefaultConfiguration(config);

        SyncUtil.CreateSyncAccount(this.neargramApplication.getApplicationContext());
    }

    @Singleton
    @Provides
    public Context provideApplicationContext() {
        return neargramApplication.getApplicationContext();
    }
}
