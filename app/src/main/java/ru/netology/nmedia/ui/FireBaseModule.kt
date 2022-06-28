package ru.netology.nmedia.ui

import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FireBaseModule {
    @Provides
    @Singleton
    fun provideFireBaseMessaging(): FirebaseMessaging {
        return FirebaseMessaging.getInstance()
    }
}