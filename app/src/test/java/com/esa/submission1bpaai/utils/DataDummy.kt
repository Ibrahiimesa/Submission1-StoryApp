package com.esa.submission1bpaai.utils

import com.esa.submission1bpaai.data.model.Login
import com.esa.submission1bpaai.data.model.Story
import com.esa.submission1bpaai.data.network.response.BaseResponse
import com.esa.submission1bpaai.data.network.response.LoginResponse
import com.esa.submission1bpaai.data.network.response.StoryResponse

object DataDummy {

    fun generateDummyLoginResponse(): LoginResponse{
        return LoginResponse(
            false,
            "token",
            Login(
                "id",
                "name",
                "token"
            )
        )
    }

    fun generateDummyRegisterResponse(): BaseResponse{
        return BaseResponse(
            false,
            "success"
        )
    }

    fun generateDummyStory(): List<Story>{
        val item = arrayListOf<Story>()

        for (i in 0 until 10){
            val story = Story(
                "story-cqEaMhCjjM5Ws20J",
                "ess",
                "zz",
                "https://story-api.dicoding.dev/images/stories/photos-1665555649719_1_Bqqmvm.jpg",
                "2022-10-12T06:20:49.720Z",
                -6.1335033,
                106.64356
            )
            item.add(story)
        }
        return item
    }

    fun generateDummyStoryLocation(): StoryResponse{
        val item: MutableList<Story> = arrayListOf()
        for (i in 0..100){
            val story = Story(
                "story-cqEaMhCjjM5Ws20J",
                "ess",
                "zz",
                "https://story-api.dicoding.dev/images/stories/photos-1665555649719_1_Bqqmvm.jpg",
                "2022-10-12T06:20:49.720Z",
                -6.1335033,
                106.64356
            )
            item.add(story)
        }
        return StoryResponse(
            false,
            "success",
            item
        )
    }

    fun generateDummyAddStoryResponse(): BaseResponse{
        return BaseResponse(
            false,
            "success"
        )
    }
}