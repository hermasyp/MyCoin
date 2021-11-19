package com.catnip.mycoin.di

import android.content.Context
import com.catnip.mycoin.data.local.SessionPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideSessionPreferences(@ApplicationContext context : Context) : SessionPreference{
        return SessionPreference(context)
    }


}