package com.nearsoft.neargram.di.components;

import com.nearsoft.neargram.di.modules.ActivityModule;
import com.nearsoft.neargram.di.scopes.PerActivity;

import dagger.Component;

/**
 * Dagger 2 activity component.
 * Created by epool on 11/23/15.
 */
@PerActivity
@Component(dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class})
public interface ActivityComponent {
}
