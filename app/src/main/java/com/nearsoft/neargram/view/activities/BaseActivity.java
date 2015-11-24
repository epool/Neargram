package com.nearsoft.neargram.view.activities;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.nearsoft.neargram.NeargramApplication;
import com.nearsoft.neargram.R;
import com.nearsoft.neargram.di.components.ActivityComponent;
import com.nearsoft.neargram.di.components.ApplicationComponent;
import com.nearsoft.neargram.di.components.DaggerActivityComponent;
import com.nearsoft.neargram.di.modules.ActivityModule;

/**
 * Base activity.
 * Created by epool on 11/23/15.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private ViewDataBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int layoutResourceId = getLayoutResourceId();

        binding = DataBindingUtil.setContentView(this, layoutResourceId);

        Toolbar toolbar = (Toolbar) findViewById(getToolbarId());
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        ActivityComponent activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();

        injectComponent(activityComponent);
    }

    protected abstract int getLayoutResourceId();

    protected int getToolbarId() {
        return R.id.toolbar;
    }

    protected <T extends ViewDataBinding> T getBinding(Class<T> clazz) {
        return clazz.cast(binding);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((NeargramApplication) getApplication()).getApplicationComponent();
    }

    protected void injectComponent(ActivityComponent activityComponent) {

    }
}
