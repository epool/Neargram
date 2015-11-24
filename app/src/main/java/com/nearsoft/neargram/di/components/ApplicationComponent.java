package com.nearsoft.neargram.di.components;

import com.nearsoft.neargram.di.modules.ApplicationModule;
import com.nearsoft.neargram.di.modules.RetrofitModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Dagger 2 application component.
 * Created by epool on 11/23/15.
 */
@Singleton
@Component(modules = {ApplicationModule.class, RetrofitModule.class})
public interface ApplicationComponent {
}
