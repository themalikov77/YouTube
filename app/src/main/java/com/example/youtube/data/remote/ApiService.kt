package com.example.youtube.data.remote

import com.example.youtube.data.remote.model.PlayList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("playlists")
    fun getPlayLists(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("channelId") channelId: String,
        @Query("maxResults") maxResults: Int,
        @Query("pageToken") pageToken: String?
    ): Call<PlayList>

    @GET("playlistItems")
    fun getInformationPlayLists(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("playlistId") channelId: String,
        @Query("maxResults") maxResults: Int
    ): Call<PlayList>

}