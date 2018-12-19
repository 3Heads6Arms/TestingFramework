package com.hoang.anh.testingframework.di

import android.app.Application
import com.hoang.anh.testingframework.MyApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ActivityModule::class,
        FragmentModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    fun inject(app: MyApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}