package com.esa.submission1bpaai.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.esa.submission1bpaai.data.model.User
import com.esa.submission1bpaai.data.repository.StoryRepository

class MapsViewModel(private val repository: StoryRepository): ViewModel() {

    fun getStoryLocation(token: String) =
        repository.getStoryLocation(token)

    fun getUser(): LiveData<User> {
        return repository.getUserData()
    }
}