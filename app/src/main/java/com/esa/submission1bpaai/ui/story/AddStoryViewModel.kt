package com.esa.submission1bpaai.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.esa.submission1bpaai.data.model.User
import com.esa.submission1bpaai.data.repository.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody


class AddStoryViewModel(private val repository: StoryRepository) : ViewModel() {

    fun addStory(token: String, file: MultipartBody.Part, description: RequestBody) = repository.addStory(token, file, description)

    fun getUser(): LiveData<User> {
        return repository.getUserData()
    }
}