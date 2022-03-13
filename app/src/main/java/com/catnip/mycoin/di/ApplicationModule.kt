package com.catnip.mycoin.di

import android.content.Context
import com.catnip.mycoin.data.local.preference.SessionPreference
import com.google.gson.Gson
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
    fun provideSessionPreferences(
        @ApplicationContext context: Context,
        gson: Gson
    ): SessionPreference {
        return SessionPreference(context, gson)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return Gson()
    }

}