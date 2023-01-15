package com.esa.submission1bpaai.ui.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.esa.submission1bpaai.data.Result
import com.esa.submission1bpaai.data.network.response.StoryResponse
import com.esa.submission1bpaai.data.repository.StoryRepository
import com.esa.submission1bpaai.utils.DataDummy
import com.esa.submission1bpaai.utils.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var mapsViewModel: MapsViewModel
    private val dummyStoryLocation = DataDummy.generateDummyStoryLocation()
    private val token = "TOKEN"

    @Before
    fun setUp() {
        mapsViewModel = MapsViewModel(storyRepository)
    }

    @Test
    fun `when get Story Location is success`() {
        val expectedStory = MutableLiveData<Result<StoryResponse>>()
        expectedStory.value = Result.Success(dummyStoryLocation)

        `when`(storyRepository.getStoryLocation(token)).thenReturn(expectedStory)

        val actualStory = mapsViewModel.getStoryLocation(token).getOrAwaitValue()
        Mockito.verify(storyRepository).getStoryLocation(token)
        assertNotNull(actualStory)
        assertTrue(actualStory is Result.Success)
    }
}