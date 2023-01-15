package com.esa.submission1bpaai.ui.story

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.esa.submission1bpaai.data.model.Story
import com.esa.submission1bpaai.data.model.User
import com.esa.submission1bpaai.data.repository.StoryRepository

class MainViewModel(private val repository: StoryRepository) : ViewModel(){

    fun getStory(): LiveData<PagingData<Story>> {
        return  repository.getStory().cachedIn(viewModelScope)
    }

    fun getUser(): LiveData<User> {
        return repository.getUserData()
    }

}