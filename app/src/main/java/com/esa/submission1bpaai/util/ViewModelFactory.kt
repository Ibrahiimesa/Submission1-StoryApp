package com.esa.submission1bpaai.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.esa.submission1bpaai.data.preference.UserPreferences
import com.esa.submission1bpaai.ui.story.AddStoryViewModel
import com.esa.submission1bpaai.ui.user.UserViewModel
import com.esa.submission1bpaai.ui.story.MainViewModel

class ViewModelFactory(private val pref: UserPreferences) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(pref) as T
        }
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }
        if (modelClass.isAssignableFrom(AddStoryViewModel::class.java)) {
            return AddStoryViewModel(pref) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.simpleName)
    }
}