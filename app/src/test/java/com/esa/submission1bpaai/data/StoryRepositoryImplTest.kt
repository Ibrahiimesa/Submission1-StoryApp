package com.esa.submission1bpaai.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.ListUpdateCallback
import com.esa.submission1bpaai.data.model.Story
import com.esa.submission1bpaai.data.network.api.ApiService
import com.esa.submission1bpaai.data.preference.UserPreferences
import com.esa.submission1bpaai.data.repository.StoryRepository
import com.esa.submission1bpaai.ui.adapter.StoryAdapter
import com.esa.submission1bpaai.ui.story.StoryPagingSource
import com.esa.submission1bpaai.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.io.File


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class StoryRepositoryImplTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var apiService: ApiService
    @Mock
    private lateinit var pref: UserPreferences
    @Mock
    private lateinit var storyRepository: StoryRepository

    @Before
    fun setUp() {
        apiService = FakeApiService()
        storyRepository = StoryRepository( pref, apiService)
    }

    @Test
    fun `when user Register is success`() = runTest {
        val expectedUser = DataDummy.generateDummyRegisterResponse()
        val actualUser = storyRepository.userRegister(name, email, password)
        actualUser.observeForTesting {
            Assert.assertNotNull(actualUser)
            Assert.assertEquals(expectedUser.message, (actualUser.value as Result.Success).data.message)
        }
    }


    @Test
    fun `when user Login is success`() = runTest {
        val expectedUser = DataDummy.generateDummyLoginResponse()
        val actualUser = storyRepository.userLogin(email, password)
        actualUser.observeForTesting {
            Assert.assertNotNull(actualUser)
            Assert.assertEquals(expectedUser.message, (actualUser.value as Result.Success).data.message)
        }
    }

    @Test
    fun `when get Story is success`() = runTest {
        val mockedClass = mock(StoryRepository::class.java)
        val expectedStory = MutableLiveData<PagingData<Story>>()
        expectedStory.value = StoryPagingSource.snapshot(DataDummy.generateDummyStory())

        `when`(mockedClass.getStory()).thenReturn(expectedStory)

        val actualData = mockedClass.getStory().getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoryAdapter.DIFF_CALLBACK,
            updateCallback = object : ListUpdateCallback {
                override fun onInserted(position: Int, count: Int) {}
                override fun onRemoved(position: Int, count: Int) {}
                override fun onMoved(fromPosition: Int, toPosition: Int) {}
                override fun onChanged(position: Int, count: Int, payload: Any?) {}
            },
            workerDispatcher = Dispatchers.Main
        )
        differ.submitData(actualData)

        verify(mockedClass).getStory()

        Assert.assertNotNull(actualData)
        Assert.assertEquals(DataDummy.generateDummyStory(), differ.snapshot())
        Assert.assertEquals(DataDummy.generateDummyStory().size, differ.snapshot().size)
    }

    @Test
    fun `when get Story Location is success`() = runTest{
        val expectedStory = DataDummy.generateDummyStoryLocation()
        val actualStory = storyRepository.getStoryLocation(token)
        actualStory.observeForTesting {
            Assert.assertNotNull(actualStory)
            Assert.assertEquals(expectedStory.listStory.size, (actualStory.value as Result.Success).data.listStory.size)
        }
    }

    @Test
    fun `when add Story is success`() = runTest{
        val description = "description".toRequestBody("text/plain".toMediaType())

        val file = mock(File::class.java)
        val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            file.name,
            requestImageFile
        )

        val expectedStory = DataDummy.generateDummyAddStoryResponse()
        val actualStory = storyRepository.addStory(token, imageMultipart, description)
        actualStory.observeForTesting {
            Assert.assertNotNull(actualStory)
            Assert.assertEquals(expectedStory.message, (actualStory.value as Result.Success).data.message)
        }
    }

    companion object {
        private const val token = "TOKEN"
        private const val name = "Name"
        private const val email = "email@gmail.com"
        private const val password = "password"
    }
}