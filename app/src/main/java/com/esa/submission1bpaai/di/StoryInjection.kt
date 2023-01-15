package com.esa.submission1bpaai.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.esa.submission1bpaai.data.network.api.ApiConfig
import com.esa.submission1bpaai.data.preference.UserPreferences
import com.esa.submission1bpaai.data.repository.StoryRepository

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("storyapp")

object StoryInjection {
    fun provideRepository(context: Context): StoryRepository {
        val preferences = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiClient()
        return StoryRepository.getInstance(preferences, apiService)
    }
}