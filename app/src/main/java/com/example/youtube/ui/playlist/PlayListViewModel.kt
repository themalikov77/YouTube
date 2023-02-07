package com.example.youtube.ui.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.youtube.BuildConfig
import com.example.youtube.`object`.Constant
import com.example.youtube.base.BaseViewModel
import com.example.youtube.base.RetrofitClient
import com.example.youtube.model.PlayList
import com.example.youtube.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayListViewModel : BaseViewModel() {
    private val apiService: ApiService by lazy {
        RetrofitClient.create()
    }

    fun playLists(): LiveData<PlayList> {
        return getPlayLists()//хранит в себе LiveData
    }

    val data = MutableLiveData<PlayList>()
    //делает запрос в бэк
    private fun getPlayLists(): LiveData<PlayList> {
        apiService.getPlayLists(BuildConfig.API_KEY, Constant.part, Constant.channelId)
            .enqueue(object : Callback<PlayList> {
                override fun onResponse(call: Call<PlayList>, response: Response<PlayList>) {
                    //200-299
                    if (response.isSuccessful) {
                        data.value = response.body()
                    }
                }

                override fun onFailure(call: Call<PlayList>, t: Throwable) {
                    print(t.stackTrace)
                    //400-499
                }
            })
        return data
    }
}