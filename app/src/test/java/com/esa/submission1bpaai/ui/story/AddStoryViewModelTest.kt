package com.esa.submission1bpaai.ui.story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.esa.submission1bpaai.data.network.response.BaseResponse
import com.esa.submission1bpaai.data.repository.StoryRepository
import com.esa.submission1bpaai.utils.DataDummy
import com.esa.submission1bpaai.data.Result
import com.esa.submission1bpaai.utils.getOrAwaitValue
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest {

    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var addStoryViewModel: AddStoryViewModel
    private val dummyAddStory = DataDummy.generateDummyAddStoryResponse()
    private val token = "TOKEN"

    @Before
    fun setUp() {
        addStoryViewModel = AddStoryViewModel(storyRepository)
    }

    @Test
    fun `when Add Story is Success`() {
        val description = "description".toRequestBody("text/plain".toMediaType())
        val file = Mockito.mock(File::class.java)
        val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            requestImageFile
        )

        val expectedStory = MutableLiveData<Result<BaseResponse>>()
        expectedStory.value = Result.Success(dummyAddStory)
        `when`(storyRepository.addStory(token, imageMultipart, description)).thenReturn(expectedStory)

        val actualStory = addStoryViewModel.addStory(token, imageMultipart, description).getOrAwaitValue()

        Mockito.verify(storyRepository).addStory(token, imageMultipart, description)
        assertNotNull(actualStory)
        assertTrue(actualStory is Result.Success)
    }
}