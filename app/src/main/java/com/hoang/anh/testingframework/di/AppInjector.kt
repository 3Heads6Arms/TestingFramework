package com.hoang.anh.testingframework.di

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.hoang.anh.testingframework.MyApplication
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

object AppInjector {
    fun inject(app: MyApplication) {
        val appComponent = DaggerAppComponent.builder()
            .application(app)
            .build()
            .inject(app)

        app.registerActivityLifecycleCallbacks(object : DefaultActivityLifeCycleCallbacks() {
            override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
                injectActivity(activity)
            }
        })

    }

    private fun injectActivity(activity: Activity) {
        if (activity is HasSupportFragmentInjector) {
            AndroidInjection.inject(activity)
        }

        if (activity is FragmentActivity) {
            activity.supportFragmentManager
                .registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
                    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
                        if (f is Injectable) {
                            AndroidSupportInjection.inject(f)
                        }
                    }
                }, true)
        }
    }
}