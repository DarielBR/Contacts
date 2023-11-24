package com.bravoromeo.contacts.hilt_di

import android.content.Context
import android.os.Environment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideContext(@ApplicationContext applicationContext: Context): Context{
        return applicationContext
    }
    @Singleton
    @Provides
    fun provideEnvironment(@ApplicationContext environment: Environment): Environment{
        return environment
    }
}