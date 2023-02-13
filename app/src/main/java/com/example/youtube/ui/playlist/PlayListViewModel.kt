package com.example.youtube.ui.playlist

import androidx.lifecycle.LiveData
import com.example.youtube.App
import com.example.youtube.core.network.result.Resource
import com.example.youtube.core.ui.BaseViewModel
import com.example.youtube.data.remote.model.PlayList

class PlayListViewModel : BaseViewModel() {
    var pageToken: String? = null
    var totalCount: Int = 1
    fun getPlayLists(pageToken: String?): LiveData<Resource<PlayList>> {
        return App().repository.getPlayLists(pageToken)
    }
}